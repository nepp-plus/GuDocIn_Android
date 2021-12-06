package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.BasketData

class BasketAdapter(

    val mContext: Context,
    resId: Int,
    val mList: List<BasketData>

) : ArrayAdapter<BasketData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = mInflater.inflate(R.layout.basket_list_item, null)
        }

        val row = tempRow!!

        val data = mList[position]

        val txtBasketName = row.findViewById<TextView>(R.id.txtBasketProductName)
        val txtBasketProductPrice2 = row.findViewById<TextView>(R.id.txtBasketProductPrice2)
        val txtTotalPrice = row.findViewById<TextView>(R.id.txtTotalPrice)
//        val txtBasketOption = row.findViewById<TextView>(R.id.txtBasketProductOption)

        txtBasketName.text = data.name
        txtBasketProductPrice2.text = data.price.toString()
        txtTotalPrice.text = data.price.toString()
//        txtBasketOption.text = data.option

        return row

    }

}