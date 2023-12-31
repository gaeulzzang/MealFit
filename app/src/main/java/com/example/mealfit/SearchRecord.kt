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
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.RecordSearchBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.storage.FirebaseStorage
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
    var isBreakfast = false
    var isLunch = false
    var isDinner = false
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

    private var searchAdapter = SearchRecordAdapter(menuList, this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = RecordSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        getFoodInfo()

        val editTextSearch : EditText = binding.editTextSearch
        val menuRecyclerView : RecyclerView = binding.menuRecyclerView
        menuList = readExcelFileFromAssets()
        searchAdapter = SearchRecordAdapter(menuList, this)
        val enrollStartBtn: TextView = binding.enrollStartBtn
        val exitBtn: ImageButton = binding.exitBtn
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
        isBreakfast = intent.getBooleanExtra("breakfast", false)
        isLunch = intent.getBooleanExtra("lunch", false)
        isDinner = intent.getBooleanExtra("dinner", false)

        exitBtn.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("previousActivity", "SearchRecord")
            startActivity(intent)
            //supportFragmentManager.beginTransaction().replace(com.example.mealfit.R.id.containers, ListFragment()).commit()
        }

        enrollStartBtn.setOnClickListener() {
            val intent = Intent(this, EnrollRecord::class.java)
            // 현재 액티비티의 인텐트 정보를 가져와서 EnrollRecord로 전달
            intent.putExtra("breakfast", isBreakfast)
            intent.putExtra("lunch", isLunch)
            intent.putExtra("dinner", isDinner)
            startActivity(intent)
        }
    }

    // 뒤로 가기 버튼 클릭 시 기록 탭으로 돌아옴

    override fun onItemClick(position: Int) {
        val selectedMeal = searchAdapter.menuList[position]
        onMealSelected(selectedMeal)
    }

    override fun onMealSelected(meal: Meal) {
        // Firebase Storage에 선택한 식사 저장
        val storage = MyApplication.storage

        val sharedPreferenceUser = getSharedPreferences("user info", MODE_PRIVATE)
        val email = sharedPreferenceUser.getString("email", "")

        val isBreakfast = intent.getBooleanExtra("breakfast", false)
        val isLunch = intent.getBooleanExtra("lunch", false)
        val isDinner = intent.getBooleanExtra("dinner", false)

        val storageRef = when{
            isBreakfast -> storage.reference.child("${email}/meals/breakfast/${meal.name}.txt")
            isLunch -> storage.reference.child("${email}/meals/lunch/${meal.name}.txt")
            isDinner -> storage.reference.child("${email}/meals/dinner/${meal.name}.txt")
            else -> storage.reference.child("${email}/meals/${meal.name}.txt")
        }
        // 식사 정보 문자열로 변환
        val mealInfo = "Name: ${meal.name}, Size: ${meal.size}, Kcal: ${meal.kcal}, Carbohydrate: ${meal.carbohydrate}, Protein: ${meal.protein}, Fat: ${meal.fat}"
        val mealData = mealInfo.toByteArray() // 문자열을 ByteArray로 변환

        val uploadTask = storageRef.putBytes(mealData) // Firebase Storage에 업로드
        uploadTask.addOnFailureListener {
            Log.d("SearchRecord", "onFailure: ${it}")
            Toast.makeText(this, "'${meal.name}' 을/를 저장하는 데 실패했습니다.", Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            Toast.makeText(this, "'${meal.name}' 을/를 저장했습니다.", Toast.LENGTH_SHORT).show()
            // ListFragment로 돌아가기
        }
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