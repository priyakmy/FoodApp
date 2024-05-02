package com.example.recyclerview.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recyclerview.Adapters.PopularMealAdapter
import com.example.recyclerview.activites.MealActivity
import com.example.recyclerview.databinding.FragmentHomeBinding
import com.example.recyclerview.pojo.CategoryMeals
import com.example.recyclerview.pojo.Meal
import com.example.recyclerview.pojo.MealList
import com.example.recyclerview.retrofit.RetrofitInstance
import com.example.recyclerview.viewModel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment()
{
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMVVM : HomeViewModel
    private lateinit var randomMeal : Meal
    private lateinit var popularItemsAdapter : PopularMealAdapter


    companion object{
        const val MEAL_ID="com.example.easyfood.ui.fragments.idMeal"
        const val MEAL_NAME="com.example.easyfood.ui.fragments.nameMeal"
        const val MEAL_THUMB="com.example.easyfood.ui.fragments.thumbMeal"


    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        homeMVVM = ViewModelProviders.of(this)[HomeViewModel::class.java]

        popularItemsAdapter = PopularMealAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)

        preparePopularItemsRecyclerView()
        homeMVVM.getRandomModel()
        observeRandomMeal()
        onRandomMealClick()

        homeMVVM.getPopularItems()
        observePopularItemsLiveData()


    }

    private fun preparePopularItemsRecyclerView()
    {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData()
    {
        homeMVVM.observePopularItemsLiveData().observe(
                viewLifecycleOwner ,
        ) { mealList ->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList)
        }
    }

    private fun onRandomMealClick()
    {
        binding.randomMeal.setOnClickListener{
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,homeMVVM.randomMealLiveData.value?.idMeal)
            intent.putExtra(MEAL_NAME,homeMVVM.randomMealLiveData.value?.strMeal)
            intent.putExtra(MEAL_THUMB, homeMVVM.randomMealLiveData.value?.strMealThumb)
            startActivity(intent)

        }
    }

    private fun observeRandomMeal()
    {
        homeMVVM.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                    .load(meal.strMealThumb)
                    .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }

    private fun getRandomMeal() {
        RetrofitInstance.foodApi.getRandomMeal().enqueue(object : Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                response.body()?.let {
                    Log.d("TAG", "onResponse:${it.meals.get(0).idMeal} name = ${it.meals.get(0).strMeal} ")
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
            }

        })
    }
}

