package com.example.recyclerview.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.recyclerview.databinding.ActivityMainBinding
import com.example.recyclerview.fragments.HomeFragment

class MealActivity : AppCompatActivity()
{
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMealInformationFromIntent()
        setInformationInViews()
    }

    private fun setInformationInViews()
    {
        Glide.with(applicationContext)
                .load(mealThumb)
                .into(binding.imageMealDetail)

        binding.collapingToolbar.title = mealName

    }


    private fun getMealInformationFromIntent(){
        val intent =intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }
}