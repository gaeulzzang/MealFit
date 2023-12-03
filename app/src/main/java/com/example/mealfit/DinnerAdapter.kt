package com.example.mealfit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.ListRecyclerviewBinding

class DinnerViewHolder(val binding: ListRecyclerviewBinding):
    RecyclerView.ViewHolder(binding.root){

    }
class DinnerAdapter(public val dinnerList: MutableMap<String, MutableMap<String, Int>>,
                    private val onUpdateSums: () -> Unit):
RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int = dinnerList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = DinnerViewHolder(ListRecyclerviewBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as DinnerViewHolder).binding
        if(position < dinnerList.size){
            val key = dinnerList.keys.elementAt(position)
            binding.menuName.text = key
            binding.menuAmount.text = dinnerList[key]?.get("g").toString() + "g"
            binding.menuCalorie.text = dinnerList[key]?.get("kcal").toString() + "kcal"
            binding.menuDeleteBtn.setOnClickListener{
                dinnerList.remove(key)
                notifyDataSetChanged()
                onUpdateSums.invoke()
            }
        }
    }
}