package com.example.mealfit

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.FragmentConsumptionBinding
import com.example.mealfit.databinding.FragmentListBinding
import com.example.mealfit.databinding.FragmentMyPageBinding
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

    val breakfastList = mutableListOf<Meal>()
    val lunchList = mutableListOf<Meal>()
    val dinnerList = mutableListOf<Meal>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentConsumptionBinding.bind(view)

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.title = "오늘의 섭취량"
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        val date = requireActivity().findViewById<android.widget.TextView>(R.id.date)
        date.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        updateDate()
        view.findViewById<android.widget.ImageButton>(R.id.arrow_left)?.setOnClickListener{
            currentDate.add(Calendar.DATE, -1)
            updateDate()
            if(currentDate.get(Calendar.DAY_OF_MONTH) != Calendar.getInstance().get(Calendar.DAY_OF_MONTH) ||
                currentDate.get(Calendar.DATE) != Calendar.getInstance().get(Calendar.DATE)){
                binding.container.visibility = View.GONE

                Toast.makeText(requireContext(), "서비스 준비 중입니다", Toast.LENGTH_SHORT).show()
            }
            else{
                binding.container.visibility = View.VISIBLE

                fetchBreakfastData()
                fetchLunchData()
                fetchDinnerData()
            }
        }
        view.findViewById<android.widget.ImageButton>(R.id.arrow_right)?.setOnClickListener{
            currentDate.add(Calendar.DATE, 1)
            updateDate()
            if(currentDate.get(Calendar.DAY_OF_MONTH) != Calendar.getInstance().get(Calendar.DAY_OF_MONTH) ||
                currentDate.get(Calendar.DATE) != Calendar.getInstance().get(Calendar.DATE)){
                binding.container.visibility = View.GONE

                Toast.makeText(requireContext(), "서비스 준비 중입니다", Toast.LENGTH_SHORT).show()
            }
            else{
                binding.container.visibility = View.VISIBLE

                fetchBreakfastData()
                fetchLunchData()
                fetchDinnerData()
            }
        }
        if (currentDate.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH) &&
            currentDate.get(Calendar.DATE) == Calendar.getInstance().get(Calendar.DATE)) {
            fetchBreakfastData()
            fetchLunchData()
            fetchDinnerData()
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

    private fun fetchBreakfastData() {
        // Firebase Storage에서 아침 식사 데이터 가져오기
        val storage = MyApplication.storage
        val sharedPreferenceUser = requireContext().getSharedPreferences("user info", AppCompatActivity.MODE_PRIVATE)
        val email = sharedPreferenceUser.getString("email", "")
        val storageRef = storage.reference.child("${email}/meals/breakfast")
        storageRef.listAll().addOnSuccessListener { listResult ->

            for(item in listResult.items){
                item.getBytes(1024*1024).addOnSuccessListener { bytes ->
                    val mealData = bytes.toString(Charsets.UTF_8)
                    val meal = parseMealData(mealData)
                    breakfastList.add(meal)
                    updateUI()
                }
                    .addOnFailureListener { exception ->
                        Log.e("fetchBreakfastData", "Failed to list items: ${exception.message}")
                    }
            }
        }
            .addOnFailureListener { exception ->
                Log.e("fetchBreakfastData", "Failed to list items: ${exception.message}")
            }
    }

    private fun fetchLunchData() {
        // Firebase Storage에서 점심 식사 데이터 가져오기
        val storage = MyApplication.storage
        val sharedPreferenceUser = requireContext().getSharedPreferences("user info", AppCompatActivity.MODE_PRIVATE)
        val email = sharedPreferenceUser.getString("email", "")
        val storageRef = storage.reference.child("${email}/meals/lunch")
        storageRef.listAll().addOnSuccessListener { listResult ->

            for(item in listResult.items){
                item.getBytes(1024*1024).addOnSuccessListener { bytes ->
                    val mealData = bytes.toString(Charsets.UTF_8)
                    val meal = parseMealData(mealData)
                    lunchList.add(meal)
                    updateUI()
                }
                    .addOnFailureListener { exception ->
                        Log.e("fetchLunchData", "Failed to list items: ${exception.message}")
                    }
            }
        }
            .addOnFailureListener { exception ->
                Log.e("fetchLunchData", "Failed to list items: ${exception.message}")
            }
    }
    private fun fetchDinnerData() {
        // Firebase Storage에서 저녁 식사 데이터 가져오기
        val storage = MyApplication.storage
        val sharedPreferenceUser = requireContext().getSharedPreferences("user info", AppCompatActivity.MODE_PRIVATE)
        val email = sharedPreferenceUser.getString("email", "")
        val storageRef = storage.reference.child("${email}/meals/dinner")
        storageRef.listAll().addOnSuccessListener { listResult ->
            for(item in listResult.items){
                item.getBytes(1024*1024).addOnSuccessListener { bytes ->
                    val mealData = bytes.toString(Charsets.UTF_8)
                    val meal = parseMealData(mealData)
                    dinnerList.add(meal)
                    updateUI()
                }
                    .addOnFailureListener { exception ->
                        Log.e("fetchDinnerData", "Failed to list items: ${exception.message}")
                    }
            }
        }
            .addOnFailureListener { exception ->
                Log.e("fetchDinnerData", "Failed to list items: ${exception.message}")
            }
    }

    private fun updateUI(){
        if(context != null && isAdded){
            val sharedPreference = requireContext().getSharedPreferences("nutr info", MODE_PRIVATE)
            val recommendedCalorie = sharedPreference.getInt("recommendedCalorie", 0)
            val recommendedCarbohydrate = sharedPreference.getInt("recommendedCarbohydrate", 0)
            val recommendedProtein = sharedPreference.getInt("recommendedProtein", 0)
            val recommendedFat = sharedPreference.getInt("recommendedFat", 0)

            val binding = FragmentConsumptionBinding.bind(requireView())

            val totalCalorieAmount = breakfastList.sumBy { it.kcal } + lunchList.sumBy { it.kcal } + dinnerList.sumBy { it.kcal }
            val totalCaloriePercent = if (recommendedCalorie != 0) Math.round(totalCalorieAmount.toFloat() / recommendedCalorie * 100) else 0
            val carbohydrateG = breakfastList.sumBy { it.carbohydrate } + lunchList.sumBy { it.carbohydrate } + dinnerList.sumBy { it.carbohydrate }
            val carbohydratePercent = if (recommendedCarbohydrate != 0) Math.round(carbohydrateG.toFloat() / recommendedCarbohydrate * 100) else 0
            val proteinG = breakfastList.sumBy { it.protein } + lunchList.sumBy { it.protein } + dinnerList.sumBy { it.protein }
            val proteinPercent = if (recommendedProtein != 0) Math.round(proteinG.toFloat() / recommendedProtein * 100) else 0
            val fatG = breakfastList.sumBy { it.fat } + lunchList.sumBy { it.fat } + dinnerList.sumBy { it.fat }
            val fatPercent = if (recommendedFat != 0) Math.round(fatG.toFloat() / recommendedFat * 100) else 0

            binding.calorieKcal.text = totalCalorieAmount.toString() + "kcal"
            binding.caloriePercent.text = totalCaloriePercent.toString() + "%"
            binding.carbohydrateG.text = carbohydrateG.toString() + "g"
            binding.carbohydratePercent.text = carbohydratePercent.toString() + "%"
            binding.proteinG.text = proteinG.toString() + "g"
            binding.proteinPercent.text = proteinPercent.toString() + "%"
            binding.fatG.text = fatG.toString() + "g"
            binding.fatPercent.text = fatPercent.toString() + "%"
        }
    }

    private fun parseMealData(mealData: String) : Meal{
        val mealInfoList = mealData.split(",")
        val mealName = mealInfoList[0].substringAfter("Name: ").trim().toString()
        val mealSize = mealInfoList[1].substringAfter("Size: ").trim().toInt()
        val mealCalorie = mealInfoList[2].substringAfter("Kcal: ").trim().toInt()
        val mealCarbohydrate = mealInfoList[3].substringAfter("Carbohydrate: ").trim().toInt()
        val mealProtein = mealInfoList[4].substringAfter("Protein: ").trim().toInt()
        val mealFat = mealInfoList[5].substringAfter("Fat: ").trim().toInt()
        return Meal(mealName, mealSize, mealCalorie, mealCarbohydrate, mealProtein, mealFat)
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