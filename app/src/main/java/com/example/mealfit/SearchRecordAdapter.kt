package com.example.mealfit

import android.R
import android.app.Activity
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.MenuRecyclerviewBinding
import com.example.mealfit.databinding.RecordSearchBinding

class SearchRecordAdapter(var menuList: ArrayList<Meal>,
    private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<SearchRecordAdapter.Holder>() {
    private val selectedItems = mutableSetOf<Meal>()
    override fun getItemCount(): Int = menuList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRecordAdapter.Holder {
        val binding =
            MenuRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: SearchRecordAdapter.Holder, position: Int) {
        holder.menuName.text = menuList[position].name
        holder.menuSize.text = menuList[position].size.toString()+"g"
        holder.menuCalorie.text = menuList[position].kcal.toString()+"kcal"
        holder.itemView.setOnClickListener{
            itemClickListener.onItemClick(position)
        }

        val currentItem = menuList[position]
        holder.menuAddBtn.setOnClickListener {
            if (holder.menuAddBtn.visibility == View.VISIBLE) {
                if (selectedItems.contains(currentItem)) {
                    selectedItems.remove(currentItem)
                    holder.itemView.setBackgroundColor(Color.WHITE)
                } else {
                    selectedItems.add(currentItem)
                    holder.itemView.setBackgroundColor(Color.LTGRAY)
                }

                //onItemClick(currentItem)

                // 버튼을 숨김
                holder.menuAddBtn.visibility = View.INVISIBLE
            }
        }
    }

    inner class Holder(binding: MenuRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) {
        val menuName = binding.menuName
        val menuSize = binding.menuSize
        val menuCalorie = binding.menuCalorie
        val menuAddBtn = binding.menuAddBtn
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
