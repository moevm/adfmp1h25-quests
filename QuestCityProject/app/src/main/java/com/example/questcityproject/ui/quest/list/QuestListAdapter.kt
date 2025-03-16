package com.example.questcityproject.ui.quest.list

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.questcityproject.R
import com.example.questcityproject.ui.quest.list.bar.QuestListBar

class QuestListAdapter(private val items: List<QuestListBar>) : RecyclerView.Adapter<QuestListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val primaryTextView: TextView = view.findViewById(R.id.questTitle)
        val secondaryTextView: TextView = view.findViewById(R.id.questDescription)
        val progressView: TextView = view.findViewById(R.id.questProgress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_quest, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (items.size == 0) { return }
        val item = items[position]
        holder.primaryTextView.text = item.primaryName
        holder.secondaryTextView.text = item.secondaryName
        if (item.isActive) {
            val progress = "${item.numPointsVisited} / ${item.numPointsAll}"
            holder.progressView.text = progress

            // Установка цветовой рамки
            val shapeDrawable = GradientDrawable()
            when (position) {
                0 -> shapeDrawable.setStroke(8, Color.RED)
                1 -> shapeDrawable.setStroke(8, Color.YELLOW)
                2 -> shapeDrawable.setStroke(8, Color.GREEN)
                3 -> shapeDrawable.setStroke(8, Color.BLUE)
                4 -> shapeDrawable.setStroke(8, Color.MAGENTA)
            }
            shapeDrawable.setColor(Color.WHITE) // Установка цвета фона
            holder.itemView.background = shapeDrawable
        }
    }

    override fun getItemCount() = items.size
}