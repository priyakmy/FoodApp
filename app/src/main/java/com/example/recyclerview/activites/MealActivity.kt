package com.example.recyclerview.activites

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.recyclerview.databinding.ActivityMealBinding
import com.example.recyclerview.db.MealDatabase
import com.example.recyclerview.fragments.HomeFragment
import com.example.recyclerview.pojo.Meal
import com.example.recyclerview.viewModel.MealViewModel
import com.example.recyclerview.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity()
{
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm: MealViewModel
    private lateinit var youtubeLink :String
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this , viewModelFactory)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()

        mealMvvm.getMealDetail(mealId)
        observerMealDetailLiveData()

        onYoutubeImageClick()
        onFavoriteClick()


    }

    private fun onFavoriteClick()
    {
        binding.btnSave.setOnClickListener{
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this,"Meal Save", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun onYoutubeImageClick()
    {
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW , Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

   private var mealToSave: Meal?= null
    @SuppressLint("SetTextI18n")
    private fun observerMealDetailLiveData() {
        mealMvvm.observeMealDetailLiveData().observe(this
        ) { value ->
            fun onChanged(t: Meal?) {
                onResponseCase()
                val meal = t
                mealToSave = meal

                binding.tvCategory.text = "Category : ${value.strCategory}"
                binding.tvAreaInfo.text = "Area : ${value.strArea}"
                binding.tvInstructions.text = value.strInstructions
                youtubeLink = value.strYoutube.toString()
            }
        }
    }

    private fun setInformationInViews()
    {
        Glide.with(this)
                .load(mealThumb)
                .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName


    }


    private fun getMealInformationFromIntent(){
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!

    }

    private  fun loadingCase(){
        binding.btnSave.visibility = View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvAreaInfo.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.btnSave.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvAreaInfo.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE

    }
}