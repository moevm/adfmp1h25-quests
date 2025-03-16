package com.example.questcityproject.ui.quest.list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questcityproject.R
import com.example.questcityproject.databinding.FragmentQuestListBinding
import com.example.questcityproject.ui.quest.list.bar.QuestListBar
import com.example.questcityproject.ui.questElement.QuestElementFragment

class QuestListFragment : Fragment() {

    private var _binding: FragmentQuestListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var questsList: List<QuestListBar> = listOf()

    private var noQuestsField: TextView? = null
    private var isActiveQuestsChecked: Boolean = false
    private var searchTextEditor: EditText? = null
    private var questNameSearch: String = ""

            private var recyclerView: RecyclerView? = null
    private var adapter: QuestListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_quest_list, container, false)

        var listArg = mutableListOf<QuestListBar>()
        // Инициализация списка элементов
        listArg.add(QuestListBar(0, "Булочная №X", "Посети все пекарни Ф.Вольчека", "fdfsdfsd",false, 99))
        listArg.add(QuestListBar(1, "Город мостов", "Посети все мосты Петербурга", "dsfgfdsg", false, 465))
        listArg.add(QuestListBar(2, "Общежития ЛЭТИ", "Посети все общежития ЛЭТИ", "ddddddddddddddddddddddd",false, 9))
        listArg.add(QuestListBar(3, "Фрукты в плитке", "Найди все арт-объекты в тратуарной плитке", "",false, 70))
        listArg.add(QuestListBar(4, "Булочка с корицей", "Посети памятник булочке с корицей", "dfdsf",false, 1))
        listArg.add(QuestListBar(5, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат", "d",false, 1))
        listArg.add(QuestListBar(6, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат", "3242432",true, 1, 0))
        listArg.add(QuestListBar(7, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат", "",true, 15, 3))
        listArg.add(QuestListBar(8, "Город мостов", "Посети все мосты Петербурга", "",true, 465, 279))
        listArg.add(QuestListBar(9, "Общежития ЛЭТИ", "Посети все общежития ЛЭТИ", "",true, 9, 1))
        listArg.add(QuestListBar(10, "Булочка с корицей", "Посети памятник булочке с корицей", "",false, 1))
        listArg.add(QuestListBar(11, "Фрукты в плитке", "Найди все арт-объекты в тратуарной плитке", "",false, 96))
        listArg.add(QuestListBar(12, "Фрукты в плитке", "Найди все арт-объекты в тратуарной плитке", "",true, 96, 12))
        questsList = listArg as List<QuestListBar>

        recyclerView = view.findViewById(R.id.questList)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        prepareQuestList()
        drawQuestsListPart(questsList)

        searchTextEditor = view.findViewById(R.id.searchTextEditor)
        searchTextEditor?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                questNameSearch = s.toString().lowercase()
                drawQuestsListPart(questsList )
            }
        })

        noQuestsField = view.findViewById(R.id.noQuestsLabel)

        adapter?.onItemClick = { quest ->
            val bundle = Bundle()
            bundle.putInt("id", quest.id)
            bundle.putString("primaryName", quest.primaryName)
            bundle.putString("secondaryName", quest.secondaryName)
            bundle.putString("description", quest.description)
            bundle.putBoolean("isActive", quest.isActive)
            bundle.putInt("numPointsAll", quest.numPointsAll)
            bundle.putInt("numPointsVisited", quest.numPointsVisited)
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_questElementFragment, bundle);

        }


        val checkboxView: CheckBox = view.findViewById(R.id.activeQuestsCheckbox)
        checkboxView.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                isActiveQuestsChecked = true
                val filteredList = questsList.filter {
                    item -> item.isActive
                }
                drawQuestsListPart(filteredList)
            } else {
                isActiveQuestsChecked = false
                drawQuestsListPart(questsList)
            }
        }

        return view
    }

    private fun sortQuests() {
        if (questsList.isEmpty()) return
        questsList = questsList.sortedBy {
            item -> !item.isActive
        } as MutableList<QuestListBar>
    }

    private fun prepareQuestList() {
        sortQuests()
    }

    private fun drawQuestsListPart(drawableList: List<QuestListBar>) {
        var filteredList = drawableList
        if (questNameSearch.isNotEmpty()) {
            filteredList = drawableList.filter { item ->
                item.primaryName.lowercase().contains(questNameSearch) ||
                item.secondaryName.lowercase().contains(questNameSearch)
            }
        }
        if (filteredList.isEmpty()) {
            noQuestsField?.visibility = View.VISIBLE
            noQuestsField?.text = "На данный момент у вас нет активных квестов. Вы можете сделать квест активным, открыв карточку квеста и нажав \"Начать квест\"."
        } else {
            noQuestsField?.visibility = View.GONE
        }
        adapter = QuestListAdapter(filteredList)
        recyclerView?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}