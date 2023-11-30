package com.example.mealfit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.ListRecyclerviewBinding

class LunchViewHolder(val binding: ListRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root){
}
class LunchAdapter(private val lunchList: MutableMap<String, MutableMap<String, Int>>,
    private val onListEmptyCallback: () -> Unit):
RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int = lunchList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = LunchViewHolder(ListRecyclerviewBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as LunchViewHolder).binding
        if(position < lunchList.size){
            val key = lunchList.keys.elementAt(position)
            binding.menuName.text = key
            binding.menuAmount.text = lunchList[key]?.get("g").toString() + "g"
            binding.menuCalorie.text = lunchList[key]?.get("kcal").toString() + "kcal"
            binding.menuDeleteBtn.setOnClickListener{
                lunchList.remove(key)
                notifyItemRemoved(position)
            }
        }
    }
    fun clearData(){
        val wasEmpty = lunchList.isEmpty()
        lunchList.clear()
        notifyDataSetChanged()
        onListEmptyCallback.invoke()
    }
}