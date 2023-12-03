package com.example.mealfit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.BreakfastLayoutBinding
import com.example.mealfit.databinding.FragmentListBinding
import com.example.mealfit.databinding.ListRecyclerviewBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class BreakfastViewHolder(val binding: ListRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root){

}
class BreakfastAdapter(public val breakfastList: MutableMap<String, MutableMap<String, Int>>,
                       private val onUpdateSums: () -> Unit):
RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = breakfastList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = BreakfastViewHolder(ListRecyclerviewBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as BreakfastViewHolder).binding

        val key = breakfastList.keys.elementAt(position)
        binding.menuName.text = key
        binding.menuAmount.text = breakfastList[key]?.get("g").toString() + "g"
        binding.menuCalorie.text = breakfastList[key]?.get("kcal").toString() + "kcal"
        binding.menuDeleteBtn.setOnClickListener{
            breakfastList.remove(key)
            notifyDataSetChanged()
            onUpdateSums.invoke()
        }
    }
}