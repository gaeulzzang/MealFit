package com.example.mealfit

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.FragmentListBinding
import java.util.Calendar

class ListFragment : Fragment() {
    private var currentDate = Calendar.getInstance()
    private var mealSelectionListener: MealSelectionListener? = null
    private var isBreakfastVisible = false
    fun setMealSelectionListener(listener: MealSelectionListener){
        mealSelectionListener = listener
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean("breakfastVisibility", view?.findViewById<View>(R.id.breakfast_layout)?.visibility == View.VISIBLE)
        super.onSaveInstanceState(outState)
    }
    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val isBreakfastVisible = savedInstanceState?.getBoolean("breakfastVisibility", false)
        super.onViewStateRestored(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentListBinding.inflate(layoutInflater, container, false)
        if(isBreakfastVisible){
            binding.breakfastLayout.breakfastLayout.visibility = View.VISIBLE
        }
        // 메뉴 추가하기 버튼을 누르면 메뉴 검색 페이지로 이동함
        binding.breakfastLayout.breakfastAddBtn.setOnClickListener{
            val intent = Intent(requireContext(), SearchRecord::class.java)
            intent.putExtra("breakfast", true)
            startActivity(intent)
        }

        binding.lunchLayout.lunchAddBtn.setOnClickListener{
            val intent = Intent(requireContext(), SearchRecord::class.java)
            intent.putExtra("lunch", true)
            startActivity(intent)
        }

        binding.dinnerLayout.dinnerAddBtn.setOnClickListener{
            val intent = Intent(requireContext(), SearchRecord::class.java)
            intent.putExtra("dinner", true)
            startActivity(intent)
        }

        // 식사 삭제 버튼 누를 경우
        binding.breakfastLayout.breakfastDeleteBtn.setOnClickListener{
            createDeleteConfirmationDialog(binding.breakfastLayout.breakfastLayout, "아침")
        }

        binding.lunchLayout.lunchDeleteBtn.setOnClickListener{
            createDeleteConfirmationDialog(binding.lunchLayout.lunchLayout, "점심")
        }

        binding.dinnerLayout.dinnerDeleteBtn.setOnClickListener{
            createDeleteConfirmationDialog(binding.dinnerLayout.dinnerLayout, "저녁")
        }

        return binding.root
    }

