package com.example.mealfit

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.mealfit.databinding.ListRecyclerviewBinding

class BreakfastViewHolder(val binding: ListRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root){

}
class BreakfastAdapter(val breakfastList: MutableList<Meal>,
                       private val onUpdateSums: () -> Unit):
RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = breakfastList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = BreakfastViewHolder(ListRecyclerviewBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as BreakfastViewHolder).binding

        val meal = breakfastList[position]
        binding.menuName.text = meal.name
        binding.menuAmount.text = meal.size.toString() + "g"
        binding.menuCalorie.text = meal.kcal.toString() + "kcal"

        binding.menuDeleteBtn.setOnClickListener{
            val mealId = meal.name
            deleteMealFromStorage(mealId, holder.itemView.context) // Storage에서 음식 삭제

            breakfastList.removeAt(position)
            notifyDataSetChanged()
            onUpdateSums.invoke()
        }
    }

    private fun deleteMealFromStorage(mealId: String, context: Context) {
        val storage = MyApplication.storage
        val sharedPreferenceUser = context.getSharedPreferences("user info", AppCompatActivity.MODE_PRIVATE)
        val email = sharedPreferenceUser.getString("email", "")
        val storageRef = storage.reference.child("${email}/meals/breakfast/${mealId}.txt")
        storageRef.delete().addOnSuccessListener {
            Log.d("Delete", "파일 삭제 성공")
        }.addOnFailureListener {
            Log.d("Delete", "파일 삭제 실패")
        }
    }
}