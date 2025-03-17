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




        listArg.add(QuestListBar(0, "ЛЭТИ", "Посети ЛЭТИ", "",true, 1))
        listArg.add(QuestListBar(1, "Общежития ЛЭТИ", "Посети все общежития ЛЭТИ", "",true, 9,4))
        listArg.add(QuestListBar(2, "ИТМО", "Посети ИТМО", "",true, 1))
        listArg.add(QuestListBar(3, "Фрукты в плитке", "Найди все арт-объекты в тротуарной плитке", "Относительно недавно в социальных сетях стали мелькать фотографии яблока, разрезанного на две части, и замурованного в тротуарной плитке у метро Сенная площадь. В городе таких мест пока что два. Во втором случае замурована груша.",true, 2))
        listArg.add(QuestListBar(4, "Город мостов", "Посети все разводные мосты Петербурга", "Ежегодно, с апреля по ноябрь, разводные мосты обеспечивают навигацию по реке Неве, создавая необходимые условия для судоходства. Разводка мостов Санкт-Петербурга и их наведение в соответствии с графиком — приоритетное направление работы СПб ГБУ «Мостотрест».\n" +
                "\n" +
                "В Санкт-Петербурге насчитывается 18 разводных мостов — тех, у которых сохранились в рабочем состоянии разводные механизмы. Из них в период навигации регулярно разводятся 12 переправ:\n" +
                "\n" +
                "Троицкий, Литейный, Большеохтинский, мост Александра Невского и Володарский — через реку Неву;\n" +
                "Благовещенский и Дворцовый мосты — через Большую Неву, соединяющие Центральную часть города и Васильевский остров;;\n" +
                "Биржевой и Тучков мосты — через Малую Неву, которые соединяют Петроградскую сторону с Васильевским островом;\n" +
                "Сампсониевский, Гренадерский и Кантемировский  мосты — через Большую Невку.", true, 12,2))

        listArg.add(QuestListBar(5, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат", "",false, 1))
        listArg.add(QuestListBar(6, "Булочная №X", "Посети все пекарни Ф.Вольчека", "Булочные Ф. Вольчека\n" +
                "— это самая известная сеть булочных в Санкт-Петербурге.\n" +
                "Булочная №1 открылась 15 февраля 2014 года. Нашу сеть основал Филипп Вольчек, именно его год рождения указан на логотипе сети. Мы считаемся самой вкусной и притом недорогой сетью булочных.",false, 99))
        questsList = listArg as List<QuestListBar>

        recyclerView = view.findViewById(R.id.questList)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        prepareQuestList()
        drawQuestsListPart(questsList, view)

        searchTextEditor = view.findViewById(R.id.searchTextEditor)
        searchTextEditor?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                questNameSearch = s.toString().lowercase()
                drawQuestsListPart(questsList, view)
            }
        })

        noQuestsField = view.findViewById(R.id.noQuestsLabel)

        val checkboxView: CheckBox = view.findViewById(R.id.activeQuestsCheckbox)
        checkboxView.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                isActiveQuestsChecked = true
                val filteredList = questsList.filter {
                        item -> item.isActive
                }
                drawQuestsListPart(filteredList, view)
            } else {
                isActiveQuestsChecked = false
                drawQuestsListPart(questsList, view)
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

    private fun drawQuestsListPart(drawableList: List<QuestListBar>, view: View) {
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
        setBundle(view)
        recyclerView?.adapter = adapter
    }

    private fun setBundle(view: View) {
        adapter?.onItemClick = { quest ->
            val bundle = Bundle()
//            val images = ['volcheck','volcheck','dorm','fruits','etu']
            bundle.putInt("id", quest.id)
            bundle.putString("primaryName", quest.primaryName)
            if (quest.id == 6){
                bundle.putString("images", "volchek")
            }
            else if (quest.id == 1){
                bundle.putString("images", "dorm")
            }
            else if (quest.id == 3){
                bundle.putString("images", "fruits")
            }
            else if (quest.id == 0){
                bundle.putString("images", "etu")
            }
            else if (quest.id == 2){
                bundle.putString("images", "itmo")
            }
            else if (quest.id == 4){
                bundle.putString("images", "bridge")
            }
            bundle.putString("secondaryName", quest.secondaryName)
            bundle.putString("description", quest.description)
            bundle.putBoolean("isActive", quest.isActive)
            bundle.putInt("numPointsAll", quest.numPointsAll)
            bundle.putInt("numPointsVisited", quest.numPointsVisited)
            Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_questElementFragment, bundle);
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}