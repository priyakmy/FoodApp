package com.example.recyclerview.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerview.databinding.PopularItemsBinding
import com.example.recyclerview.pojo.MealsByCategory

class PopularMealAdapter : RecyclerView.Adapter<PopularMealAdapter.PopularMealViewHolder>() {
    private var mealsList = ArrayList<MealsByCategory>()
    var onItemClick: ((MealsByCategory) -> Unit)?= null
    lateinit var onLongItemClick : (MealsByCategory) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        val binding = PopularItemsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PopularMealViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        val meal = mealsList[position]
        Log.d("TAG", "onBindViewHolder:$meal ")
        Glide.with(holder.itemView)
                .load(meal.strMealThumb)
                .into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(mealsList[position])
        }
        holder.itemView.setOnLongClickListener{
            onLongItemClick?.invoke(mealsList[position])
            true
        }
    }

    fun setMeals(mealsList: List<MealsByCategory>) {
        this.mealsList.clear()
        this.mealsList.addAll(mealsList)
        notifyDataSetChanged()
    }

    class PopularMealViewHolder(val binding: PopularItemsBinding) : RecyclerView.ViewHolder(binding.root)

}
