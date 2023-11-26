package com.example.mealfit

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.mealfit.databinding.ActivityInitSetting1Binding

class InitSettingActivity1 : AppCompatActivity() {
    private lateinit var binding: ActivityInitSetting1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInitSetting1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.title = "당신을 위한 맞춤 설정"
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.navigationIcon?.setTint(resources.getColor(R.color.black))

        val nickname: EditText = binding.nickname
        val inputGender: RadioGroup = findViewById(R.id.genderGroup)
        val gender: String = when (inputGender.checkedRadioButtonId) {
            R.id.male -> "남자"
            R.id.female -> "여자"
            else -> "여자" // default
        }
        val inputAge: EditText = binding.age
        val inputHeight: EditText = binding.height
        val inputWeight: EditText = binding.weight
        val inputExercise: Spinner = binding.exercise
        var exercise = "거의 운동하지 않음"
        inputExercise.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long){
                exercise = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
                exercise = "거의 운동하지 않음"
            }
        }

        binding.nextButton.setOnClickListener{
            val nickname: String = nickname.text.toString()
            val age: String = inputAge.text.toString()
            val height: String = inputHeight.text.toString()
            val weight: String = inputWeight.text.toString()
            val exercise: String = inputExercise.selectedItem?.toString() ?: "거의 운동하지 않음" // default
            if(nickname == "" || age == "" || height == "" || weight == ""){ // 닉네임, 나이, 키, 몸무게를 입력하지 않았을 때
                Toast.makeText(this, "입력하지 않은 내용이 있습니다", Toast.LENGTH_SHORT).show()
            }
            else{
                try {
                    val sharedPreference = getSharedPreferences("user info", MODE_PRIVATE)
                    val editor : SharedPreferences.Editor = sharedPreference.edit()
                    editor.putString("nickname", nickname)
                    editor.putString("age", age)
                    editor.putString("height", height)
                    editor.putString("weight", weight)
                    editor.commit()

                    // 입력값을 Intent에 담아서 다음 액티비티로 전달
                    val intent = Intent(this, InitSettingActivity2::class.java)
                    intent.putExtra("gender", gender)
                    intent.putExtra("age", age.toInt())
                    intent.putExtra("height", height.toInt())
                    intent.putExtra("weight", weight.toInt())
                    intent.putExtra("exercise", exercise)
                    startActivity(intent)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "올바른 숫자 형식으로 입력해 주세요", Toast.LENGTH_SHORT).show()
                }
            }
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