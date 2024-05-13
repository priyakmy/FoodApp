package com.example.retrofitwithrecycler

import com.example.retrofitwithrecycler.models.CountryItem
import retrofit2.Call
import retrofit2.http.GET

interface CountryApi {

    @GET("v1/countries")
    fun getData() : Call<List<CountryItem>>
}