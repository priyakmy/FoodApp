package com.example.recyclerview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recyclerview.Adapters.CategoriesRecyclerAdapter
import com.example.recyclerview.Adapters.CategoryMealsAdapter
import com.example.recyclerview.R
import com.example.recyclerview.activites.MainActivity
import com.example.recyclerview.databinding.FragmentCategoriesBinding
import com.example.recyclerview.databinding.MealItemBinding
import com.example.recyclerview.viewModel.HomeViewModel

class CategoriesFragment : Fragment()
{
    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var categoriesAdapter: CategoriesRecyclerAdapter
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle? ,
    ): View?
    {
        binding = FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)
        prepareRecyclerView()
        observeCategories()
    }

    private fun observeCategories()
    {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner, Observer { categories ->
            categoriesAdapter.setCategoryList( categories )

        })
    }

    private fun prepareRecyclerView()
    {
        categoriesAdapter = CategoriesRecyclerAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context,3, GridLayoutManager.VERTICAL ,false)
            adapter = categoriesAdapter
        }
    }

}