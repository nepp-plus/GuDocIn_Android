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
import com.neppplus.gudocin_android.api.ServerAPI
import com.neppplus.gudocin_android.api.ServerAPIInterface
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.BasketData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasketRecyclerAdapter(val mContext: Context, val mList: List<BasketData>) :
    RecyclerView.Adapter<BasketRecyclerAdapter.BasketViewHolder>() {

    inner class BasketViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgBasketPhoto = view.findViewById<ImageView>(R.id.imgBasketPhoto)
        val txtBasketProductName = view.findViewById<TextView>(R.id.txtBasketProductName)
        val txtBasketProductPrice = view.findViewById<TextView>(R.id.txtBasketProductPrice)

        lateinit var apiService: ServerAPIInterface

        val retrofit = ServerAPI.getRetrofit(mContext)

        val imgDeleteSubscribe = view.findViewById<ImageView>(R.id.imgDeleteSubscribe)

        fun bind(data: BasketData) {

            txtBasketProductName.text = data.product.name
            txtBasketProductPrice.text = data.product.price.toString()

            Glide.with(mContext).load(data.product.imageUrl).into(imgBasketPhoto)

            txtBasketProductPrice.text = data.product.getFormatedPrice()

            apiService = retrofit.create(ServerAPIInterface::class.java)

            imgDeleteSubscribe.setOnClickListener {

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.basket_list_item, parent, false)
        return BasketViewHolder(row)

    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}