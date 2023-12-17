package com.example.mealfit

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mealfit.databinding.RecordEnrollBinding

class EnrollRecord: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = RecordEnrollBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_close_clear_cancel)

        val enrollButton = binding.enrollButton
        val newMenuName = binding.newMenuName.toString()
        val newMenuSize = binding.newMenuSize.toString()
        val newMenuCalorie = binding.newMenuCalorie.toString()
        val newMenuCarbohydrate = binding.newMenuCarbohydrate.toString()
        val newMenuProtein = binding.newMenuProtein.toString()
        val newMenuFat = binding.newMenuFat.toString()

        enrollButton.setOnClickListener(){
            if(newMenuName == "" || newMenuSize == "" || newMenuCalorie == "" || newMenuCarbohydrate == "" || newMenuProtein == "" || newMenuFat == ""){
                Toast.makeText(this, "입력하지 않은 내용이 있습니다", Toast.LENGTH_SHORT).show()
            }
            try {
                // 입력값을 Intent에 담아서 이전 액티비티로 전달
                val intent = Intent(this, ListFragment::class.java)
                intent.putExtra("newMenuName", newMenuName)
                intent.putExtra("newMenuSize", newMenuSize.toInt())
                intent.putExtra("newMenuCalorie", newMenuCalorie.toInt())
                intent.putExtra("newMenuCarbohydrate", newMenuCarbohydrate.toInt())
                intent.putExtra("newMenuProtein", newMenuProtein.toInt())
                intent.putExtra("newMenuFat", newMenuFat.toInt())
                startActivity(intent)
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "올바른 숫자 형식으로 입력해 주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }
}