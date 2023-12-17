package com.example.mealfit

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.HomeRecyclerviewBinding

class MyHomeViewHolder(val binding: HomeRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
}
class MyHomeAdapter(val menuInfo: MutableList<Meal>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = menuInfo.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = MyHomeViewHolder(HomeRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyHomeViewHolder).binding
        val meal = menuInfo[position]
        binding.foodName.text = meal.name
        binding.foodCalorie.text = "열량 ${meal.kcal}kcal"
        binding.foodCarbohydrate.text = "탄수화물 ${meal.carbohydrate}g"
        binding.foodProtein.text = "단백질 ${meal.protein}g"
        binding.foodFat.text = "지방 ${meal.fat}g"
    }
}
