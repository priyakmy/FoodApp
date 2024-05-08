package com.example.recyclerview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recyclerview.Adapters.FavoritesMealsAdaptor
import com.example.recyclerview.activites.MainActivity
import com.example.recyclerview.databinding.FragmentFavouritesBinding
import com.example.recyclerview.pojo.Meal
import com.example.recyclerview.viewModel.HomeViewModel


class FavouritesFragment : Fragment()
{
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var viewModel : HomeViewModel
    private lateinit var favoritesAdapter : FavoritesMealsAdaptor

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater , container: ViewGroup? ,
        savedInstanceState: Bundle? ,
    ): View
    {
        binding = FragmentFavouritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View , savedInstanceState: Bundle?)
    {
        super.onViewCreated(view , savedInstanceState)
        observeFavorites()
        prepareRecyclerView()
    }

    private fun prepareRecyclerView()
    {
        favoritesAdapter = FavoritesMealsAdaptor()
        binding.favRecView.apply {
            layoutManager = GridLayoutManager(context,2, GridLayoutManager.VERTICAL,false)
            adapter = favoritesAdapter
        }
    }

    private fun observeFavorites()
    {
        viewModel.observeCategoriesLiveData().observe(requireActivity(), Observer { meals ->
           favoritesAdapter.differ.submitList(meals)

        })
    }


}