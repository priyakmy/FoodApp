package com.example.recyclerview.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recyclerview.Adapters.FavoritesMealsAdaptor
import com.example.recyclerview.activites.MainActivity
import com.example.recyclerview.databinding.FragmentFavouritesBinding
import com.example.recyclerview.pojo.Meal
import com.example.recyclerview.viewModel.HomeViewModel
import com.google.android.material.snackbar.Snackbar


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

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN ,
                ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT

        ){
            override fun onMove(
                recyclerView: RecyclerView ,
                viewHolder: RecyclerView.ViewHolder ,
                target: RecyclerView.ViewHolder ,
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder , direction: Int)
            {
              val position = viewHolder.adapterPosition
                viewModel.deleteMeal(favoritesAdapter.differ.currentList[position])
                Snackbar.make(requireView(), "Meal Deleted", Snackbar.LENGTH_LONG).setAction(
                        "Undo" ,
                        View.OnClickListener {
                           viewModel.insertMeal(favoritesAdapter.differ.currentList[position])

                        }
                ).show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)
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