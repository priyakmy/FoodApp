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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.recyclerview.Adapters.CategoriesRecyclerAdapter
import com.example.recyclerview.Adapters.PopularMealAdapter
import com.example.recyclerview.activites.CategoryMealsActivity
import com.example.recyclerview.activites.MealActivity
import com.example.recyclerview.databinding.FragmentHomeBinding
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
    private lateinit var categoriesAdapter: CategoriesRecyclerAdapter


    companion object{
        const val MEAL_ID="com.example.recyclerview.fragments.idMeal"
        const val MEAL_NAME="com.example.recyclerview.fragments.nameMeal"
        const val MEAL_THUMB="com.example.recyclerview.fragments.thumbMeal"
        const val  CATEGORY_NAME = "com.example.recyclerview.fragments.categoryName"


    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        homeMVVM = ViewModelProviders.of(this)[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)
        popularItemsAdapter = PopularMealAdapter()
        preparePopularItemsRecyclerView()

        homeMVVM.getRandomModel()
        observeRandomMeal()
        onRandomMealClick()

        homeMVVM.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClick()

        prepareCategoriesRecyclerView()
        homeMVVM.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()



    }

    private fun onCategoryClick()
    {
        categoriesAdapter.(onItemClicked())() = { Category ->
            val intent = Intent(activity , CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME , category.strCAtegory)
            startActivity(intent)
        }
    }


    private fun prepareCategoriesRecyclerView()
    {
        categoriesAdapter = CategoriesRecyclerAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter = categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() =
            homeMVVM.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
                categoriesAdapter.setCategoryList(categories)
            })


    private fun onPopularItemClick()
    {
        popularItemsAdapter.onItemClick = { meal ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID, meal.idMeal)
            intent.putExtra(MEAL_NAME, meal.strMeal)
            intent.putExtra(MEAL_THUMB, meal.strMealThumb)
            startActivity(intent)

        }
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
            Log.d("tag", "observePopularItemsLiveData:${mealList.size} ")
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
                    Log.d("TAG", "onResponse:${it.meals[0].idMeal} name = ${it.meals[0].strMeal} ")
                }

            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("TAG", "onFailure: ${t.message}")
            }

        })
    }
}

