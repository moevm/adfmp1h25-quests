package com.example.questcityproject.ui.quest.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_quest_list, container, false)

        // Инициализация списка элементов
        val itemList = arrayListOf<QuestListBar>()
        itemList.add(QuestListBar(0, "Булочная №X", "Посети все пекарни Ф.Вольчека"))
        itemList.add(QuestListBar(1, "Город мостов", "Посети все мосты Петербурга"))
        itemList.add(QuestListBar(2, "Общежития ЛЭТИ", "Посети все общежития ЛЭТИ"))
        itemList.add(QuestListBar(3, "Фрукты в плитке", "Найди все арт-объекты в тратуарной плитке"))
        itemList.add(QuestListBar(4, "Булочка с корицей", "Посети памятник булочке с корицей"))
        itemList.add(QuestListBar(5, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат"))
        itemList.add(QuestListBar(6, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат"))
        itemList.add(QuestListBar(7, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат"))


        val recyclerView : RecyclerView = view.findViewById(R.id.questList)
        val adapter = ListAdapter(itemList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
//        val questListViewModel =
//            ViewModelProvider(this).get(QuestListViewModel::class.java)
//
//        _binding = FragmentQuestListBinding.inflate(inflater, container, false)
//        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val questsBtn: Button = view.findViewById(R.id.menu_quests)
        val mapBtn: Button = view.findViewById(R.id.menu_map)
        val profileBtn: Button = view.findViewById(R.id.menu_profile)
        val controller = findNavController()
        mapBtn.setOnClickListener{ controller.navigate(R.id.mapFragment) }
        profileBtn.setOnClickListener{ controller.navigate(R.id.profileFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}