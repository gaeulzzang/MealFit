package com.example.mealfit

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.RecordSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.apache.poi.hssf.usermodel.HSSFCell
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*

data class Meal(
    val name: String,
    val size: Int,
    val kcal: Int,
    val carbohydrate: Int,
    val protein: Int,
    val fat: Int)
interface MealSelectionListener{
    fun onMealSelected(meal: Meal)
}
class SearchRecord : AppCompatActivity(), SearchRecordAdapter.OnItemClickListener,
MealSelectionListener{
    private var menuList = arrayListOf<Meal>()
//    식단 api 불러오기
//    private fun getFoodInfo(){
//
//        FoodObject.foodService.getFoodInfo("71bdb826d7084a3cb4c7", "I2790", "json").enqueue(object : Callback<List<FoodInfo>> {
//            override fun onResponse(call: Call<List<FoodInfo>>, response: Response<List<FoodInfo>>) {
//                if(response.isSuccessful){
//                    response.body()?.let {
//                        Log.d("SearchRecord", "onResponse: ${it}")
//                        Toast.makeText(this@SearchRecord, "데이터", Toast.LENGTH_SHORT).show()
//                    }
//                }
//                else{
//                    Log.d("retrofit onResponse", "${response.code()}")
//                    Toast.makeText(this@SearchRecord, "데이터를 불러오는 데 실패했습니다.1", Toast.LENGTH_SHORT).show()
//                }
//            }
//            override fun onFailure(call: Call<List<FoodInfo>>, t: Throwable) {
//                Log.d("retrofit onFaliure", "${t.message}")
//                Toast.makeText(this@SearchRecord, "데이터를 불러오는 데 실패했습니다.2", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = RecordSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_close_clear_cancel)

//        getFoodInfo()

        val editTextSearch : EditText = binding.editTextSearch
        val menuRecyclerView : RecyclerView = binding.menuRecyclerView
        menuList = readExcelFileFromAssets()
        val searchAdapter = SearchRecordAdapter(menuList, this)
        val finBtn : ImageButton = binding.finBtn
        val enrollStartButton: TextView = binding.enrollStartButton
        menuRecyclerView.adapter = searchAdapter
        menuRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val listFragment = ListFragment()
        listFragment.setMealSelectionListener(this)
        editTextSearch.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun afterTextChanged(s: Editable?) {
                val searchWord = s.toString()
                val searchList = arrayListOf<Meal>()
                if (searchWord.length > 0) {
                    for (i in 0 until menuList.size) {
                        if (menuList[i].name.contains(searchWord)) {
                            searchList.add(menuList[i])
                        }
                    }
                }
                else {
                    for (i in 0 until menuList.size) {
                        searchList.add(menuList[i])
                    }
                }
                searchAdapter.menuList = searchList
                searchAdapter.notifyDataSetChanged()
            }
        })

        enrollStartButton.setOnClickListener() {
            val intent = Intent(this, EnrollRecord::class.java)
            startActivity(intent)
        }

        finBtn.setOnClickListener(){
            val intent = Intent(this, ListFragment::class.java)
            startActivity(intent)
        }
    }

    // 뒤로 가기 버튼 클릭 시 기록 탭으로 돌아옴
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onItemClick(position: Int) {
        val selectedMeal = menuList[position]
        // 선택한 Meal을 ListFragment로 전달
        onMealSelected(selectedMeal)
    }

    override fun onMealSelected(meal: Meal) {
        val listFragment = ListFragment()
        Toast.makeText(this, "선택된 메뉴: ${meal.name}", Toast.LENGTH_SHORT).show()
        //listFragment?.displaySelectedMeal(meal)
    }
    private fun readExcelFileFromAssets() : ArrayList<Meal> {
    // Excel 파일에서 데이터 읽기
        val inputStream = assets.open("foodDB.xls")
        val workbook = HSSFWorkbook(inputStream)

        val sheet : Sheet = workbook.getSheetAt(0)
        val meals = arrayListOf<Meal>()
        for(rowIndex in 1..sheet.lastRowNum){
            val row = sheet.getRow(rowIndex)
            val name = row.getCell(1).stringCellValue
            val size = getValueFromCell(row.getCell(2))
            val kcal = getValueFromCell(row.getCell(3))
            val carbohydrate = getValueFromCell(row.getCell(4))
            val protein = getValueFromCell(row.getCell(5))
            val fat = getValueFromCell(row.getCell(6))
            meals.add(Meal(name, size, kcal, carbohydrate, protein, fat))
        }
        inputStream.close()
        return meals
    }

    private fun getValueFromCell(cell: Cell): Int {
        return when(cell?.cellType){
            HSSFCell.CELL_TYPE_NUMERIC -> cell.numericCellValue.toInt()
            HSSFCell.CELL_TYPE_STRING -> cell.stringCellValue.toDouble().toInt()
            else -> 0
        }
    }
}