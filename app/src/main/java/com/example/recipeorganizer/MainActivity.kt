package com.example.recipeorganizer

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonBrowseRecipes: Button = findViewById(R.id.buttonBrowseRecipes)
        val buttonFavorites: Button = findViewById(R.id.buttonFavorites)

        buttonBrowseRecipes.setOnClickListener {
            val intent = Intent(this, BrowseRecipesActivity::class.java)
            startActivity(intent)
        }

        buttonFavorites.setOnClickListener {
            val intent = Intent(this, FavoriteRecipesActivity::class.java)
            startActivity(intent)

        }
        displayPopupDialog()
    }

    private fun displayPopupDialog(){
        var popupDialog = Dialog(this)
        popupDialog.setCancelable(false)

        popupDialog.setContentView(R.layout.instructionpopup)
        popupDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var confirm_btn = popupDialog.findViewById<Button>(R.id.confirm_button)

        confirm_btn.setOnClickListener{
            popupDialog.dismiss()
        }

        popupDialog.show()

    }
}
