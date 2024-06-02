package com.example.recipeorganizer

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class BrowseRecipesActivity : AppCompatActivity() {
    private lateinit var listViewRecipes: ListView
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_recipes)

        listViewRecipes = findViewById(R.id.listViewRecipes)
        var buttonFavorite = findViewById<Button>(R.id.buttonFavorite)

        // Load preferred cuisine and saved recipe name
        sharedPreferences = getSharedPreferences("RecipePreferences", Context.MODE_PRIVATE)
        val savedRecipeName = sharedPreferences.getString("savedRecipeName", "")

        val allRecipes = listOf(
            Recipe("Salisbury Steak", "Onion, Butter, Minced Meat, Egg, Panko, Milk", "Delicious steak with onion and butter", "1. Slice an onion!\n2. Slice! \n3. Chop!\n 4. Melt the butter!\n 5. Saute!\n6. Add the ingredients!\n7. Knead! \n8. Make shape!\n9. Pan-fry!\n10. Arrange the plate!", R.drawable.salisbury_steak),
            Recipe("Beef Curry", "Potato, Carrot, Onion, Beef, Butter, Water, Curry Roux, Bay Leaf", "Spicy beef curry with potato and carrot", "1. Peel!\n2. Slice up!\n3. Slice up! \n4. Melt the butter!\n5. Saute!\n6. Measure and add!\n7. Stew!", R.drawable.beef_curry),
            Recipe("Sweet Tofu Sushi", "Fried Tofu, Water, Soy Sauce, Sugar, Sake, Mirin, Rice, Water, Rice Vinegar", "Sweet tofu sushi with fried tofu and soy sauce", "1.slice up!\n2. Stew!\n3. Remove fried tofu!\n 4. Measure and add!\n5. Measure and add! \n6. Wash some rice. \n7. Set the timer! \n8. Make sushi rice! \n9. Stuff with rice!", R.drawable.sweet_tofu),
            Recipe("Udon", "Water, Kelp, Green Onion, Udon, Soy Sauce, Salt", "Simple udon with green onion and soy sauce", "1. Make stock! \n2. Seperate the stock! \n3. Chop up!\n4. Stew!", R.drawable.udon),
            Recipe("Soba", "Buckwheat Flour, Flour, Hot Water", "Traditional soba with buckwheat flour", "1. Add the ingredients! \n2. Mix!\n3. Knead!\n4. Roll out the dough!\n5. Chop up!\n6. Boil some soba.\n7. Remove the soba.", R.drawable.soba),
            Recipe("Rice Gratin", "Butter, Flour, Milk, Salt, Pepper, Chicken, Onion, Rice, Ketchup", "Creamy rice gratin with chicken and ketchup", "1. Melt the butter! Mix!\n" +"2. Stew!\n" + "3. Cut!\n" + "4. Chop up!\n" + "5. Saute!\n" + "6. Saute!\n" + "7. Add toppings freely!\n" + "8. Set the timer!", R.drawable.rice_gratin),
            Recipe("Pizza", "Flour, Dry Yeast, Hot Water, Olive Oil, Tomato, Pepperoni, Pizza Sauce", "Classic pizza with tomato and pepperoni", "1. Add the ingredients!\n" + "2. Knead!\n" + "3. Roll out the dough!\n" + "4. Cut!\n" + "5. Cut!\n" + "6. Spread pizza sauce!\n" + "7. Add toppings freely!\n" + "8. Set the timer!", R.drawable.pizza),
            Recipe("Chicken Kebabs", "Chicken, Leek, Shiitake Mushroom", "Savory chicken kebabs with leek and mushroom", "1. Slice up!\n" + "2. Slice up!\n" + "3. Place on the stick!\n" + "4. Grill with charcoal!", R.drawable.chicken_kebabs),
            Recipe("Seasoned Beef with Potatoes", "Potatoes, Carrot, Onion, Beef, Water, Soy Sauce, Sugar, Mirin, Sake", "Flavorful beef with potatoes and soy sauce", "1. Peel!\n" + "2. Slice up!\n" + "3. Chop up!\n" + "4. Chop up!\n" + "5. Saute!\n" + "6. Remove foam!\n" + "7. Stew!", R.drawable.seasoned_beef),
            Recipe("Shumai Wonton", "Scallion, Onion, Minced Meat, Salt, Soy Sauce, Sesame Oil, Wonton Skin, Peas", "Delicious shumai wonton with scallion and minced meat", "1. Chop up!\n" + "2. Slice an onion!\n" + "3. Slice!\n" + "4. Chop up!\n" + "5. Add the ingredients!\n" + "Knead!\n" + "6. Wrap shumai wontons!\n" + "7. Steam!", R.drawable.shumai)
        )

        val recipes = if (savedRecipeName.isNullOrEmpty()) {
            allRecipes
        } else {
            allRecipes.filter { recipe ->
                recipe.name.contains(savedRecipeName, ignoreCase = true)
            }
        }

        val adapter = RecipeAdapter(this, recipes)
        listViewRecipes.adapter = adapter

        listViewRecipes.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedRecipe = recipes[position]
            val intent = Intent(this, RecipeDetailActivity::class.java)
            intent.putExtra("recipeName", selectedRecipe.name)
            intent.putExtra("recipeIngredients", selectedRecipe.ingredients)
            intent.putExtra("recipeInstructions", selectedRecipe.instructions)
            intent.putExtra("recipeImageResource", selectedRecipe.imageResId)
            startActivity(intent)
        }
    }

    private fun loadFavoriteRecipes(): List<Recipe> {
        val savedRecipes = sharedPreferences.getStringSet("favoriteRecipes", mutableSetOf())
        val recipeList = mutableListOf<Recipe>()

        savedRecipes?.forEach { recipeString ->
            val recipeData = recipeString.split("|")
            if (recipeData.size == 4) {
                val name = recipeData[0]
                val ingredients = recipeData[1]
                val summary = recipeData[2]
                val instruction = recipeData[3]
                val imageResource = recipeData[4].toIntOrNull() ?: 0
                recipeList.add(Recipe(name, ingredients, summary, instruction, imageResource))
            }
        }

        return recipeList
    }
}
