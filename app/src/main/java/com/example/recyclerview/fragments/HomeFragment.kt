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
import com.bumptech.glide.Glide
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

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        homeMVVM = ViewModelProviders.of(this)[HomeViewModel::class.java]
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
        homeMVVM.getRandomModel()
        observeRandomMeal()
        onRandomMealClick()


    }

    private fun onRandomMealClick()
    {
        binding.randomMeal.setOnClickListener{
            val intent = Intent(t)
        }
    }

    private fun observeRandomMeal()
    {
        homeMVVM.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { t ->
            Glide.with(this@HomeFragment)
                    .load(t!!.strMealThumb)
                    .into(binding.imgRandomMeal)
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

