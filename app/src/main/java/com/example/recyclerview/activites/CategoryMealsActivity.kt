package com.example.recyclerview.activites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recyclerview.Adapters.CategoryMealsAdapter
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityCategoryMealsBinding
import com.example.recyclerview.fragments.HomeFragment
import com.example.recyclerview.viewModel.CategoryMealsViewModels

class CategoryMealsActivity : AppCompatActivity()
{
     lateinit var binding : ActivityCategoryMealsBinding
     private lateinit var categoryMealsViewModels: CategoryMealsViewModels
     lateinit var categoryMealsAdapter: CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModels = ViewModelProviders.of(this)[CategoryMealsViewModels::class.java]
        intent.getStringExtra(HomeFragment.CATEGORY_NAME)?.let { categoryMealsViewModels.getMealsByCategory(it) }

        categoryMealsViewModels.observeMealsLiveData().observe(this, Observer{ mealsList ->
            binding.tvCategoryCount.text = mealsList.size.toString()
           categoryMealsAdapter.setMealsList(mealsList)


        } )
    }

    private fun prepareRecyclerView()
    {
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.mealRecyclerview.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}