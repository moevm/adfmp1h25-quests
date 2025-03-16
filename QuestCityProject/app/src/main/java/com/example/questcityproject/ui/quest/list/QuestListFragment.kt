package com.example.questcityproject.ui.quest.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questcityproject.R
import com.example.questcityproject.databinding.FragmentQuestListBinding
import com.example.questcityproject.ui.quest.list.bar.QuestListBar

class QuestListFragment : Fragment() {

    private var _binding: FragmentQuestListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var questsList: MutableList<QuestListBar> = mutableListOf()

    private var noQuestsField: TextView? = null
    private var isActiveQuestsChecked: Boolean = false
    private var questNameSearch: String = ""

    private var recyclerView: RecyclerView? = null
    private var adapter: QuestListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_quest_list, container, false)

        // Инициализация списка элементов
        questsList.add(QuestListBar(0, "Булочная №X", "Посети все пекарни Ф.Вольчека", false, 99))
        questsList.add(QuestListBar(1, "Город мостов", "Посети все мосты Петербурга", false, 465))
        questsList.add(QuestListBar(2, "Общежития ЛЭТИ", "Посети все общежития ЛЭТИ", false, 9))
        questsList.add(QuestListBar(3, "Фрукты в плитке", "Найди все арт-объекты в тратуарной плитке", false, 70))
        questsList.add(QuestListBar(4, "Булочка с корицей", "Посети памятник булочке с корицей", false, 1))
        questsList.add(QuestListBar(5, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат", false, 1))
        questsList.add(QuestListBar(6, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат", true, 1, 0))
        questsList.add(QuestListBar(7, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат", true, 15, 3))
        questsList.add(QuestListBar(8, "Город мостов", "Посети все мосты Петербурга", true, 465, 279))
        questsList.add(QuestListBar(9, "Общежития ЛЭТИ", "Посети все общежития ЛЭТИ", true, 9, 1))
        questsList.add(QuestListBar(10, "Булочка с корицей", "Посети памятник булочке с корицей", false, 1))
        questsList.add(QuestListBar(11, "Фрукты в плитке", "Найди все арт-объекты в тратуарной плитке", false, 96))
        questsList.add(QuestListBar(12, "Фрукты в плитке", "Найди все арт-объекты в тратуарной плитке", true, 96, 12))

        recyclerView = view.findViewById(R.id.questList)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        prepareQuestList()
        drawQuestsListPart(questsList)

        noQuestsField = view.findViewById(R.id.noQuestsLabel)

        val checkboxView: CheckBox = view.findViewById(R.id.activeQuestsCheckbox)
        checkboxView.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                isActiveQuestsChecked = true
                drawQuestsListPart(questsList.filter {
                    item -> item.isActive
                })
            } else {
                isActiveQuestsChecked = false
                drawQuestsListPart(questsList)
            }
        }

        return view
    }

    private fun sortQuests() {
        questsList = questsList.sortedBy { item -> !item.isActive } as MutableList<QuestListBar>
    }

    private fun prepareQuestList() {
        sortQuests()
    }

    private fun drawQuestsListPart(drawableList: List<QuestListBar>) {
        if (drawableList.isEmpty() && isActiveQuestsChecked) {
            noQuestsField?.visibility = View.VISIBLE
            noQuestsField?.text = "На данный момент у вас нет активных квестов. Вы можете сделать квест активным, открыв карточку квеста и нажав \"Начать квест\"."
        } else if (drawableList.isEmpty() && !isActiveQuestsChecked) {
            noQuestsField?.visibility = View.VISIBLE
            noQuestsField?.text = "Квесты не найдены"
        } else {
            noQuestsField?.visibility = View.GONE
            adapter = QuestListAdapter(drawableList)
            recyclerView?.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}