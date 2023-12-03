package com.example.mealfit

import android.app.AlertDialog
import android.content.Intent
import android.health.connect.datatypes.MealType
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.FragmentListBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.Calendar

class ListFragment : Fragment() {
    private var currentDate = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentListBinding.inflate(layoutInflater, container, false)

        // 메뉴 추가하기 버튼을 누르면 메뉴 검색 페이지로 이동함
        binding.breakfastLayout.breakfastAddBtn.setOnClickListener{
            val intent = Intent(requireContext(), SearchRecord::class.java)
            startActivity(intent)
        }

        var breakfastList = mutableMapOf(
            "백미밥" to mutableMapOf(
                "g" to 300,
                "kcal" to 300,
                "탄수화물(g)" to 50,
                "단백질(g)" to 30,
                "지방(g)" to 10
            ),
            "닭가슴살" to mutableMapOf(
                "g" to 100,
                "kcal" to 200,
                "탄수화물(g)" to 50,
                "단백질(g)" to 30,
                "지방(g)" to 10
            ),
            "두부" to mutableMapOf(
                "g" to 50,
                "kcal" to 80,
                "탄수화물(g)" to 50,
                "단백질(g)" to 30,
                "지방(g)" to 10
            )
        )
        binding.breakfastLayout.breakfastCalorie.text = "열량 " + breakfastList.values.sumBy { it["kcal"] as Int }.toString() + "kcal"
        binding.breakfastLayout.breakfastCarbohydrate.text = "탄수화물 " + breakfastList.values.sumBy { it["탄수화물(g)"] as Int }.toString() + "g"
        binding.breakfastLayout.breakfastProtein.text = "단백질 " + breakfastList.values.sumBy { it["단백질(g)"] as Int }.toString() + "g"
        binding.breakfastLayout.breakfastFat.text = "지방 " + breakfastList.values.sumBy { it["지방(g)"] as Int }.toString() + "g"

        // 메뉴 추가하기 버튼을 누르면 메뉴 검색 페이지로 이동함
        binding.lunchLayout.lunchAddBtn.setOnClickListener{
            val intent = Intent(requireContext(), SearchRecord::class.java)
            startActivity(intent)
        }

        var lunchList = mutableMapOf(
            "닭가슴살 샐러드" to mutableMapOf(
                "g" to 300,
                "kcal" to 300,
                "탄수화물(g)" to 50,
                "단백질(g)" to 30,
                "지방(g)" to 10
            ),
            "과일 스무디" to mutableMapOf(
                "g" to 100,
                "kcal" to 200,
                "탄수화물(g)" to 50,
                "단백질(g)" to 30,
                "지방(g)" to 10
            ),
            "참치 샌드위치" to mutableMapOf(
                "g" to 50,
                "kcal" to 80,
                "탄수화물(g)" to 50,
                "단백질(g)" to 30,
                "지방(g)" to 10
            ),
            "백미밥" to mutableMapOf(
                "g" to 300,
                "kcal" to 300,
                "탄수화물(g)" to 50,
                "단백질(g)" to 30,
                "지방(g)" to 10
            ),
        )
        binding.lunchLayout.lunchCalorie.text = "열량 " + lunchList.values.sumBy { it["kcal"] as Int }.toString() + "kcal"
        binding.lunchLayout.lunchCarbohydrate.text = "탄수화물 " + lunchList.values.sumBy { it["탄수화물(g)"] as Int }.toString() + "g"
        binding.lunchLayout.lunchProtein.text = "단백질 " + lunchList.values.sumBy { it["단백질(g)"] as Int }.toString() + "g"
        binding.lunchLayout.lunchFat.text = "지방 " + lunchList.values.sumBy { it["지방(g)"] as Int }.toString() + "g"

        // 메뉴 추가하기 버튼을 누르면 메뉴 검색 페이지로 이동함
        binding.dinnerLayout.dinnerAddBtn.setOnClickListener{
            val intent = Intent(requireContext(), SearchRecord::class.java)
            startActivity(intent)
        }

        var dinnerList = mutableMapOf(
            "닭가슴살 샐러드" to mutableMapOf(
                "g" to 300,
                "kcal" to 300,
                "탄수화물(g)" to 50,
                "단백질(g)" to 30,
                "지방(g)" to 10
            ),
            "과일 스무디" to mutableMapOf(
                "g" to 100,
                "kcal" to 200,
                "탄수화물(g)" to 50,
                "단백질(g)" to 30,
                "지방(g)" to 10
            ),
            "참치 샌드위치" to mutableMapOf(
                "g" to 50,
                "kcal" to 80,
                "탄수화물(g)" to 50,
                "단백질(g)" to 30,
                "지방(g)" to 10
            )
        )
        binding.dinnerLayout.dinnerCalorie.text = "열량 " + dinnerList.values.sumBy { it["kcal"] as Int }.toString() + "kcal"
        binding.dinnerLayout.dinnerCarbohydrate.text = "탄수화물 " + dinnerList.values.sumBy { it["탄수화물(g)"] as Int }.toString() + "g"
        binding.dinnerLayout.dinnerProtein.text = "단백질 " + dinnerList.values.sumBy { it["단백질(g)"] as Int }.toString() + "g"
        binding.dinnerLayout.dinnerFat.text = "지방 " + dinnerList.values.sumBy { it["지방(g)"] as Int }.toString() + "g"

