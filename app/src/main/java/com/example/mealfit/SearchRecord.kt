package com.example.mealfit

import android.R
import android.os.Bundle
import android.text.TextUtils.replace
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.RecordSearchBinding
data class Meal(val name: String, val size: Int, val kcal: Int, val carbohydrate: Int, val protein: Int, val fat: Int)

class SearchRecord : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = RecordSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val editTextSearch : EditText = binding.editTextSearch
        val menuRecyclerView : RecyclerView = binding.menuRecyclerView
        val menuList = arrayListOf(
            Meal("사과", 10, 10, 100, 100, 100),
            Meal("닭가슴살", 100, 100, 100, 100, 100)
        )

        val searchAdapter = SearchRecordAdapter(menuList)
        searchAdapter.notifyDataSetChanged()

        menuRecyclerView.adapter = searchAdapter
        menuRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        editTextSearch.addTextChangedListener(object: TextWatcher {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun afterTextChanged(s: android.text.Editable?) {

            }
        })
    }
}