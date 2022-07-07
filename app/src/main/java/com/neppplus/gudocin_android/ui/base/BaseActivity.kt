package com.neppplus.gudocin_android.ui.base

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ui.cart.CartActivity
import com.neppplus.gudocin_android.ui.shopping.ShoppingActivity

abstract class BaseActivity : AppCompatActivity() {

    lateinit var back: ImageView
    lateinit var title: TextView
    lateinit var shopping: ImageView
    lateinit var cart: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.let {
            setCustomActionBar()
            actionBarListener()
        }
    }

    private fun setCustomActionBar() {
        Log.d("ActionBar", "Into the Setting")

        supportActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.layout_header)

            customView.apply {
                back = findViewById(R.id.btnBack)
                title = findViewById<TextView>(R.id.txtTitle).toString()
                shopping = findViewById(R.id.btnShopping)
                cart = findViewById(R.id.btnCart)
            }

            val toolbar = customView.parent as Toolbar
            toolbar.setContentInsetsAbsolute(0, 0)
        }
    }

    private fun actionBarListener() {
        val onClickListener = View.OnClickListener { view ->
            when (view) {
                back -> finish()
                shopping -> startActivity(Intent(this, ShoppingActivity::class.java))
                cart -> startActivity(Intent(this, CartActivity::class.java))
            }
        }
        back.setOnClickListener(onClickListener)
        shopping.setOnClickListener(onClickListener)
        cart.setOnClickListener(onClickListener)
    }

}