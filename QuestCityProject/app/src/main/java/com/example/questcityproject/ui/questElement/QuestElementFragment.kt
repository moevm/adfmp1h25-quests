package com.example.questcityproject.ui.questElement

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.questcityproject.R
import com.example.questcityproject.databinding.FragmentQuestElementBinding

class QuestElementFragment : Fragment() {

    private var _binding: FragmentQuestElementBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_quest_element, container, false)
        val questTitle = view.findViewById<TextView>(R.id.questTitle)
        val questSubtitle = view.findViewById<TextView>(R.id.questSubtitle)
        val questDescription = view.findViewById<TextView>(R.id.questDescription)
        val questProgress = view.findViewById<TextView>(R.id.questProgress)
        val questImage = view.findViewById<ImageView>(R.id.questImage)
        val determinateBar = view.findViewById<ProgressBar>(R.id.determinateBar)
        val actionButton: Button = view.findViewById(R.id.actionButton)
//        val mapButton: Button = view.findViewById(R.id.mapButton)

        val numPointsAll = arguments?.getInt("numPointsAll")
        val numPointsVisited = arguments?.getInt("numPointsVisited")
        if (numPointsVisited != null && numPointsAll != null){
            val numPointsVisitedPercent = numPointsVisited * 100 / numPointsAll
            determinateBar.progress = numPointsVisitedPercent
        }

        val progress = "$numPointsVisited / $numPointsAll"


        questImage.setImageResource(R.drawable.volchek)
        questTitle.text = arguments?.getString("primaryName")
        questSubtitle.text = arguments?.getString("secondaryName")
        questDescription.text = arguments?.getString("description")
        questProgress.text = progress

        var isActive = arguments?.getBoolean("isActive")

        actionButton.text = if (isActive == true) "Прекратить квест" else "Начать квест"


        actionButton.setOnClickListener {
            isActive = if (isActive == true) false else true
            actionButton.text = if (isActive == true) "Прекратить квест" else "Начать квест"
        }

//        mapButton.setOnClickListener {
//            Navigation.findNavController(view).navigate(R.id.action_questElementFragment_to_mapElementFragment);
//        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
