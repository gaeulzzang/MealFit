package com.example.mealfit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.mealfit.databinding.ActivityAuthBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            // 구글 로그인 결과 처리
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try{
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                MyApplication.auth.signInWithCredential(credential)
                    .addOnCompleteListener(this){ task ->
                        if(task.isSuccessful){
                            MyApplication.email = account.email
                            // 이메일 계정을 SharedPreferences에 저장
                            val sharedPreference = getSharedPreferences("user info", MODE_PRIVATE)
                            val editor = sharedPreference.edit()
                            editor.putString("email", account.email)
                            editor.apply()
                            Snackbar.make(binding.root, "로그인 성공", Snackbar.LENGTH_SHORT).show()
                            // fetchUserInfoFromFirebase(account.email ?: "")
                        }
                        else{
                            Snackbar.make(binding.root, "로그인 실패", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    .addOnSuccessListener(this){
                        startActivity(Intent(this, InitSettingActivity1::class.java))
                        finish()
                    }
            } catch(e: ApiException){
                Snackbar.make(binding.root, "로그인 실패", Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.btnGoogleLogin.setOnClickListener{
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            requestLauncher.launch(signInIntent)
        }
    }

    fun fetchUserInfoFromFirebase(email: String) {
        val storage = MyApplication.storage
        val storageRef = storage.reference.child("${email}/info/${email}.txt")
        storageRef.getBytes(Long.MAX_VALUE).addOnSuccessListener { bytes ->
            val userInfo = String(bytes)
            val userInfoList = userInfo.split(",")
            val nickname = userInfoList[0].substringAfter("Nickname: ").trim().toString()
            val age = userInfoList[1].substringAfter("Age: ").trim()
            val height = userInfoList[2].substringAfter("Height: ").trim()
            val weight = userInfoList[3].substringAfter("Weight: ").trim()
            val recommendedCalorie = userInfoList[4].substringAfter("RecommendedCalorie: ").trim().toInt()
            val recommendedCarbohydrate = userInfoList[5].substringAfter("RecommendedCarbohydrate: ").trim().toInt()
            val recommendedProtein = userInfoList[6].substringAfter("RecommendedProtein: ").trim().toInt()
            val recommendedFat = userInfoList[7].substringAfter("RecommendedFat: ").trim().toInt()

            // SharedPreferences에 사용자 정보 저장
            val sharedPreference = getSharedPreferences("user info", MODE_PRIVATE)
            val editor = sharedPreference.edit()
            editor.putString("nickname", nickname)
            editor.putString("age", age)
            editor.putString("height", height)
            editor.putString("weight", weight)
            editor.putInt("recommendedCalorie", recommendedCalorie)
            editor.putInt("recommendedCarbohydrate", recommendedCarbohydrate)
            editor.putInt("recommendedProtein", recommendedProtein)
            editor.putInt("recommendedFat", recommendedFat)
            editor.apply()

            // MainActivity로 이동
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            Log.d("Fetch", "사용자 정보 가져오기 실패")
        }
    }
}