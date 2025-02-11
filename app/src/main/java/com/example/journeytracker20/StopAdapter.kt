// StopAdapter.kt
package com.example.journeytracker20

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StopAdapter(
    private val stops: List<String>,
    private val distances: List<Int>,
    private val visaRequirements: List<String>,
    private val isKm: Boolean
) : RecyclerView.Adapter<StopAdapter.StopViewHolder>() {

    inner class StopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvStopName: TextView = itemView.findViewById(R.id.tvStopName)
        private val tvStopDistance: TextView = itemView.findViewById(R.id.tvStopDistance)
        private val tvStopVisa: TextView = itemView.findViewById(R.id.tvStopVisa)

        fun bind(stop: String, distance: Int, visa: String) {
            tvStopName.text = "📍 $stop"
            tvStopDistance.text = if (isKm) "Distance: $distance km" else "Distance: ${kmToMiles(distance)} miles"
            tvStopVisa.text = "Visa: $visa"
        }

        private fun kmToMiles(km: Int): Double {
            return km * 0.621371
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stop, parent, false)
        return StopViewHolder(view)
    }

    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        holder.bind(stops[position], distances[position], visaRequirements[position])
    }

    override fun getItemCount(): Int {
        return stops.size
    }
}