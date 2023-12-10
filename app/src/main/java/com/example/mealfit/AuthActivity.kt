package com.example.mealfit

import android.content.Intent
import android.os.Bundle
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
                            Snackbar.make(binding.root, "로그인 성공", Snackbar.LENGTH_SHORT).show()
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
}