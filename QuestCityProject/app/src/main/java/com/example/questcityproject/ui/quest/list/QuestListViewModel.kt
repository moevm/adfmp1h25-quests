package com.example.questcityproject.ui.quest.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class QuestListViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is quest list Fragment"
    }
    val text: LiveData<String> = _text
}