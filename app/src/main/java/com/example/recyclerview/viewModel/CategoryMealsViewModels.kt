package com.example.recyclerview.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerview.pojo.MealsByCategory
import com.example.recyclerview.pojo.MealsByCategoryList
import com.example.recyclerview.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModels : ViewModel() {

    private val mealsLiveData = MutableLiveData<List<MealsByCategory>>()

    fun getMealsByCategory(categoryName: String) {
        RetrofitInstance.foodApi.getMealsByCategory(categoryName).enqueue(object : Callback<MealsByCategoryList> {

            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                response.body()?.let { mealsList ->
                    mealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("CategoryMealsViewModel", t.message.toString())
            }
        })
    }

    fun observeMealsLiveData(): MutableLiveData<List<MealsByCategory>>
    {
        return mealsLiveData
    }
}

