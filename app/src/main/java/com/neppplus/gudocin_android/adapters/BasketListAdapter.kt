package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.basket.Basket

class BasketListAdapter(val context: Context, val basketList: ArrayList<Basket>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View = LayoutInflater.from(context).inflate(R.layout.basket_list_item, null)

        val basketPhoto = view.findViewById<ImageView>(R.id.imgBasketPhoto)
        val basketProductName = view.findViewById<TextView>(R.id.txtBasketProductName)
        val basketProductPrice = view.findViewById<TextView>(R.id.txtBasketProductPrice)
        val basketProductOption = view.findViewById<TextView>(R.id.txtBasketProductOption)

        val basket = basketList[position]
        val resourceId =
            context.resources.getIdentifier(basket.photo, "drawable", context.packageName)
        basketPhoto.setImageResource(resourceId)
        basketProductName.text = basket.name
        basketProductPrice.text = basket.price
        basketProductOption.text = basket.option

        return view
    }

    override fun getItem(position: Int): Any {
        return basketList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return basketList.size
    }
}