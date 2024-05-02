package com.example.recyclerview.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerview.pojo.Meal
import com.example.recyclerview.pojo.MealList
import retrofit2.Call
import retrofit2.Response


class MealViewModel: ViewModel()
{
    private var mealDetailLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id:String){
        fun onResponse(call: Call<MealList> , response: Response<MealList>) {
            response.body()?.let {
                Log.d("TAG" , "onResponse:${it.meals.get(0).idMeal} name = ${it.meals.get(0).strMeal} ")
            }

        }

         fun onFailure(call: Call<MealList> , t: Throwable) {
            Log.d("TAG" , "onFailure: ${t.message}")
        }

    }

    fun observeMealDetailLiveData(): LiveData<Meal>
    {
        return mealDetailLiveData
    }
}
