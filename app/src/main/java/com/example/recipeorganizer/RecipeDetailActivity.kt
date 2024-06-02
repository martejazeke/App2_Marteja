package com.example.recipeorganizer

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RecipeDetailActivity : AppCompatActivity() {
    private lateinit var textViewRecipeName: TextView
    private lateinit var textViewRecipeIngredients: TextView
    private lateinit var textViewRecipeInstructions: TextView
    private lateinit var buttonFavorite: Button
    private lateinit var recipeImageView: ImageView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        textViewRecipeName = findViewById(R.id.recipeNameTextView)
        textViewRecipeIngredients = findViewById(R.id.recipeIngredientsTextView)
        textViewRecipeInstructions = findViewById(R.id.recipeInstructionsTextView)
        recipeImageView = findViewById(R.id.recipeImageView)
        buttonFavorite = findViewById(R.id.buttonFavorite)

        // Load recipe details from intent extras
        val recipeName = intent.getStringExtra("recipeName")
        val recipeIngredients = intent.getStringExtra("recipeIngredients")
        val recipeInstructions = intent.getStringExtra("recipeInstructions")
        val recipeImageResource = intent.getIntExtra("recipeImageResource", 0)

        textViewRecipeName.text = recipeName
        textViewRecipeIngredients.text = recipeIngredients
        textViewRecipeInstructions.text = recipeInstructions
        recipeImageView.setImageResource(recipeImageResource)

        Log.d("RecipeDetailActivity", "Image Resource ID: $recipeImageResource")
        recipeImageView.setImageResource(recipeImageResource)


        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("RecipePreferences", Context.MODE_PRIVATE)

        // Favorites button click listener
        buttonFavorite.setOnClickListener {
            saveFavoriteRecipe(recipeName, recipeIngredients, recipeInstructions, recipeImageResource)
        }

    }

    private fun saveFavoriteRecipe(name: String?, ingredients: String?, instructions: String?, imageResource: Int) {
        if (name.isNullOrEmpty() || ingredients.isNullOrEmpty() || instructions.isNullOrEmpty()) {
            Toast.makeText(this, "Recipe data is incomplete!", Toast.LENGTH_SHORT).show()
            return
        }

        val savedRecipes = sharedPreferences.getStringSet("favoriteRecipes", mutableSetOf())?.toMutableSet()
            ?: mutableSetOf()

        val formattedRecipe = "$name|$ingredients|$instructions|$imageResource"
        savedRecipes.add(formattedRecipe)

        val editor = sharedPreferences.edit()
        editor.putStringSet("favoriteRecipes", savedRecipes)
        editor.apply()

        Toast.makeText(this, "$name added to favorites!", Toast.LENGTH_SHORT).show()
    }


}
