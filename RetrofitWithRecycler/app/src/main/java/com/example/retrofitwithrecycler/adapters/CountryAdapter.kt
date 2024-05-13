package com.example.retrofitwithrecycler.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrofitwithrecycler.R
import com.example.retrofitwithrecycler.databinding.CountryItemBinding
import com.example.retrofitwithrecycler.models.CountryItem

class CountryAdapter(private var con : Context , private var list : List<CountryItem>) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>()
{
    inner class ViewHolder(v : View) :RecyclerView.ViewHolder(v){

        var imgCountry = v.findViewById<ImageView>(R.id.imgCountry)
        var tvCountryName = v.findViewById<TextView>(R.id.tvCountryName)
        var tvCapital = v.findViewById<TextView>(R.id.tvCapital)
    }

    override fun onCreateViewHolder(parent: ViewGroup , viewType: Int): ViewHolder
    {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CountryItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)

    }

    override fun getItemCount(): Int
    {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder , position: Int)
    {
        Glide.with(con)
                .load(list[position].flag)
                .into(holder.imgCountry)
        holder.tvCountryName.text = list[position].name
        holder.tvCapital.text = list[position].capital
    }

}