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
class MyHomeAdapter(private val menuInfo: Map<String, Map<String, Any>>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = menuInfo.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = MyHomeViewHolder(HomeRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyHomeViewHolder).binding
        binding.foodName.text = menuInfo.keys.elementAt(position)
        binding.foodCalorie.text = "열량 " + menuInfo[menuInfo.keys.elementAt(position)]?.get("열량(kcal)").toString() + "kcal"
        binding.foodCarbohydrate.text = "탄수화물 " + menuInfo[menuInfo.keys.elementAt(position)]?.get("탄수화물(g)").toString() + "g"
        binding.foodProtein.text = "단백질 " + menuInfo[menuInfo.keys.elementAt(position)]?.get("단백질(g)").toString() + "g"
        binding.foodFat.text = "지방 " + menuInfo[menuInfo.keys.elementAt(position)]?.get("지방(g)").toString() + "g"
    }
}
