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
            startActivity(Intent(this, InitSettingActivity4::class.java))
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