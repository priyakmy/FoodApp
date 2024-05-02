package com.example.recyclerview.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerview.databinding.PopularItemsBinding
import com.example.recyclerview.pojo.CategoryMeals

class PopularMealAdapter(): RecyclerView.Adapter<PopularMealAdapter.PopularMealViewHolder>(){
    private var mealsList = ArrayList<CategoryMeals>()


    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): PopularMealViewHolder
    {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int
    {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder , position: Int)
    {
        Glide.with(holder.itemView)
                .load(mealsList[position].strMeValThumb)
                .into(holder.binding.imgPopularMeal)

    }
    class PopularMealViewHolder(val binding: PopularItemsBinding): RecyclerView.ViewHolder(binding.root){

    }

}

