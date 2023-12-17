package com.example.mealfit

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.example.mealfit.databinding.ActivityInitSetting3Binding
import com.example.mealfit.databinding.ActivityInitSetting4Binding

class InitSettingActivity4 : AppCompatActivity() {
    private lateinit var binding: ActivityInitSetting4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInitSetting4Binding.inflate(layoutInflater)
        val carbohydrate : Int = intent.getIntExtra("carbohydrate", 5)
        val protein : Int = intent.getIntExtra("protein", 3)
        val fat : Int = intent.getIntExtra("fat", 2)
        val intakeCalorie : Int = intent.getIntExtra("intakeCalorie", 0)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.title = "당신을 위한 맞춤 설정"
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.navigationIcon?.setTint(resources.getColor(R.color.black))

        var carbohydrateAmount = binding.carbohydrateAmount
        var proteinAmount = binding.proteinAmount
        var fatAmount = binding.fatAmount

        // 탄수화물, 단백질, 지방, 총 열량 계산하는 로직 구현하기
        val recommendedCarbohydrate = Math.round((intakeCalorie * (carbohydrate * 0.1) / 4))
        carbohydrateAmount.text = recommendedCarbohydrate.toString() + " g"
        val recommendedProtein = Math.round((intakeCalorie * (protein * 0.1) / 4))
        proteinAmount.text = recommendedProtein.toString() + " g"
        val recommendedFat = Math.round((intakeCalorie * (fat * 0.1) / 9))
        fatAmount.text = recommendedFat.toString() + " g"
        binding.totalCalorieAmount.text = intakeCalorie.toString() + " kcal"

        binding.nextButton.setOnClickListener{
            val sharedPreference = getSharedPreferences("nutr info", MODE_PRIVATE)
            val editor : SharedPreferences.Editor = sharedPreference.edit()
            editor.putInt("recommendedCarbohydrate", recommendedCarbohydrate.toInt())
            editor.putInt("recommendedProtein", recommendedProtein.toInt())
            editor.putInt("recommendedFat", recommendedFat.toInt())
            editor.putInt("recommendedCalorie", intakeCalorie)
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("previousActivity", "InitSettingActivity4")
            intent.putExtra("carbohydrateAmount", carbohydrateAmount.text.toString())
            intent.putExtra("proteinAmount", proteinAmount.text.toString())
            intent.putExtra("fatAmount", fatAmount.text.toString())
            intent.putExtra("totalCalorieAmount", binding.totalCalorieAmount.text.toString())
            startActivity(intent)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}