package com.example.recipeorganizer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class FavoriteRecipesActivity : AppCompatActivity() {
    private lateinit var listViewFavorites: ListView

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private lateinit var favoriteRecipes: MutableList<String>
    private lateinit var adapter: FavoriteRecipeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_recipes)

        listViewFavorites = findViewById(R.id.listViewFavorites)

        sharedPreferences = getSharedPreferences("RecipePreferences", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        favoriteRecipes = sharedPreferences.getStringSet("favoriteRecipes", mutableSetOf())?.toMutableList()
            ?: mutableListOf()

        adapter = FavoriteRecipeAdapter(this, favoriteRecipes)
        listViewFavorites.adapter = adapter

        registerForContextMenu(listViewFavorites)

        listViewFavorites.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val selectedRecipe = favoriteRecipes[position]
                val parts = selectedRecipe.split("|")
                val recipeName = parts[0]
                val recipeIngredients = parts[1]
                val recipeInstructions = parts[2]

                val intent = Intent(this, RecipeDetailActivity::class.java).apply {
                    putExtra("recipeName", recipeName)
                    putExtra("recipeIngredients", recipeIngredients)
                    putExtra("recipeInstructions", recipeInstructions)
                }
                startActivity(intent)
            }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.context_menu_favorite_recipes, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.itemId) {
            R.id.action_delete_favorite -> {
                deleteFavoriteRecipe(info.position)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun deleteFavoriteRecipe(position: Int) {
        // Confirm deletion with an alert dialog
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to delete this recipe from favorites?")
            .setPositiveButton("Yes") { _, _ ->
                favoriteRecipes.removeAt(position)
                adapter.notifyDataSetChanged()

                // Save the updated list of favorite recipes to SharedPreferences
                editor.putStringSet("favoriteRecipes", favoriteRecipes.toMutableSet())
                editor.apply()

                Toast.makeText(this, "Recipe removed from favorites", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No", null)
            .show()
    }
}
