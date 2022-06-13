package com.neppplus.gudocin_android.view.adapter.product

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
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.view.presenter.activity.write.WriteActivity

class ProductRecyclerViewAdapter(val mContext: Context, private val mList: List<ProductData>) :
  RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>() {

  inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
    private val txtStore: TextView = view.findViewById(R.id.txtStore)
    private val txtProduct: TextView = view.findViewById(R.id.txtProduct)
    private val txtPrice: TextView = view.findViewById(R.id.txtPrice)
    private val btnWriteReview: Button = view.findViewById(R.id.btnWriteReview)

    fun bind(data: ProductData) {
      Glide.with(mContext).load(data.imageUrl).into(imgProduct)
      txtStore.text = data.store.name
      txtProduct.text = data.name
      txtPrice.text = data.getFormattedPrice()

      btnWriteReview.setOnClickListener {
        val myIntent = Intent(mContext, WriteActivity::class.java)
        myIntent.putExtra("product", data)
        mContext.startActivity(myIntent)
      }
    }

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
    val row = LayoutInflater.from(mContext).inflate(R.layout.adapter_product, parent, false)
    return ProductViewHolder(row)
  }

  override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
    holder.bind(mList[position])
  }

  override fun getItemCount() = mList.size

}