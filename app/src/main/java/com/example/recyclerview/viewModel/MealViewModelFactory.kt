package com.example.recyclerview.viewModel

import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.recyclerview.db.MealDatabase

class MealViewModelFactory(private val mealDatabase: MealDatabase): ViewModelProvider.Factory
{
    override fun <T :ViewModel> create(modelClass : Class<T>) : T {
        return MealViewModel(mealDatabase) as T
    }

}