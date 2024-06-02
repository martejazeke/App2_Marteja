package com.example.recipeorganizer

data class Recipe(
    val name: String,
    val ingredients: String,
    val summary: String,
    val instructions: String,
    val imageResId: Int
)
