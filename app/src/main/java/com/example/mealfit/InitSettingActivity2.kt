package com.example.mealfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mealfit.databinding.ActivityInitSetting1Binding
import com.example.mealfit.databinding.ActivityInitSetting2Binding

class InitSettingActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityInitSetting2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInitSetting2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val gender: String? = intent.getStringExtra("gender")
        val age: Int = intent.getIntExtra("age", 0)
        val height: Int = intent.getIntExtra("height", 0)
        val weight: Int = intent.getIntExtra("weight", 0)
        val exercise: String? = intent.getStringExtra("exercise")

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.title = "당신을 위한 맞춤 설정"
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.navigationIcon?.setTint(resources.getColor(R.color.black))

        val basalMetabolism: TextView = findViewById(R.id.basal_metabolism)
        val recommendedBasalMetabolism: Int =
            if(gender == "남자"){
                (66.47 + (13.75 * weight) + (5 * height) - (6.76 * age)).toInt()
            }
            else{
                (665.1 + (9.56 * weight) + (1.85 * height) - (4.68 * age)).toInt()
            }
        basalMetabolism.text = recommendedBasalMetabolism.toString() + " kcal"

        val activityMetabolism: TextView = findViewById(R.id.activity_metabolism)
        val recommendedActivityMetabolism: Int = when (exercise) {
            "거의 운동하지 않음" -> (recommendedBasalMetabolism * 0.2).toInt()
            "가벼운 활동(주 1~3회)" -> (recommendedBasalMetabolism * 0.375).toInt()
            "보통 활동(주 3~5회)" -> (recommendedBasalMetabolism * 0.555).toInt()
            "적극적인 활동(주 6~7회)" -> (recommendedBasalMetabolism * 0.725).toInt()
            "매우 적극적인 활동(주 7회)" -> (recommendedBasalMetabolism * 0.9).toInt()
            else -> (recommendedBasalMetabolism * 0.2).toInt() // default
        }
        activityMetabolism.text = recommendedActivityMetabolism.toString() + " kcal"

        val intakeCalorie: EditText = findViewById(R.id.intake_calorie)
        val recommendedIntakeCalorie: Int = when (exercise) {
            "거의 운동하지 않음" -> (recommendedBasalMetabolism * 1.2).toInt()
            "가벼운 활동(주 1~3회)" -> (recommendedBasalMetabolism * 1.375).toInt()
            "보통 활동(주 3~5회)" -> (recommendedBasalMetabolism * 1.55).toInt()
            "적극적인 활동(주 6~7회)" -> (recommendedBasalMetabolism * 1.725).toInt()
            "매우 적극적인 활동(주 7회)" -> (recommendedBasalMetabolism * 1.9).toInt()
            else -> (recommendedBasalMetabolism * 1.2).toInt() // default
        }
        intakeCalorie.hint = recommendedIntakeCalorie.toString() + " kcal"

        binding.nextButton.setOnClickListener{
            val intent = Intent(this, InitSettingActivity3::class.java)
            if (intakeCalorie.text.toString() == "") {
                intent.putExtra("intakeCalorie", recommendedIntakeCalorie)
            } else {
                intent.putExtra("intakeCalorie", intakeCalorie.text.toString().toInt())
            }
            startActivity(intent)
        }
    }
}