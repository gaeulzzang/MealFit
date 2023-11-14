package com.example.mealfit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mealfit.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // 이전화면이 InitSettingActivity4일 경우 MyPageFragment로 이동
        val previousActivity = intent.getStringExtra("previousActivity")
        Log.d("MainActivity", "previousActivity: $previousActivity")
        if(previousActivity == "InitSettingActivity4"){
            Log.d("MainActivity", "Starting MyPageFragment")
            supportFragmentManager.beginTransaction().replace(R.id.containers, MyPageFragment()).commit()
            binding.bottomNavigation.selectedItemId = R.id.my_tab
//            AlertDialog.Builder(this)
//                .setTitle("맞춤 설정 완료")
//                .setMessage("맞춤 설정이 완료되었습니다.")
//                .setPositiveButton("확인") { _, _ -> }
//                .show()
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