package com.example.questcityproject.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.questcityproject.R
import com.example.questcityproject.databinding.FragmentQuestListBinding

class MapFragment : Fragment() {

    private var _binding: FragmentQuestListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        // Инициализация списка элементов
//        val itemList = arrayListOf<ProfileBar>()
//        itemList.add(ProfileBar(0, "Булочная №X", "Посети все пекарни Ф.Вольчека"))
//        itemList.add(ProfileBar(1, "Город мостов", "Посети все мосты Петербурга"))
//        itemList.add(ProfileBar(2, "Общежития ЛЭТИ", "Посети все общежития ЛЭТИ"))
//        itemList.add(ProfileBar(3, "Фрукты в плитке", "Найди все арт-объекты в тратуарной плитке"))
//        itemList.add(ProfileBar(4, "Булочка с корицей", "Посети памятник булочке с корицей"))
//        itemList.add(ProfileBar(5, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат"))
//        itemList.add(ProfileBar(6, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат"))
//        itemList.add(ProfileBar(7, "Хлебобулочный комбинат", "Посети хлебобулочный комбинат"))


//        val recyclerView : RecyclerView = view.findViewById(R.id.questList)
//        val adapter = ListAdapter(itemList)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter = adapter
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}