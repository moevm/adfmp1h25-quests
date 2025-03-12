package com.example.questcityproject.ui.quest.list

//class QuestListAdapter {
//}
//// ListAdapter.kt
//package com.example.navigationexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.questcityproject.R
import com.example.questcityproject.ui.quest.list.bar.QuestListBar

class ListAdapter(private val items: List<QuestListBar>) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView = view.findViewById(R.id.textViewItem)
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
        holder.primaryTextView.text = items[position].primaryName
        holder.secondaryTextView.text = items[position].secondaryName
        holder.progressView.text = ""
    }

    override fun getItemCount() = items.size
}