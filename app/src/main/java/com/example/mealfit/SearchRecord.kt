package com.example.mealfit

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
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

data class Meal(val name: String, val size: Int, val kcal: Int, val carbohydrate: Int, val protein: Int, val fat: Int)

class SearchRecord : AppCompatActivity(){
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
        var menuList = arrayListOf(
            Meal("사과", 10, 10, 100, 100, 100),
            Meal("닭가슴살", 100, 100, 100, 100, 100)
        )
        val enrollStartButton: TextView = binding.enrollStartButton

        val searchAdapter = SearchRecordAdapter(menuList)
        menuRecyclerView.adapter = searchAdapter
        menuRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        editTextSearch.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun afterTextChanged(s: android.text.Editable?) {
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
    }

    // 뒤로 가기 버튼 클릭 시 기록 탭으로 돌아옴
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}