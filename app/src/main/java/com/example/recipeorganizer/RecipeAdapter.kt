package com.example.recipeorganizer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class RecipeAdapter(private val context: Context, private val recipes: List<Recipe>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return recipes.size
    }

    override fun getItem(position: Int): Any {
        return recipes[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_recipe, parent, false)
            viewHolder = ViewHolder(
                view.findViewById(R.id.recipeName),
                view.findViewById(R.id.recipeIngredients),
                view.findViewById(R.id.recipeSummary),
                view.findViewById(R.id.recipeImage)
            )
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val recipe = recipes[position]
        viewHolder.recipeName.text = recipe.name
        viewHolder.recipeIngredients.text = "Ingredients: ${recipe.ingredients}"
        viewHolder.recipeSummary.text = recipe.summary
        viewHolder.recipeImage.setImageResource(recipe.imageResId)

        return view
    }

    private class ViewHolder(
        val recipeName: TextView,
        val recipeIngredients: TextView,
        val recipeSummary: TextView,
        val recipeImage: ImageView
    )
}
