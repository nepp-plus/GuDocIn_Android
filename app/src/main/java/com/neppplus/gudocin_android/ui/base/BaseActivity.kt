package com.neppplus.gudocin_android.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.cart.CartActivity
import com.neppplus.gudocin_android.ui.shopping.ShoppingActivity

abstract class BaseActivity : AppCompatActivity() {
    lateinit var mContext: Context
    lateinit var apiService: RetrofitService

    lateinit var back: ImageView
    lateinit var title: TextView
    lateinit var shopping: ImageView
    lateinit var cart: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        val retrofit = Retrofit.getRetrofit(mContext)
        apiService = retrofit.create(RetrofitService::class.java)

        supportActionBar?.let {
            setCustomActionBar()
            actionBarListener()
        }
    }

    private fun setCustomActionBar() {
        val defActionBar = supportActionBar
        Log.d("ActionBar", "Into the Setting")

        defActionBar?.apply {
            displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
            setCustomView(R.layout.layout_header)

            val toolbar = customView.parent as androidx.appcompat.widget.Toolbar
            toolbar.setContentInsetsAbsolute(0, 0)

            back = customView.findViewById(R.id.btnBack)
            title = customView.findViewById<TextView>(R.id.txtTitle).toString()
            shopping = customView.findViewById(R.id.btnShopping)
            cart = customView.findViewById(R.id.btnCart)
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

    abstract fun setupEvents()
    abstract fun setValues()

}