package com.example.mealfit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.FragmentListBinding
import com.example.mealfit.databinding.ListRecyclerviewBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class BreakfastViewHolder(val binding: ListRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root){

}
class BreakfastAdapter(private val breakfastList: MutableMap<String, MutableMap<String, Int>>,
    private val onListEmptyCallback: () -> Unit):
RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = breakfastList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = BreakfastViewHolder(ListRecyclerviewBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as BreakfastViewHolder).binding
        if(position < breakfastList.size){
            val key = breakfastList.keys.elementAt(position)
            binding.menuName.text = key
            binding.menuAmount.text = breakfastList[key]?.get("g").toString() + "g"
            binding.menuCalorie.text = breakfastList[key]?.get("kcal").toString() + "kcal"
            binding.menuDeleteBtn.setOnClickListener{
                breakfastList.remove(key)
                notifyItemRemoved(position)

                if (breakfastList.isEmpty()) { // 리스트가 비었으면 처리
                    onListEmptyCallback.invoke()
                }
            }
        }
    }
    fun clearData(){
        val wasEmpty = breakfastList.isEmpty()
        breakfastList.clear()
        notifyDataSetChanged()
        onListEmptyCallback.invoke()
    }
}