    private fun createDeleteConfirmationDialog(layout: View, mealType: String) {
        val alertDialogBuilder = AlertDialog.Builder(requireContext())
        alertDialogBuilder.apply {
            setTitle("$mealType 식사 삭제 확인")
            setMessage("정말 삭제하시겠습니까?") // 사용자에게 표시할 메시지
            setPositiveButton("네") { _, _ ->
                layout.visibility = View.GONE
            }
            setNegativeButton("아니오") { dialog, _ ->
                dialog.dismiss() // 다이얼로그 닫기
            }
            create().show() // 다이얼로그를 보여줍니다.
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentListBinding.bind(view)
        binding.toolbar.title = "식사 기록"
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.date.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        updateDate()
        binding.arrowLeft?.setOnClickListener{
            currentDate.add(Calendar.DATE, -1)
            updateDate()
        }
        binding.arrowRight?.setOnClickListener{
            currentDate.add(Calendar.DATE, 1)
            updateDate()
        }
        binding.addMealFab.setOnClickListener{
            val items = arrayOf<String>("아침", "점심", "저녁")
            AlertDialog.Builder(requireContext()).run{
                setTitle("식사 추가하기")
                setItems(items){ _, which ->
                    when(which){
                        0 -> {
                            if (binding.breakfastLayout.breakfastLayout.visibility != View.VISIBLE) {
                                Toast.makeText(requireContext(), "아침 식사 추가", Toast.LENGTH_SHORT).show()
                                binding.breakfastLayout.breakfastLayout.visibility = View.VISIBLE
                                isBreakfastVisible = true
                            } else {
                                Toast.makeText(requireContext(), "이미 아침 식사가 존재합니다", Toast.LENGTH_SHORT).show()
                            }
                        }
                        1 -> {
                            if (binding.lunchLayout.lunchLayout.visibility != View.VISIBLE) {
                                Toast.makeText(requireContext(), "점심 식사 추가", Toast.LENGTH_SHORT).show()
                                binding.lunchLayout.lunchLayout.visibility = View.VISIBLE
                            } else {
                                Toast.makeText(requireContext(), "이미 점심 식사가 존재합니다", Toast.LENGTH_SHORT).show()
                            }
                        }
                        2 -> {
                            if (binding.dinnerLayout.dinnerLayout.visibility != View.VISIBLE) {
                                Toast.makeText(requireContext(), "저녁 식사 추가", Toast.LENGTH_SHORT).show()
                                binding.dinnerLayout.dinnerLayout.visibility = View.VISIBLE
                            } else {
                                Toast.makeText(requireContext(), "이미 저녁 식사가 존재합니다", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    // 적어도 하나가 visible이면 empty_text를 gone으로 설정
                        if(binding.breakfastLayout.breakfastLayout.visibility == View.VISIBLE ||
                            binding.lunchLayout.lunchLayout.visibility == View.VISIBLE ||
                            binding.dinnerLayout.dinnerLayout.visibility == View.VISIBLE){
                            binding.emptyText.visibility = View.GONE
                        } else{
                            binding.emptyText.visibility = View.VISIBLE
                        }
                }
                show()
            }
        }
        fetchBreakfastData()
        fetchLunchData()
        fetchDinnerData()
    }

    private fun fetchBreakfastData() {
        // Firebase Storage에서 아침 식사 데이터 가져오기
        val storage = MyApplication.storage
        val storageRef = storage.reference.child("meals/breakfast")
        storageRef.listAll().addOnSuccessListener { listResult ->
            val breakfastList = mutableListOf<Meal>()
            for(item in listResult.items){
                item.getBytes(1024*1024).addOnSuccessListener { bytes ->
                    val mealData = bytes.toString(Charsets.UTF_8)
                    val meal = parseMealData(mealData)
                    breakfastList.add(meal)

                    // RecyclerView Adapter 업데이트
                    val binding = FragmentListBinding.bind(requireView())
                    val breakfastAdapter = BreakfastAdapter(breakfastList){updateSums()}
                    binding.breakfastLayout.breakfastListRecyclerView.adapter = breakfastAdapter
                    binding.breakfastLayout.breakfastListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.breakfastLayout.breakfastListRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
                    updateSums()
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
        val storageRef = storage.reference.child("meals/lunch")
        storageRef.listAll().addOnSuccessListener { listResult ->
            val lunchList = mutableListOf<Meal>()
            for(item in listResult.items){
                item.getBytes(1024*1024).addOnSuccessListener { bytes ->
                    val mealData = bytes.toString(Charsets.UTF_8)
                    val meal = parseMealData(mealData)
                    lunchList.add(meal)

                    // RecyclerView Adapter 업데이트
                    val binding = FragmentListBinding.bind(requireView())
                    val lunchAdapter = LunchAdapter(lunchList){updateSums()}
                    binding.lunchLayout.lunchListRecyclerView.adapter = lunchAdapter
                    binding.lunchLayout.lunchListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.lunchLayout.lunchListRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
                    updateSums()
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
        val storageRef = storage.reference.child("meals/dinner")
        storageRef.listAll().addOnSuccessListener { listResult ->
            val dinnerList = mutableListOf<Meal>()
            for(item in listResult.items){
                item.getBytes(1024*1024).addOnSuccessListener { bytes ->
                    val mealData = bytes.toString(Charsets.UTF_8)
                    val meal = parseMealData(mealData)
                    dinnerList.add(meal)

                    // RecyclerView Adapter 업데이트
                    val binding = FragmentListBinding.bind(requireView())
                    val dinnerAdapter = DinnerAdapter(dinnerList){updateSums()}
                    binding.dinnerLayout.dinnerListRecyclerView.adapter = dinnerAdapter
                    binding.dinnerLayout.dinnerListRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    binding.dinnerLayout.dinnerListRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
                    updateSums()
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

    private fun calculateSum(mealList : MutableList<Meal>) : Meal{

        val calorieSum = mealList.sumBy { it.kcal }
        val carbohydrateSum = mealList.sumBy { it.carbohydrate }
        val proteinSum = mealList.sumBy { it.protein }
        val fatSum = mealList.sumBy { it.fat }
        Log.d("calculateSum", "calorieSum: $calorieSum, carbohydrateSum: $carbohydrateSum, proteinSum: $proteinSum, fatSum: $fatSum")
        return Meal("Total", 0, calorieSum, carbohydrateSum, proteinSum, fatSum)
    }
    private fun updateSums() {
        // 아침, 점심, 저녁 식사의 합계 업데이트
        val binding = FragmentListBinding.bind(requireView())
        val breakfastAdapter = binding.breakfastLayout.breakfastListRecyclerView.adapter as? BreakfastAdapter
        val lunchAdapter = binding.lunchLayout.lunchListRecyclerView.adapter as? LunchAdapter
        val dinnerAdapter = binding.dinnerLayout.dinnerListRecyclerView.adapter as? DinnerAdapter

        val totalBreakfast = breakfastAdapter?.let { calculateSum(it.breakfastList) } ?: Meal("Total", 0, 0, 0, 0, 0)
        binding.breakfastLayout.breakfastCalorie.text = "열량 ${totalBreakfast.kcal} kcal"
        binding.breakfastLayout.breakfastCarbohydrate.text = "탄수화물 ${totalBreakfast.carbohydrate} g"
        binding.breakfastLayout.breakfastProtein.text = "단백질 ${totalBreakfast.protein} g"
        binding.breakfastLayout.breakfastFat.text = "지방 ${totalBreakfast.fat} g"

        val totalLunch = lunchAdapter?.let { calculateSum(it.lunchList) } ?: Meal("Total", 0, 0, 0, 0, 0)
        binding.lunchLayout.lunchCalorie.text = "열량 ${totalLunch.kcal} kcal"
        binding.lunchLayout.lunchCarbohydrate.text = "탄수화물 ${totalLunch.carbohydrate} g"
        binding.lunchLayout.lunchProtein.text = "단백질 ${totalLunch.protein} g"
        binding.lunchLayout.lunchFat.text = "지방 ${totalLunch.fat} g"

        val totalDinner = dinnerAdapter?.let { calculateSum(it.dinnerList) } ?: Meal("Total", 0, 0, 0, 0, 0)
        binding.dinnerLayout.dinnerCalorie.text = "열량 ${totalDinner.kcal} kcal"
        binding.dinnerLayout.dinnerCarbohydrate.text = "탄수화물 ${totalDinner.carbohydrate} g"
        binding.dinnerLayout.dinnerProtein.text = "단백질 ${totalDinner.protein} g"
        binding.dinnerLayout.dinnerFat.text = "지방 ${totalDinner.fat} g"
    }

    override fun onResume() {
        super.onResume()
        val binding = FragmentListBinding.bind(requireView())

        binding.toolbar.title = "식사 기록"
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        binding.date.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
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