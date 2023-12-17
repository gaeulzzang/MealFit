package com.example.mealfit

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.ListRecyclerviewBinding

class DinnerViewHolder(val binding: ListRecyclerviewBinding):
    RecyclerView.ViewHolder(binding.root){

    }
class DinnerAdapter(val dinnerList: MutableList<Meal>,
                    private val onUpdateSums: () -> Unit):
RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    override fun getItemCount(): Int = dinnerList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = DinnerViewHolder(ListRecyclerviewBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as DinnerViewHolder).binding
        val meal = dinnerList[position]
        binding.menuName.text = meal.name
        binding.menuAmount.text = meal.size.toString() + "g"
        binding.menuCalorie.text = meal.kcal.toString() + "kcal"

        binding.menuDeleteBtn.setOnClickListener{
            val mealId = meal.name
            deleteMealFromStorage(mealId) // Storage에서 음식 삭제

            dinnerList.removeAt(position)
            notifyDataSetChanged()
            onUpdateSums.invoke()
        }
    }

    private fun deleteMealFromStorage(mealId: String) {
        val storage = MyApplication.storage
        val storageRef = storage.reference.child("meals/dinner/${mealId}.txt")
        storageRef.delete().addOnSuccessListener {
            Log.d("Delete", "파일 삭제 성공")
        }.addOnFailureListener {
            Log.d("Delete", "파일 삭제 실패")
        }
    }
}