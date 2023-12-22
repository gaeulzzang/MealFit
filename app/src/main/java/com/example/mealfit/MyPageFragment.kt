package com.example.mealfit

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mealfit.MyApplication.Companion.email
import com.example.mealfit.databinding.FragmentMyPageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MyPageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MyPageFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var nickname: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            nickname = it.getString(ARG_NICKNAME)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMyPageBinding.bind(view)

        val sharedPreferenceNutr = requireContext().getSharedPreferences("nutr info", MODE_PRIVATE)
        val recommendedCalorie = sharedPreferenceNutr.getInt("recommendedCalorie", 0)
        val recommendedCarbohydrate = sharedPreferenceNutr.getInt("recommendedCarbohydrate", 0)
        val recommendedProtein = sharedPreferenceNutr.getInt("recommendedProtein", 0)
        val recommendedFat = sharedPreferenceNutr.getInt("recommendedFat", 0)

        binding.totalCalorieAmount.text = recommendedCalorie.toString() + "kcal"
        binding.carbohydrateAmount.text = recommendedCarbohydrate.toString() + "g"
        binding.proteinAmount.text = recommendedProtein.toString() + "g"
        binding.fatAmount.text = recommendedFat.toString() + "g"

        val sharedPreference = requireContext().getSharedPreferences("user info", MODE_PRIVATE)
        val email = sharedPreference.getString("email", "")
        val nickname = sharedPreference.getString("nickname", "")
        val age = sharedPreference.getString("age", "")
        val height = sharedPreference.getString("height", "")
        val weight = sharedPreference.getString("weight", "")
        binding.nickname.text = nickname
        binding.ageHeightWeight.text = age + "세 / " + height + "cm / " + weight + "kg"

        val storage = MyApplication.storage
        val storageRef = storage.reference.child("${email}/info/${email}.txt")
        val userInfo =
            "Nickname: $nickname, Age: $age, Height: $height, Weight: $weight, RecommendedCalorie: $recommendedCalorie, RecommendedCarbohydrate: $recommendedCarbohydrate, RecommendedProtein: $recommendedProtein, RecommendedFat: $recommendedFat"
        val userData = userInfo.toByteArray()
        val uploadTask = storageRef.putBytes(userData)
        uploadTask.addOnFailureListener {
            Log.d("Upload", "사용자 정보 업로드 실패")
        }.addOnSuccessListener {
            Log.d("Upload", "사용자 정보 업로드 성공")
            val homeFragment = HomeFragment().apply {
                arguments = Bundle().apply {
                    putString("nickname", nickname)
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentMyPageBinding.inflate(layoutInflater)
        binding.editBtn.setOnClickListener(){
            val intent = Intent(activity, InitSettingActivity1::class.java)
            startActivity(intent)
        }
        return binding.root
    }

    companion object {
        private const val ARG_NICKNAME = "nickname"
        fun newInstance(nickname: String): MyPageFragment {
            val fragment = MyPageFragment()
            val args = Bundle()
            args.putString(ARG_NICKNAME, nickname)
            fragment.arguments = args
            return fragment
        }
    }
}