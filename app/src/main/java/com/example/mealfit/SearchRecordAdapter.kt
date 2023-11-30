package com.example.mealfit

import android.R
import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.MenuRecyclerviewBinding
import com.example.mealfit.databinding.RecordSearchBinding

class SearchRecordAdapter(val menuList: ArrayList<Meal>) : RecyclerView.Adapter<SearchRecordAdapter.Holder>() {
    override fun getItemCount(): Int = menuList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecordAdapter.Holder {
        val binding =
            MenuRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: SearchRecordAdapter.Holder, position: Int) {
        holder.menuName.text = menuList[position].name
        holder.menuSize.text = menuList[position].size.toString()
        holder.menuCalorie.text = menuList[position].kcal.toString()
    }

    inner class Holder(binding: MenuRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val menuName = binding.menuName
        val menuSize = binding.menuSize
        val menuCalorie = binding.menuCalorie
        val menuButton = binding.menuButton
    }
}
