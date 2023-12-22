package com.example.mealfit

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mealfit.databinding.RecordEnrollBinding
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class EnrollRecord: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = RecordEnrollBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu_close_clear_cancel)

        binding.enrollButton.setOnClickListener() {
            val newMenuName = binding.newMenuName.text.toString()
            val newMenuSize = binding.newMenuSize.text.toString()
            val newMenuCalorie = binding.newMenuCalorie.text.toString()
            val newMenuCarbohydrate = binding.newMenuCarbohydrate.text.toString()
            val newMenuProtein = binding.newMenuProtein.text.toString()
            val newMenuFat = binding.newMenuFat.text.toString()

            if (newMenuName.isEmpty() || newMenuSize.isEmpty() || newMenuCalorie.isEmpty() || newMenuCarbohydrate.isEmpty() || newMenuProtein.isEmpty() || newMenuFat.isEmpty()) {
                Toast.makeText(this, "입력하지 않은 내용이 있습니다", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    // Firebase Storage에 새로운 메뉴 정보 저장하는 로직 추가
                    val storage = MyApplication.storage

                    val sharedPreferenceUser = getSharedPreferences("user info", MODE_PRIVATE)
                    val email = sharedPreferenceUser.getString("email", "")

                    // SearchRecord로부터 받은 인텐트 값을 확인하여 저장 경로 설정
                    val isBreakfast = intent.getBooleanExtra("breakfast", false)
                    val isLunch = intent.getBooleanExtra("lunch", false)
                    val isDinner = intent.getBooleanExtra("dinner", false)
                    Log.d("EnrollRecord", "isBreakfast: $isBreakfast, isLunch: $isLunch, isDinner: $isDinner")
                    val storageRef = when {
                        isBreakfast -> storage.reference.child("${email}/meals/breakfast/${newMenuName}.txt")
                        isLunch -> storage.reference.child("${email}/meals/lunch/${newMenuName}.txt")
                        isDinner -> storage.reference.child("${email}/meals/dinner/${newMenuName}.txt")
                        else -> storage.reference.child("${email}/meals/unknown/${newMenuName}.txt")
                    }

                    val menuInfo =
                        "Name: $newMenuName, Size: $newMenuSize, Kcal: $newMenuCalorie, Carbohydrate: $newMenuCarbohydrate, Protein: $newMenuProtein, Fat: $newMenuFat"
                    val menuData = menuInfo.toByteArray()
                    val uploadTask = storageRef.putBytes(menuData)
                    uploadTask.addOnFailureListener {
                        Toast.makeText(this, "새로운 메뉴 정보를 저장하는 데 실패했습니다", Toast.LENGTH_SHORT).show()
                    }.addOnSuccessListener {
                        Toast.makeText(this, "새로운 메뉴 정보를 저장했습니다", Toast.LENGTH_SHORT).show()
                        // 저장 성공 시 SearchRecord로 돌아감
                        val intent = Intent(this, SearchRecord::class.java)
                        startActivity(intent)
                    }
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "올바른 숫자 형식으로 입력해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}