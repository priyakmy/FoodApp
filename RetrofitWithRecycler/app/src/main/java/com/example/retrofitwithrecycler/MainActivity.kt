package com.example.retrofitwithrecycler

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofitwithrecycler.adapters.CountryAdapter
import com.example.retrofitwithrecycler.models.CountryItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
   private var baseUrl = "https://api.example.com/v1/"

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rvCountry)

        getAllData()
    }

    private fun getAllData()
    {
        val retrofitBuilder = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(CountryApi::class.java)
        val retrofitData = retrofitBuilder.getData()

        retrofitData.enqueue(object  : Callback<List<CountryItem>>{
            override fun onResponse(
                call: Call<List<CountryItem>> ,
                response: Response<List<CountryItem>> ,
            )
            // if Api is success , then use the data of API and show in your app
            {
                val data = response.body()!!

                adapter = CountryAdapter(baseContext,data)

                recyclerView.adapter = adapter

                Log.d("TAG" ,data.toString())


            }

            override fun onFailure(call: Call<List<CountryItem>> , t: Throwable)
            {
                Log.d("TAG" , "OnFaliure:" + t.message)

            }

        })

        }
    }



