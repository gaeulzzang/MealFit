package com.example.mealfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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

        binding.minus1.setOnClickListener{
            binding.carbohydrateNum.text = (binding.carbohydrateNum.text.toString().toInt() - 1).toString()
        }
        binding.plus1.setOnClickListener{
            binding.carbohydrateNum.text = (binding.carbohydrateNum.text.toString().toInt() + 1).toString()
        }
        binding.minus2.setOnClickListener{
            binding.proteinNum.text = (binding.proteinNum.text.toString().toInt() - 1).toString()
        }
        binding.plus2.setOnClickListener{
            binding.proteinNum.text = (binding.proteinNum.text.toString().toInt() + 1).toString()
        }
        binding.minus3.setOnClickListener{
            binding.fatNum.text = (binding.fatNum.text.toString().toInt() - 1).toString()
        }
        binding.plus3.setOnClickListener{
            binding.fatNum.text = (binding.fatNum.text.toString().toInt() + 1).toString()
        }
        binding.nextButton.setOnClickListener{
            val intent = Intent(this, InitSettingActivity4::class.java)
            try {
                val carbohydrate = binding.carbohydrateNum.text.toString().toInt()
                val protein = binding.proteinNum.text.toString().toInt()
                val fat = binding.fatNum.text.toString().toInt()

                if (carbohydrate + protein + fat != 10) {
                    // 합이 10이 아니면 처리
                    // 여기에서는 기본값인 5:3:2으로 설정하여 이동하도록 함
                    intent.putExtra("carbohydrate", 5)
                    intent.putExtra("protein", 3)
                    intent.putExtra("fat", 2)
                } else {
                    // 이외일 경우 정상적으로 값을 넘겨줌
                    intent.putExtra("carbohydrate", carbohydrate)
                    intent.putExtra("protein", protein)
                    intent.putExtra("fat", fat)
                    intent.putExtra("intakeCalorie", intakeCalorie)
                }
                startActivity(intent)
            } catch (e: NumberFormatException) {
                // 숫자로 변환할 수 없는 경우 처리
                // 여기에서는 기본값으로 설정하여 이동하도록 함
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