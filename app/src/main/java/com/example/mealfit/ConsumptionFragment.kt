package com.example.mealfit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealfit.databinding.FragmentConsumptionBinding
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ConsumptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConsumptionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var currentDate = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumption, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.title = "오늘의 섭취량"
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        val date = requireActivity().findViewById<android.widget.TextView>(R.id.date)
        date.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        updateDate()
        view.findViewById<android.widget.ImageButton>(R.id.arrow_left)?.setOnClickListener{
            currentDate.add(Calendar.DATE, -1)
            updateDate()
        }
        view.findViewById<android.widget.ImageButton>(R.id.arrow_right)?.setOnClickListener{
            currentDate.add(Calendar.DATE, 1)
            updateDate()
        }
    }
    override fun onResume() {
        super.onResume()
        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.title = "오늘의 섭취량"
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        val date = requireActivity().findViewById<android.widget.TextView>(R.id.date)
        date.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        updateDate()
    }
    private fun updateDate(){
        val month = currentDate.get(Calendar.MONTH) + 1
        val day = currentDate.get(Calendar.DAY_OF_MONTH)
        val dayOfWeek = when(currentDate.get(Calendar.DAY_OF_WEEK)){
            1 -> "일"
            2 -> "월"
            3 -> "화"
            4 -> "수"
            5 -> "목"
            6 -> "금"
            7 -> "토"
            else -> ""
        }
        val date = "${month}월 ${day}일 ${dayOfWeek}요일"
        view?.findViewById<android.widget.TextView>(R.id.date)?.text = date
    }
}