        val breakfastLayoutManager = LinearLayoutManager(activity)
        binding.breakfastLayout.breakfastListRecyclerView.layoutManager = breakfastLayoutManager
        val breakfastAdapter = BreakfastAdapter(breakfastList){updateSums()}
        binding.breakfastLayout.breakfastListRecyclerView.adapter = breakfastAdapter
        binding.breakfastLayout.breakfastListRecyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        val lunchLayoutManager = LinearLayoutManager(activity)
        binding.lunchLayout.lunchListRecyclerView.layoutManager = lunchLayoutManager
        binding.lunchLayout.lunchListRecyclerView.adapter = LunchAdapter(lunchList){updateSums()}
        binding.lunchLayout.lunchListRecyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        val dinnerLayoutManager = LinearLayoutManager(activity)
        binding.dinnerLayout.dinnerListRecyclerView.layoutManager = dinnerLayoutManager
        binding.dinnerLayout.dinnerListRecyclerView.adapter = DinnerAdapter(dinnerList){updateSums()}
        binding.dinnerLayout.dinnerListRecyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

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
                // '네'를 선택한 경우
                layout.visibility = View.GONE
                // 추가로 원하는 작업 수행 가능
            }
            setNegativeButton("아니오") { dialog, _ ->
                // '아니오'를 선택한 경우
                dialog.dismiss() // 다이얼로그 닫기
            }
            create().show() // 다이얼로그를 보여줍니다.
        }
    }
    data class Quadruple<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
    private fun calculateSum(adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>): Quadruple<Int, Int, Int, Int> {
        val mealList = when (adapter) {
            is BreakfastAdapter -> adapter.breakfastList
            is LunchAdapter -> adapter.lunchList
            is DinnerAdapter -> adapter.dinnerList
            else -> emptyMap()
        }

        val calorieSum = mealList.values.sumBy { it["kcal"] as Int }
        val carbohydrateSum = mealList.values.sumBy { it["탄수화물(g)"] as Int }
        val proteinSum = mealList.values.sumBy { it["단백질(g)"] as Int }
        val fatSum = mealList.values.sumBy { it["지방(g)"] as Int }

        return Quadruple(calorieSum, carbohydrateSum, proteinSum, fatSum)
    }
    private fun updateSums() {
        val binding = FragmentListBinding.bind(requireView())
        val breakfastAdapter = binding.breakfastLayout.breakfastListRecyclerView.adapter as BreakfastAdapter
        val lunchAdapter = binding.lunchLayout.lunchListRecyclerView.adapter as LunchAdapter
        val dinnerAdapter = binding.dinnerLayout.dinnerListRecyclerView.adapter as DinnerAdapter

        val (breakfastCalorieSum, breakfastCarbohydrateSum, breakfastProteinSum, breakfastFatSum) =
            breakfastAdapter?.let {calculateSum(it)}  ?: Quadruple(0, 0, 0, 0)
        binding.breakfastLayout.breakfastCalorie.text = "열량 $breakfastCalorieSum kcal"
        binding.breakfastLayout.breakfastCarbohydrate.text = "탄수화물 $breakfastCarbohydrateSum g"
        binding.breakfastLayout.breakfastProtein.text = "단백질 $breakfastProteinSum g"
        binding.breakfastLayout.breakfastFat.text = "지방 $breakfastFatSum g"

        val (lunchCalorieSum, lunchCarbohydrateSum, lunchProteinSum, lunchFatSum) =
            lunchAdapter?.let {calculateSum(it)}  ?: Quadruple(0, 0, 0, 0)
        binding.lunchLayout.lunchCalorie.text = "열량 $lunchCalorieSum kcal"
        binding.lunchLayout.lunchCarbohydrate.text = "탄수화물 $lunchCarbohydrateSum g"
        binding.lunchLayout.lunchProtein.text = "단백질 $lunchProteinSum g"
        binding.lunchLayout.lunchFat.text = "지방 $lunchFatSum g"

        val (dinnerCalorieSum, dinnerCarbohydrateSum, dinnerProteinSum, dinnerFatSum) =
            dinnerAdapter?.let {calculateSum(it)}  ?: Quadruple(0, 0, 0, 0)
        binding.dinnerLayout.dinnerCalorie.text = "열량 $dinnerCalorieSum kcal"
        binding.dinnerLayout.dinnerCarbohydrate.text = "탄수화물 $dinnerCarbohydrateSum g"
        binding.dinnerLayout.dinnerProtein.text = "단백질 $dinnerProteinSum g"
        binding.dinnerLayout.dinnerFat.text = "지방 $dinnerFatSum g"
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