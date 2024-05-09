package com.example.recyclerview.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recyclerview.db.MealDatabase
import com.example.recyclerview.pojo.CategoryList
import com.example.recyclerview.pojo.MealsByCategoryList
import com.example.recyclerview.pojo.MealsByCategory
import com.example.recyclerview.pojo.Meal
import com.example.recyclerview.pojo.MealList
import com.example.recyclerview.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
        private val mealDatabase : MealDatabase
) : ViewModel() {
    val randomMealLiveData = MutableLiveData<Meal>()
    private val popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private val categoriesLiveData = MutableLiveData<List<CategoryList>>()
    private val bottomSheetLiveData = MutableLiveData<Meal>()
    private val searchMealsLiveData = MutableLiveData<List<Meal>>()
    private val favouriteMealsLiveData = mealDatabase.mealDao().getAllMeals()



     private var saveStateRandomMeal : Meal? = null
    fun getRandomModel() {
        saveStateRandomMeal?.let { randomMeal ->
            randomMealLiveData.postValue(randomMeal)

        }
        RetrofitInstance.foodApi.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    val randomMeal: Meal? = response.body()?.meals?.firstOrNull()
                    randomMeal?.let {
                        randomMealLiveData.postValue(it)
                        saveStateRandomMeal = randomMeal
                    }
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel", "Error fetching random meal: ${t.message}")
            }
        })
    }

    fun getPopularItems() {
        RetrofitInstance.foodApi.getPopularItems("Seafood").enqueue(object : Callback<MealsByCategoryList> {
            override fun onResponse(call: Call<MealsByCategoryList>, response: Response<MealsByCategoryList>) {
                if (response.isSuccessful) {
                    popularItemsLiveData.postValue(response.body()?.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("HomeViewModel", "Error fetching popular items: ${t.message}")
            }
        })
    }

    fun getCategories() {
        RetrofitInstance.foodApi.getCategories().enqueue(object : Callback<CategoryList> {
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
               response.body()?.let { categoryList ->
                   categoriesLiveData.postValue(categoryList.categories)

               }
                }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.e("HomeViewModel", "Error fetching categories: ${t.message}")
            }
        })
    }

    fun getMealById(id: String) {
        RetrofitInstance.foodApi.getMealDetails(id).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    response.body()?.meals?.firstOrNull()?.let {
                        bottomSheetLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel", "Error fetching meal by id: ${t.message}")
            }
        })
    }

    fun searchMeals(searchQuery: String) {
        RetrofitInstance.foodApi.searchMeals(searchQuery).enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.isSuccessful) {
                    val mealList: List<Meal>? = response.body()?.meals
                    mealList?.let {
                        searchMealsLiveData.postValue(it)
                    }
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("HomeViewModel", "Error searching meals: ${t.message}")
            }
        })
    }

    fun deleteMeal(meal: Meal)
    {
        viewModelScope.launch {
            mealDatabase.mealDao().deleteMeal(meal)

        }
    }

    fun insertMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().insertMeal(meal)
        }
    }

    fun observeRandomMealLiveData(): LiveData<Meal> = randomMealLiveData

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> = popularItemsLiveData

    fun observeCategoriesLiveData(): LiveData<List<CategoryList>> = categoriesLiveData

    fun observeFavouritesMealsLiveData() : LiveData<List<Meal>>  = favouriteMealsLiveData

    fun observeBottomSheetMeal(): LiveData<Meal> = bottomSheetLiveData


    fun observeSearchMealsLiveData(): LiveData<List<Meal>> = searchMealsLiveData
}
