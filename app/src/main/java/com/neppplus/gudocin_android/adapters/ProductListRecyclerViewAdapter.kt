package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.activities.WriteReviewActivity
import com.neppplus.gudocin_android.datas.ProductData

class ProductListRecyclerViewAdapter(val mContext: Context, val mList: List<ProductData>) :
    RecyclerView.Adapter<ProductListRecyclerViewAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgProduct = view.findViewById<ImageView>(R.id.imgProduct)
        val txtStoreName = view.findViewById<TextView>(R.id.txtStoreName)
        val txtProductName = view.findViewById<TextView>(R.id.txtProductName)
        val txtPrice = view.findViewById<TextView>(R.id.txtPrice)
        val btnWriteReview = view.findViewById<Button>(R.id.btnWriteReview)

        fun bind(data: ProductData) {
            Glide.with(mContext).load(data.imageUrl).into(imgProduct)
            txtStoreName.text = data.store.name
            txtProductName.text = data.name
            txtPrice.text = data.getFormattedPrice()

            btnWriteReview.setOnClickListener {
                val myIntent = Intent(mContext, WriteReviewActivity::class.java)
                myIntent.putExtra("product", data)
                mContext.startActivity(myIntent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.product_list_item, parent, false)
        return ProductViewHolder(row)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}