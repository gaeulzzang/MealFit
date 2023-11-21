package com.example.mealfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.example.mealfit.databinding.ActivityMainBinding
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.example.mealfit.databinding.ActivityInitSetting3Binding

class InitSettingActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityInitSetting3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityInitSetting3Binding.inflate(layoutInflater)
        val intakeCalorie : Int = intent.getIntExtra("intakeCalorie", 0)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.title = "당신을 위한 맞춤 설정"
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.navigationIcon?.setTint(resources.getColor(R.color.black))

        //var carbohydrateNum: TextView = findViewById(R.id.carbohydrate_num)
        var carbohydrateNum = binding.carbohydrateNum
        var proteinNum = binding.proteinNum
        var fatNum = binding.fatNum

        binding.minus1.setOnClickListener{
            carbohydrateNum.text = (carbohydrateNum.text.toString().toInt() - 1).toString()
        }
        binding.plus1.setOnClickListener{
            carbohydrateNum.text = (carbohydrateNum.text.toString().toInt() + 1).toString()
        }
        binding.minus2.setOnClickListener{
            proteinNum.text = (proteinNum.text.toString().toInt() - 1).toString()
        }
        binding.plus2.setOnClickListener{
            proteinNum.text = (proteinNum.text.toString().toInt() + 1).toString()
        }
        binding.minus3.setOnClickListener{
            fatNum.text = (fatNum.text.toString().toInt() - 1).toString()
        }
        binding.plus3.setOnClickListener{
            fatNum.text = (fatNum.text.toString().toInt() + 1).toString()
        }
        binding.nextButton.setOnClickListener{
            val intent = Intent(this, InitSettingActivity4::class.java)
            try {
                val carbohydrate : Int = carbohydrateNum.text.toString().toInt()
                val protein : Int = proteinNum.text.toString().toInt()
                val fat : Int = fatNum.text.toString().toInt()

                if (carbohydrate + protein + fat != 10)
                    Toast.makeText(this,"탄단지 비율의 합은 10이어야 합니다", Toast.LENGTH_SHORT).show()

                else { // 이외일 경우 정상적으로 값을 넘겨줌
                    intent.putExtra("carbohydrate", carbohydrate)
                    intent.putExtra("protein", protein)
                    intent.putExtra("fat", fat)
                    intent.putExtra("intakeCalorie", intakeCalorie)
                    startActivity(intent)
                }

            } catch (e: NumberFormatException) {
                // 숫자로 변환할 수 없는 경우 기본값으로 설정함
                Toast.makeText(this,"추천 비율로 구성합니다", Toast.LENGTH_SHORT).show()
                intent.putExtra("carbohydrate", 5)
                intent.putExtra("protein", 3)
                intent.putExtra("fat", 2)
                startActivity(intent)
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