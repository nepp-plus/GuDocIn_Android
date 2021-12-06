package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.BasketData

class BasketRecyclerAdapter(val mContext: Context, val mList: List<BasketData>) :
    RecyclerView.Adapter<BasketRecyclerAdapter.BasketViewHolder>() {

    inner class BasketViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgBasketPhoto = view.findViewById<ImageView>(R.id.imgBasketPhoto)
        val txtBasketProductName = view.findViewById<TextView>(R.id.txtBasketProductName)
        val txtBasketProductPrice2 = view.findViewById<TextView>(R.id.txtBasketProductPrice2)
        val txtTotalPrice = view.findViewById<TextView>(R.id.txtTotalPrice)

        fun bind(data: BasketData) {

            txtBasketProductName.text = data.name
            txtBasketProductPrice2.text = data.price.toString()
            txtTotalPrice.text = data.price.toString()

            Glide.with(mContext).load(data.imageURL).into(imgBasketPhoto)

            txtBasketProductPrice2.text = data.getFormattedPrice()
            txtTotalPrice.text = data.getFormattedPrice()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.fragment_basket_list, parent, false)
        return BasketViewHolder(row)

    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}