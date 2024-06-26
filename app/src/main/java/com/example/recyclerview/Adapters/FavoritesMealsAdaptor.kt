package com.example.recyclerview.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerview.databinding.MealItemBinding
import com.example.recyclerview.pojo.Meal

class FavoritesMealsAdaptor :
    RecyclerView.Adapter<FavoritesMealsAdaptor.FavoritesMealsAdaptorViewHolder>()
{
    inner  class FavoritesMealsAdaptorViewHolder (val binding: MealItemBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Meal>(){
        override fun areItemsTheSame(oldItem: Meal , newItem: Meal): Boolean
        {
            return oldItem.idMeal == newItem.idMeal

        }

        override fun areContentsTheSame(oldItem: Meal , newItem: Meal): Boolean {
           return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,diffUtil)
    override fun onCreateViewHolder(
        parent: ViewGroup ,
        viewType: Int ,
    ): FavoritesMealsAdaptorViewHolder
    {
        return FavoritesMealsAdaptorViewHolder(
                MealItemBinding.inflate(
                        LayoutInflater.from(parent.context),parent,false
                )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: FavoritesMealsAdaptorViewHolder , position: Int)
    {
        val meal = differ.currentList[position]
        Glide.with(holder.itemView).load(meal.strMealThumb).into(holder.binding.imMealImage)
        holder.binding.tvMealName.text = meal.strMeal
    }


}