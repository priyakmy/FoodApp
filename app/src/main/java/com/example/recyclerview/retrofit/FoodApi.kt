package com.example.recyclerview.retrofit

import com.example.recyclerview.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET


interface FoodApi {

    @GET ("random.php")
    fun getRandomMeal():Call<MealList>

}