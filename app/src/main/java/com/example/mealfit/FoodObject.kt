package com.example.mealfit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//object FoodObject {
//    var gson = GsonBuilder()
//        .setLenient()
//        .create()
//
//    val retrofit = Retrofit.Builder()
//        .baseUrl("http://openapi.foodsafetykorea.go.kr/")
//        .addConverterFactory(GsonConverterFactory.create(gson))
//        .build()
//
//    val foodService: FoodService by lazy{
//        retrofit.create(FoodService::class.java)
//    }
//}