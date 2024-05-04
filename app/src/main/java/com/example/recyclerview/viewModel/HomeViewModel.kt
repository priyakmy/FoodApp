package com.example.recyclerview.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.recyclerview.pojo.Category
import com.example.recyclerview.pojo.CategoryList
import com.example.recyclerview.pojo.MealsByCategoryList
import com.example.recyclerview.pojo.MealsByCategory
import com.example.recyclerview.pojo.Meal
import com.example.recyclerview.pojo.MealList
import com.example.recyclerview.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(): ViewModel() {
    var randomMealLiveData = MutableLiveData<Meal>()
    val popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    var categoriesLiveData = MutableLiveData<List<CategoryList>>()

    fun getRandomModel(){
        RetrofitInstance.foodApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body() != null) {
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("", t.message.toString())
            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.foodApi.getPopularItems("Seafood").enqueue(object :Callback<MealsByCategoryList>{
            override fun onResponse(call: Call<MealsByCategoryList> , response: Response<MealsByCategoryList>)
            {
                if(response.body()!= null){

                    popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList> , t: Throwable)
            {
                Log.d("", t.message.toString())
            }

        })

    }

    fun getCategories(){
    RetrofitInstance.foodApi.getCategories().enqueue(object :Callback<CategoryList>{
        override fun onResponse(call: Call<CategoryList> , response: Response<CategoryList>)
        {
            response.body()?.let { categoryList ->
            categoriesLiveData.postValue(categoryList.categories)
        }
        }

        override fun onFailure(call: Call<CategoryList> , t: Throwable)
        {
            Log.e("HomeViewModel", t.message.toString())
        }

    })
    }
    fun observeRandomMealLiveData(): LiveData<Meal> {
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): MutableLiveData<List<CategoryList>> {
        return categoriesLiveData
    }
}

private fun <T> LiveData<T>.postValue(categories: List<Category>)
{

}


