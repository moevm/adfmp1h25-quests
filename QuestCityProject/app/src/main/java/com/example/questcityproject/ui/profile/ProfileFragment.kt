package com.example.questcityproject.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.questcityproject.R
import com.example.questcityproject.databinding.FragmentUserProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_user_profile, container, false)


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}