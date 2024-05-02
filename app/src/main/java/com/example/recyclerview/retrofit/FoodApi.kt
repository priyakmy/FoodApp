package com.example.recyclerview.retrofit

import com.example.recyclerview.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface FoodApi {

    @GET ("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetails(@Query("i")id:String) : Call<MealList>

}