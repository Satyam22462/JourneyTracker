package com.example.journeytracker20_compose

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JourneyTrackerApp()
        }
    }
}

@Composable
fun JourneyTrackerApp() {
    var isKm by remember { mutableStateOf(true) }
    var currentStopIndex by remember { mutableStateOf(0) }
    val stops = remember { mutableStateListOf<String>() }
    val distances = remember { mutableStateListOf<Int>() }
    val visaRequirements = remember { mutableStateListOf<String>() }
    var totalDistance by remember { mutableStateOf(0) }

    // Get the context
    val context = LocalContext.current

    // Read stops from file
    LaunchedEffect(Unit) {
        readStopsFromFile(stops, distances, visaRequirements, context)
        totalDistance = distances.sum()
    }

    val distanceCovered = distances.take(currentStopIndex).sum()
    val distanceLeft = totalDistance - distanceCovered

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Stops Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "🚩 Stops",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Use LazyColumn for efficient rendering of stops
                LazyColumn(
                    modifier = Modifier.heightIn(max = 200.dp) // Set a maximum height
                ) {
                    items(stops.take(currentStopIndex)) { stop ->
                        StopItem(
                            stop = stop,
                            distance = distances[stops.indexOf(stop)],
                            visa = visaRequirements[stops.indexOf(stop)],
                            isKm = isKm
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Distance Covered Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "🛬 Distance Covered",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isKm) "$distanceCovered km" else "${kmToMiles(distanceCovered)} miles",
                    fontSize = 18.sp,
                    color = Color.Green
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Distance Left Card
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.LightGray)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "📏 Distance Left",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isKm) "$distanceLeft km" else "${kmToMiles(distanceLeft)} miles",
                    fontSize = 18.sp,
                    color = Color.Red
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Buttons and Progress Bar
        Button(
            onClick = { isKm = !isKm },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
        ) {
            Text(text = if (isKm) "Switch to Miles" else "Switch to Kilometers")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (currentStopIndex < stops.size) {
                    currentStopIndex++
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF018786))
        ) {
            Text(text = if (currentStopIndex < stops.size) "Next Stop: ${stops[currentStopIndex]}" else "End of Journey")
        }
        Spacer(modifier = Modifier.height(16.dp))

        LinearProgressIndicator(
            progress = { if (totalDistance > 0) (distanceCovered.toFloat() / totalDistance) else 0f },
            modifier = Modifier.fillMaxWidth(),
            color = Color(0xFF6200EE),
        )
    }
}

@Composable
fun StopItem(stop: String, distance: Int, visa: String, isKm: Boolean) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = "📍 $stop",
            fontSize = 16.sp,
            color = Color.Black
        )
        Text(
            text = if (isKm) "Distance: $distance km" else "Distance: ${kmToMiles(distance)} miles",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = "Visa: $visa",
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

fun kmToMiles(km: Int): Double {
    return km * 0.621371
}

fun readStopsFromFile(
    stops: MutableList<String>,
    distances: MutableList<Int>,
    visaRequirements: MutableList<String>,
    context: Context
) {
    val inputStream = context.resources.openRawResource(R.raw.input1)
    inputStream.bufferedReader().useLines { lines ->
        lines.forEach { line ->
            val parts = line.split(",")
            if (parts.size == 3) {
                stops.add(parts[0].trim())
                distances.add(parts[1].trim().toInt())
                visaRequirements.add(parts[2].trim())
            }
        }
    }
}





