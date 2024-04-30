package com.example.recyclerview.activites


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.recyclerview.R
import com.example.recyclerview.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.btm_nav)
        val navController = Navigation.findNavController(this , R.id.host_fragment)
        NavigationUI.setupWithNavController(bottomNavigation,navController)

    }
}