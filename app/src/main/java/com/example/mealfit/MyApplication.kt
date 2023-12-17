package com.example.mealfit

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class MyApplication : MultiDexApplication() {
    companion object{
        lateinit var db: FirebaseFirestore
        lateinit var storage: FirebaseStorage
        lateinit var auth: FirebaseAuth
        var email : String? = null
        fun checkAuth() : Boolean{
            val currentUser = auth.currentUser
            return currentUser?.let{
                email = currentUser.email
                if(currentUser.isEmailVerified){
                    true
                }
                else{
                    false
                }
            } ?: let{
                false
            }
        }
    }
    override fun onCreate(){
        super.onCreate()
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
    }
}