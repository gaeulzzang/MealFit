package com.example.mealfit

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealfit.databinding.FragmentHomeBinding
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.*
import java.io.File
import java.io.FileInputStream
import java.util.Calendar
import kotlin.random.Random

class HomeFragment : Fragment() {
    private var currentDate = Calendar.getInstance()
    val breakfastList = mutableListOf<Meal>()
    val lunchList = mutableListOf<Meal>()
    val dinnerList = mutableListOf<Meal>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        val toolbar = requireActivity().findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        toolbar.title = "오늘의 추천 식단"
        toolbar.setTitleTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        val date = requireActivity().findViewById<android.widget.TextView>(R.id.date)
        date.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        updateDate()
        view.findViewById<android.widget.ImageButton>(R.id.arrow_left)?.setOnClickListener{
            currentDate.add(Calendar.DATE, -1)
            updateDate()

            if(currentDate.get(Calendar.DAY_OF_MONTH) != Calendar.getInstance().get(Calendar.DAY_OF_MONTH) ||
                currentDate.get(Calendar.DATE) != Calendar.getInstance().get(Calendar.DATE)){
                binding.homeRecyclerView.visibility = View.GONE
                binding.text1.visibility = View.GONE
                binding.text2.visibility = View.GONE
                Toast.makeText(requireContext(), "서비스 준비 중입니다", Toast.LENGTH_SHORT).show()
            }
            else{
                binding.homeRecyclerView.visibility = View.VISIBLE
                binding.text1.visibility = View.VISIBLE
                binding.text2.visibility = View.VISIBLE
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
                binding.homeRecyclerView.visibility = View.GONE
                binding.text1.visibility = View.GONE
                binding.text2.visibility = View.GONE
                Toast.makeText(requireContext(), "서비스 준비 중입니다", Toast.LENGTH_SHORT).show()
            }
            else{
                binding.homeRecyclerView.visibility = View.VISIBLE
                binding.text1.visibility = View.VISIBLE
                binding.text2.visibility = View.VISIBLE
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
        toolbar.title = "오늘의 추천 식단"
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
    private fun updateUI(){
        if(context != null && isAdded){
            val sharedPreference = requireContext().getSharedPreferences("nutr info", Context.MODE_PRIVATE)
            val recommendedCarbohydrate = sharedPreference.getInt("recommendedCarbohydrate", 0)
            val recommendedProtein = sharedPreference.getInt("recommendedProtein", 0)
            val recommendedFat = sharedPreference.getInt("recommendedFat", 0)

            val carbohydrateG = breakfastList.sumBy { it.carbohydrate } + lunchList.sumBy { it.carbohydrate } + dinnerList.sumBy { it.carbohydrate }
            val carbohydratePercent = if (recommendedCarbohydrate != 0) Math.round(carbohydrateG.toFloat() / recommendedCarbohydrate * 100) else 0
            val proteinG = breakfastList.sumBy { it.protein } + lunchList.sumBy { it.protein } + dinnerList.sumBy { it.protein }
            val proteinPercent = if (recommendedProtein != 0) Math.round(proteinG.toFloat() / recommendedProtein * 100) else 0
            val fatG = breakfastList.sumBy { it.fat } + lunchList.sumBy { it.fat } + dinnerList.sumBy { it.fat }
            val fatPercent = if (recommendedFat != 0) Math.round(fatG.toFloat() / recommendedFat * 100) else 0

            val binding = FragmentHomeBinding.bind(requireView())
            if(carbohydratePercent > 100 && proteinPercent > 100 && fatPercent > 100){
                Toast.makeText(requireContext(), "탄수화물, 단백질, 지방 함량이 모두 권장량을 초과했습니다.", Toast.LENGTH_SHORT).show()
            }else{
                val smallestValue = minOf(carbohydratePercent, proteinPercent, fatPercent)
                when(smallestValue){
                    carbohydratePercent -> {
                        val carbohydrateMenuInfo = filterHighCarbohydrateFoods(recommendedCarbohydrate, carbohydrateG)
                        val layoutManager = LinearLayoutManager(activity)
                        val adapter = MyHomeAdapter(carbohydrateMenuInfo)
                        binding.homeRecyclerView.layoutManager = layoutManager
                        binding.homeRecyclerView.adapter = adapter
                        val toast = Toast.makeText(requireContext(), "탄수화물 위주의 식단을 추천합니다.", Toast.LENGTH_SHORT)
                        toast.show()
                        Handler().postDelayed(Runnable{
                            run(){ toast.cancel() } }, 700)
                    }
                    proteinPercent -> {
                        val proteinMenuInfo = filterHighProteinFoods(recommendedProtein, proteinG)
                        val layoutManager = LinearLayoutManager(activity)
                        val adapter = MyHomeAdapter(proteinMenuInfo)
                        binding.homeRecyclerView.layoutManager = layoutManager
                        binding.homeRecyclerView.adapter = adapter
                        val toast = Toast.makeText(requireContext(), "단백질 위주의 식단을 추천합니다.", Toast.LENGTH_SHORT)
                        toast.show()
                        Handler().postDelayed(Runnable{
                            run(){ toast.cancel() } }, 700)
                    }
                    fatPercent -> {
                        val fatMenuInfo = filterHighFatFoods(recommendedFat, fatG)
                        val layoutManager = LinearLayoutManager(activity)
                        val adapter = MyHomeAdapter(fatMenuInfo)
                        binding.homeRecyclerView.layoutManager = layoutManager
                        binding.homeRecyclerView.adapter = adapter
                        val toast = Toast.makeText(requireContext(), "지방 위주의 식단을 추천합니다.", Toast.LENGTH_SHORT)
                        toast.show()
                        Handler().postDelayed(Runnable{
                            run(){ toast.cancel() } }, 700)
                    }
                }
            }
        }
    }

    private fun filterHighCarbohydrateFoods(recommendedCarbohydrate: Int, carbohydrateG: Int) : ArrayList<Meal>{
        val inputStream = requireContext().assets.open("foodDB.xls")
        val workbook = HSSFWorkbook(inputStream)
        val sheet : Sheet = workbook.getSheetAt(0)

        val highCarbohydrateFoods = arrayListOf<Meal>()

        for (rowIndex in 1..sheet.lastRowNum) {
            val row = sheet.getRow(rowIndex)
            val carbohydrateCell = getValueFromCell(row.getCell(4)) // 탄수화물 함량이 있는 열의 인덱스
            val proteinCell = getValueFromCell(row.getCell(5))
            val fatCell = getValueFromCell(row.getCell(6))
            if (carbohydrateCell != null && carbohydrateCell + carbohydrateG <= recommendedCarbohydrate){
                val nameCell = row.getCell(1).stringCellValue // 음식 이름
                val sizeCell = getValueFromCell(row.getCell(2))
                val kcalCell = getValueFromCell(row.getCell(3))
                highCarbohydrateFoods.add(Meal(nameCell, sizeCell, kcalCell, carbohydrateCell, proteinCell, fatCell))
            }
        }

        inputStream.close()

        // 필터링된 탄수화물 음식 중 탄수화물이 가장 높은 상위 3개 리스트만 추린다.
        val sortedByCarbohydrate = highCarbohydrateFoods.sortedByDescending { it.carbohydrate }
        val top3HighCarbohydrateFoods = ArrayList(sortedByCarbohydrate.take(3))

        return top3HighCarbohydrateFoods
    }

    private fun filterHighProteinFoods(recommendedProtein: Int, proteinG: Int) : ArrayList<Meal> {
        val inputStream = requireContext().assets.open("foodDB.xls")
        val workbook = HSSFWorkbook(inputStream)
        val sheet : Sheet = workbook.getSheetAt(0)

        val highProteinFoods = arrayListOf<Meal>()

        for (rowIndex in 1..sheet.lastRowNum) {
            val row = sheet.getRow(rowIndex)
            val proteinCell = getValueFromCell(row.getCell(5)) // 단백질 함량이 있는 열의 인덱스
            if (proteinCell != null && proteinCell + proteinG <= recommendedProtein){
                val nameCell = row.getCell(1).stringCellValue // 음식 이름
                val sizeCell = getValueFromCell(row.getCell(2))
                val kcalCell = getValueFromCell(row.getCell(3))
                val carbohydrateCell = getValueFromCell(row.getCell(4))
                val fatCell = getValueFromCell(row.getCell(6))
                highProteinFoods.add(Meal(nameCell, sizeCell, kcalCell, carbohydrateCell, proteinCell, fatCell))
            }
        }

        inputStream.close()

        // 필터링된 단백질 음식 중 상위 3개 리스트만 추린다.
        val sortedByProtein = highProteinFoods.sortedByDescending { it.protein }
        val top3HighProteinFoods = ArrayList(sortedByProtein.take(3))
        return top3HighProteinFoods
    }
    private fun filterHighFatFoods(recommendedFat: Int, fatG: Int) : ArrayList<Meal>{
       val inputStream = requireContext().assets.open("foodDB.xls")
       val workbook = HSSFWorkbook(inputStream)
       val sheet : Sheet = workbook.getSheetAt(0)

       val highFatFoods = arrayListOf<Meal>()

       for (rowIndex in 1..sheet.lastRowNum) {
           val row = sheet.getRow(rowIndex)
           val fatCell = getValueFromCell(row.getCell(6)) // 지방 함량이 있는 열의 인덱스
           if (fatCell != null && fatCell + fatG <= recommendedFat){
               val nameCell = row.getCell(1).stringCellValue // 음식 이름
               val sizeCell = getValueFromCell(row.getCell(2))
               val kcalCell = getValueFromCell(row.getCell(3))
               val carbohydrateCell = getValueFromCell(row.getCell(4))
               val proteinCell = getValueFromCell(row.getCell(5))
               highFatFoods.add(Meal(nameCell, sizeCell, kcalCell, carbohydrateCell, proteinCell, fatCell))
           }
       }

       inputStream.close()

       // 필터링된 지방 음식 중 상위 3개 리스트만 추린다.
        val sortedByFat = highFatFoods.sortedByDescending { it.fat }
         val top3HighFatFoods = ArrayList(sortedByFat.take(3))
         return top3HighFatFoods
   }
    private fun getValueFromCell(cell: Cell): Int {
        return when(cell?.cellType){
            HSSFCell.CELL_TYPE_NUMERIC -> cell.numericCellValue.toInt()
            HSSFCell.CELL_TYPE_STRING -> cell.stringCellValue.toDouble().toInt()
            else -> 0
        }
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