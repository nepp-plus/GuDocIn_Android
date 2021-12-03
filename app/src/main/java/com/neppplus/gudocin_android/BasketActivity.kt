package com.neppplus.gudocin_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.neppplus.gudocin_android.adapters.BasketListAdapter
import com.neppplus.gudocin_android.basket.Basket

class BasketActivity : AppCompatActivity() {

    var basketList = arrayListOf<Basket>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        val basketAdapter = BasketListAdapter(this, basketList)
//        basketListView.adapter = basketAdapter

    }

}