package com.example.recipeorganizer

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class FavoriteRecipeAdapter(private val context: Context, private val dataSource: MutableList<String>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        val holder: ViewHolder

        if (rowView == null) {
            rowView = inflater.inflate(R.layout.list_item_favorite_recipe, parent, false)

            holder = ViewHolder()
            holder.titleTextView = rowView.findViewById(R.id.favorite_recipe_list_title)
            holder.subtitleTextView = rowView.findViewById(R.id.favorite_recipe_list_subtitle)

            rowView.tag = holder
        } else {
            holder = rowView.tag as ViewHolder
        }

        val recipeString = getItem(position) as String
        val parts = recipeString.split("|")

        if (parts.size >= 3) {
            val recipeName = parts[0]
            val recipeIngredients = parts[1]
            val recipeInstructions = parts[2]

            holder.titleTextView.text = recipeName
            holder.subtitleTextView.text = recipeIngredients
        }

        return rowView!!
    }

    private class ViewHolder {
        lateinit var titleTextView: TextView
        lateinit var subtitleTextView: TextView
    }
}
