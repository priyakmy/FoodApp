package com.example.recyclerview.fragments.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.recyclerview.activites.MainActivity
import com.example.recyclerview.databinding.FragmentMealBottomSheetBinding
import com.example.recyclerview.viewModel.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val MEAL_ID = "param1"

class MealBottomSheetFragment : BottomSheetDialogFragment()
{
    private var mealId :String?= null
    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel : HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(MEAL_ID)
        }
        viewModel= (activity as MainActivity).viewModel


    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle? ,
    ): View
    {
        binding = FragmentMealBottomSheetBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)
        mealId?.let { viewModel.getMealById(it) }
        observeBottomSheetMeal()
    }

    private fun observeBottomSheetMeal()
    {
        viewModel.observeBottomSheetMeal().observe(viewLifecycleOwner , Observer { meal ->
            Glide.with(this)
                    .load(meal.strMealThumb)
                    .into(binding.imgCategory)
            binding.tvMealCategory.text = meal.strArea
            binding.tvMealNameInBtmsheet.text = meal.strCategory
            binding.tvMealCountry.text = meal.strMeal
            
        })
    }

    companion object{
    @JvmStatic
    fun newInstance( param1: String) =
            MealBottomSheetFragment.apply {
                var arguments = Bundle().apply {
                    putString(MEAL_ID , param1)
                }
            }

    }


}