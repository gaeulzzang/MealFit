package com.example.mealfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.DrawableCompat.getColorFilter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mealfit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //private val key = "71bdb826d7084a3cb4c7"
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 이전 화면이 InitSettingActivity4일 경우 MyPageFragment로 이동
        val previousActivity = intent.getStringExtra("previousActivity")
        Log.d("MainActivity", "previousActivity: $previousActivity")
        if(previousActivity == "InitSettingActivity4"){
            Log.d("MainActivity", "Starting MyPageFragment")
            supportFragmentManager.beginTransaction().replace(R.id.containers, MyPageFragment()).commit()
            binding.bottomNavigation.selectedItemId = R.id.my_tab
            AlertDialog.Builder(this).run {
                setTitle("맞춤 설정 완료")
                val icon = resources.getDrawable(android.R.drawable.stat_notify_error, null)
                icon.setTint(resources.getColor(R.color.black))
                setIcon(icon)
                setPositiveButton("확인", null)
                show()
            }
        }
        // 그 외의 경우 HomeFragment로 이동
        else {
            Log.d("MainActivity", "Starting HomeFragment")
            supportFragmentManager.beginTransaction().replace(R.id.containers, HomeFragment()).commit()
            binding.bottomNavigation.selectedItemId = R.id.home_tab
        }
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.home_tab -> supportFragmentManager.beginTransaction().replace(R.id.containers, HomeFragment()).commit()
                R.id.list_tab -> supportFragmentManager.beginTransaction().replace(R.id.containers, ListFragment()).commit()
                R.id.consumption_tab -> supportFragmentManager.beginTransaction().replace(R.id.containers, ConsumptionFragment()).commit()
                R.id.my_tab -> supportFragmentManager.beginTransaction().replace(R.id.containers, MyPageFragment()).commit()
            }
            true
        }
    }



}