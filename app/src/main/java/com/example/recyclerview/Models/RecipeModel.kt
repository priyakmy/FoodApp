package com.example.recyclerview.Models

import java.io.Serializable

data class RecipeModel(
val name: String,
val description: String,
val price: Double,
val image: String
): Serializable

