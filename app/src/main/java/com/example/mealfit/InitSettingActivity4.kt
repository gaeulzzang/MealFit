package com.example.mealfit

import android.content.Intent
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
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.title = "당신을 위한 맞춤 설정"
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black))
        binding.toolbar.navigationIcon?.setTint(resources.getColor(R.color.black))

        binding.nextButton.setOnClickListener{
            startActivity(Intent(this, MyPageActivity::class.java))
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