package com.neppplus.gudocin_android.view.adapter.product

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.databinding.AdapterProductBinding
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.view.activity.write.WriteActivity

class ProductRecyclerViewAdapter(private val mList: List<ProductData>) :
  RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>() {

  inner class ProductViewHolder(private val binding: AdapterProductBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: ProductData) {
      Glide.with(itemView.context).load(data.imageUrl).into(binding.imgProduct)
      binding.txtStore.text = data.store.name
      binding.txtProduct.text = data.name
      binding.txtPrice.text = data.getFormattedPrice()

      binding.btnWriteReview.setOnClickListener {
        val myIntent = Intent(itemView.context, WriteActivity::class.java)
        myIntent.putExtra("product", data)
        itemView.context.startActivity(myIntent)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
    val binding = AdapterProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ProductViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
    holder.bind(mList[position])
  }

  override fun getItemCount() = mList.size

}