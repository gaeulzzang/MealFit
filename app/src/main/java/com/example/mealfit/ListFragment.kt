package com.example.mealfit

import android.app.AlertDialog
import android.content.Intent
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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.FragmentListBinding
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ListFragment : Fragment() {
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
        val binding = FragmentListBinding.inflate(layoutInflater, container, false)
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
        val breakfastAdapter = BreakfastAdapter(breakfastList){
            binding.breakfastLayout.breakfastLayout.visibility = View.GONE
        }
        binding.breakfastLayout.breakfastListRecyclerView.adapter = breakfastAdapter
        binding.breakfastLayout.breakfastListRecyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        val lunchLayoutManager = LinearLayoutManager(activity)
        binding.lunchLayout.lunchListRecyclerView.layoutManager = lunchLayoutManager
        val lunchAdapter = LunchAdapter(lunchList){
            binding.lunchLayout.lunchLayout.visibility = View.GONE
        }
        binding.lunchLayout.lunchListRecyclerView.adapter = lunchAdapter
        binding.lunchLayout.lunchListRecyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        val dinnerLayoutManager = LinearLayoutManager(activity)
        binding.dinnerLayout.dinnerListRecyclerView.layoutManager = dinnerLayoutManager
        val dinnerAdapter = DinnerAdapter(dinnerList){
            binding.dinnerLayout.dinnerLayout.visibility = View.GONE
        }
        binding.dinnerLayout.dinnerListRecyclerView.adapter = dinnerAdapter
        binding.dinnerLayout.dinnerListRecyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        binding.breakfastLayout.breakfastDeleteBtn.setOnClickListener{
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.apply {
                setTitle("아침 식사 삭제 확인")
                setMessage("정말 삭제하시겠습니까?") // 사용자에게 표시할 메시지
                setPositiveButton("네") { _, _ ->
                    // '네'를 선택한 경우
                    binding.breakfastLayout.breakfastLayout.visibility = View.GONE

                    val adapter = binding.breakfastLayout.breakfastListRecyclerView.adapter as BreakfastAdapter
                    adapter.clearData()
                    adapter.notifyDataSetChanged()

                    // 열량과 단백질 텍스트 갱신
                    val calorieSum = breakfastList.values.sumBy { it["kcal"] as Int }
                    val carbohydrateSum = breakfastList.values.sumBy { it["탄수화물(g)"] as Int }
                    val proteinSum = breakfastList.values.sumBy { it["단백질(g)"] as Int }
                    val fatSum = breakfastList.values.sumBy { it["지방(g)"] as Int }

                    binding.breakfastLayout.breakfastCalorie.text = "열량 $calorieSum kcal"
                    binding.breakfastLayout.breakfastCarbohydrate.text = "탄수화물 $carbohydrateSum g"
                    binding.breakfastLayout.breakfastProtein.text = "단백질 $proteinSum g"
                    binding.breakfastLayout.breakfastFat.text = "지방 $fatSum g"
                }
                setNegativeButton("아니오") { dialog, _ ->
                    // '아니오'를 선택한 경우
                    dialog.dismiss() // 다이얼로그 닫기
                }
                create().show() // 다이얼로그를 보여줍니다.
            }
        }
        binding.lunchLayout.lunchDeleteBtn.setOnClickListener{
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.apply {
                setTitle("점심 식사 삭제 확인")
                setMessage("정말 삭제하시겠습니까?") // 사용자에게 표시할 메시지
                setPositiveButton("네") { _, _ ->
                    // '네'를 선택한 경우
                    binding.lunchLayout.lunchLayout.visibility = View.GONE

                    val adapter = binding.lunchLayout.lunchListRecyclerView.adapter as LunchAdapter
                    adapter.clearData()
                    adapter.notifyDataSetChanged()
                }
                setNegativeButton("아니오") { dialog, _ ->
                    // '아니오'를 선택한 경우
                    dialog.dismiss() // 다이얼로그 닫기
                }
                create().show() // 다이얼로그를 보여줍니다.
            }
        }
        binding.dinnerLayout.dinnerDeleteBtn.setOnClickListener{
            val alertDialogBuilder = AlertDialog.Builder(requireContext())
            alertDialogBuilder.apply {
                setTitle("저녁 식사 삭제 확인")
                setMessage("정말 삭제하시겠습니까?") // 사용자에게 표시할 메시지
                setPositiveButton("네") { _, _ ->
                    // '네'를 선택한 경우
                    binding.dinnerLayout.dinnerLayout.visibility = View.GONE

                    val adapter = binding.dinnerLayout.dinnerListRecyclerView.adapter as DinnerAdapter
                    adapter.clearData()
                    adapter.notifyDataSetChanged()
                }
                setNegativeButton("아니오") { dialog, _ ->
                    // '아니오'를 선택한 경우
                    dialog.dismiss() // 다이얼로그 닫기
                }
                create().show() // 다이얼로그를 보여줍니다.
            }
        }

        binding.breakfastLayout.breakfastAddBtn.setOnClickListener{
            val intent = Intent(activity, SearchRecord::class.java)
            startActivity(intent)
        }

        binding.lunchLayout.lunchAddBtn.setOnClickListener{
            // 점심 식사 추가
            Toast.makeText(requireContext(), "점심 식사 추가", Toast.LENGTH_SHORT).show()
        }
        binding.dinnerLayout.dinnerAddBtn.setOnClickListener{
            // 저녁 식사 추가
            Toast.makeText(requireContext(), "저녁 식사 추가", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.title = "식사 기록"
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
        toolbar.title = "식사 기록"
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
    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}