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
import androidx.appcompat.app.AlertDialog
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.questcityproject.R
import com.example.questcityproject.databinding.FragmentQuestElementBinding

class QuestElementFragment : Fragment() {

    private var _binding: FragmentQuestElementBinding? = null
    private val binding get() = _binding!!

    // Define your own isActive variable instead of using the one from kotlinx.coroutines
    private var isActive = false

    @SuppressLint("SetTextI18n", "DiscouragedApi")
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

        val numPointsAll = arguments?.getInt("numPointsAll") ?: 0
        val numPointsVisited = arguments?.getInt("numPointsVisited") ?: 0
        val mapButton: ImageButton = view.findViewById(R.id.mapButton)
        val imageName = arguments?.getString("images")
        val numPointsVisitedPercent = if (numPointsAll > 0) {
            numPointsVisited * 100 / numPointsAll
        } else {
            0
        }
        determinateBar.progress = numPointsVisitedPercent

        val progress = "$numPointsVisited / $numPointsAll"

        if (imageName != null){
            val resID = resources.getIdentifier(imageName, "drawable", requireContext().packageName)
            questImage.setImageResource(resID)
        }

        questTitle.text = arguments?.getString("primaryName")
        questSubtitle.text = arguments?.getString("secondaryName")
        questDescription.text = arguments?.getString("description")
        questProgress.text = progress

        // Initialize isActive from arguments
        isActive = arguments?.getBoolean("isActive") ?: false

        updateActionButtonText(actionButton)

        actionButton.setOnClickListener {
            if (!isActive) {
                // User is trying to start a quest
                if (true) { // Note: This will always show the dialog - you might want to change this condition
                    showTooManyQuestsDialog()
                } else {
                    // Start the quest
                    isActive = true
                    updateActionButtonText(actionButton)
                    // Add your quest activation logic here
                }
            } else {
                // User is trying to stop a quest
                showStopQuestDialog(actionButton)
            }
        }

        mapButton.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_questElementFragment_to_mapElementFragment)
        }

        return view
    }

    private fun updateActionButtonText(button: Button) {
        button.text = if (isActive) "Прекратить квест" else "Начать квест"
    }

    private fun tooMuchQuestsCheck(): Boolean {
        // Implement your logic to check if there are too many active quests
        // For example, query your database or shared preferences
        // Return true if there are too many quests, false otherwise
        return false // Placeholder, replace with actual implementation
    }

    private fun showTooManyQuestsDialog() {
        context?.let { ctx ->
            val dialog = AlertDialog.Builder(ctx)
                .setMessage("Количество активных квестов не должно превышать 5!")
                .setPositiveButton("OK", null)
                .setCancelable(false)
                .create()

            dialog.show()
        }
    }

    private fun showStopQuestDialog(actionButton: Button) {
        context?.let { ctx ->
            val dialog = AlertDialog.Builder(ctx)
                .setMessage("Прекратить квест? Прогресс будет удален безвозвратно.")
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton("Прекратить") { _, _ ->
                    // Stop the quest
                    isActive = false
                    updateActionButtonText(actionButton)
                    // Add your quest deactivation logic here
                }
                .setCancelable(true)
                .create()

            dialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}