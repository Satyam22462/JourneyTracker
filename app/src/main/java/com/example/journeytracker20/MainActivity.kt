
package com.example.journeytracker20

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.InputStream

class MainActivity : AppCompatActivity() {

    private lateinit var tvDistanceCovered: TextView
    private lateinit var tvDistanceLeft: TextView
    private lateinit var btnSwitchUnit: Button
    private lateinit var btnNextStop: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var rvStops: RecyclerView

    private var isKm = true
    private var currentStopIndex = 0
    private val stops = mutableListOf<String>()
    private val distances = mutableListOf<Int>()
    private var totalDistance = 0
    private val visaRequirements = mutableListOf<String>()
    private lateinit var lazyStops: Sequence<Pair<String, Int>>
    private lateinit var lazyVisaRequirements: Sequence<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDistanceCovered = findViewById(R.id.tvDistanceCovered)
        tvDistanceLeft = findViewById(R.id.tvDistanceLeft)
        btnSwitchUnit = findViewById(R.id.btnSwitchUnit)
        btnNextStop = findViewById(R.id.btnNextStop)
        progressBar = findViewById(R.id.progressBar)
        rvStops = findViewById(R.id.rvStops)

        // Set up RecyclerView
        rvStops.layoutManager = LinearLayoutManager(this)

        // Read stops and distances from input1.txt
        readStopsFromFile()

        currentStopIndex = 0
        updateUI()

        // Button to switch between kilometers and miles
        btnSwitchUnit.setOnClickListener {
            isKm = !isKm
            btnSwitchUnit.text = if (isKm) "Switch to Miles" else "Switch to Kilometers"
            updateUI()
        }

        // Button to move to the next stop
        btnNextStop.setOnClickListener {
            if (currentStopIndex < stops.size) {
                currentStopIndex++
                updateUI()
            } else {
                btnNextStop.isEnabled = false
                btnNextStop.text = "End of Journey"
            }
        }
    }

    private fun updateUI() {
        val distanceCovered = distances.take(currentStopIndex).sum()
        val distanceLeft = totalDistance - distanceCovered

        val distanceCoveredText = if (isKm) "$distanceCovered km" else "${kmToMiles(distanceCovered)} miles"
        val distanceLeftText = if (isKm) "$distanceLeft km" else "${kmToMiles(distanceLeft)} miles"

        tvDistanceCovered.text = distanceCoveredText
        tvDistanceLeft.text = distanceLeftText

        // Use lazy sequences if there are more than 3 stops
        val stopsToShow = if (stops.size > 3) {
            lazyStops.take(currentStopIndex).map { it.first }.toList()
        } else {
            stops.take(currentStopIndex)
        }

        val distancesToShow = if (stops.size > 3) {
            lazyStops.take(currentStopIndex).map { it.second }.toList()
        } else {
            distances.take(currentStopIndex)
        }

        val visaRequirementsToShow = if (stops.size > 3) {
            lazyVisaRequirements.take(currentStopIndex).toList()
        } else {
            visaRequirements.take(currentStopIndex)
        }

        // Update RecyclerView Adapter
        val adapter = StopAdapter(stopsToShow, distancesToShow, visaRequirementsToShow, isKm)
        rvStops.adapter = adapter

        if (currentStopIndex < stops.size) {
            btnNextStop.text = "Next Stop: ${stops[currentStopIndex]}"
            btnNextStop.isEnabled = true
        } else {
            btnNextStop.text = "End of Journey"
            btnNextStop.isEnabled = false
        }

        if (totalDistance > 0) {
            val progress = ((distanceCovered.toFloat() / totalDistance) * 100).toInt()
            animateProgressBar(progress)
        } else {
            progressBar.progress = 0
        }
    }

    private fun kmToMiles(km: Int): Double {
        return km * 0.621371
    }

    private fun animateProgressBar(progress: Int) {
        ObjectAnimator.ofInt(progressBar, "progress", progressBar.progress, progress)
            .setDuration(500)
            .start()
    }

    private fun readStopsFromFile() {
        val inputStream: InputStream = resources.openRawResource(R.raw.input1)
        inputStream.bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val parts = line.split(",")
                if (parts.size == 3) {
                    val stop = parts[0].trim()
                    val distance = parts[1].trim().toInt()
                    val visa = parts[2].trim()
                    stops.add(stop)
                    distances.add(distance)
                    visaRequirements.add(visa)
                }
            }
        }
        totalDistance = distances.sum()
        Log.d("AppState", "totalDistance: $totalDistance")

        // Create lazy sequences if there are more than 3 stops
        if (stops.size > 3) {
            lazyStops = stops.asSequence().zip(distances.asSequence())
            lazyVisaRequirements = visaRequirements.asSequence()
        } else {
            lazyStops = stops.zip(distances).asSequence()
            lazyVisaRequirements = visaRequirements.asSequence()
        }
    }
}