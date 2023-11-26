package com.example.mealfit

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentMyPageBinding.bind(view)
        val carbohydrateAmount : String? = requireActivity().intent.getStringExtra("carbohydrateAmount")
        val proteinAmount : String? = requireActivity().intent.getStringExtra("proteinAmount")
        val fatAmount : String? = requireActivity().intent.getStringExtra("fatAmount")
        val totalCalorieAmount : String? = requireActivity().intent.getStringExtra("totalCalorieAmount")
        binding.totalCalorieAmount.text = totalCalorieAmount.toString()
        binding.carbohydrateAmount.text = carbohydrateAmount.toString()
        binding.proteinAmount.text = proteinAmount.toString()
        binding.fatAmount.text = fatAmount.toString()

        val sharedPreference = requireContext().getSharedPreferences("user info", MODE_PRIVATE)
        val nickname = sharedPreference.getString("nickname", "")
        val age = sharedPreference.getString("age", "")
        val height = sharedPreference.getString("height", "")
        val weight = sharedPreference.getString("weight", "")
        binding.nickname.text = nickname
        binding.ageHeightWeight.text = age + "세 / " + height + "cm / " + weight + "kg"
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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MyPageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MyPageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}