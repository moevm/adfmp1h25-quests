package com.example.questcityproject.ui.questElement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.questcityproject.R
import com.example.questcityproject.databinding.FragmentQuestElementBinding

class QuestElementFragment : Fragment() {

    private var _binding: FragmentQuestElementBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_quest_element, container, false)
        return view

//        val notificationsViewModel =
//            ViewModelProvider(this).get(NotificationsViewModel::class.java)
//
//        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
