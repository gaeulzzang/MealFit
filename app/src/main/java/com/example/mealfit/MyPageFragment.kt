package com.example.mealfit

import android.content.Intent
import android.os.Bundle
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

        val carbohydrateAmount : String? = requireActivity().intent.getStringExtra("carbohydrateAmount")
        val proteinAmount : String? = requireActivity().intent.getStringExtra("proteinAmount")
        val fatAmount : String? = requireActivity().intent.getStringExtra("fatAmount")
        val totalCalorieAmount : String? = requireActivity().intent.getStringExtra("totalCalorieAmount")
        binding.totalCalorieAmount.text = totalCalorieAmount.toString()
        binding.carbohydrateAmount.text = carbohydrateAmount.toString()
        binding.proteinAmount.text = proteinAmount.toString()
        binding.fatAmount.text = fatAmount.toString()
        nickname?.let{
            binding.nickname.text = nickname.toString()
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