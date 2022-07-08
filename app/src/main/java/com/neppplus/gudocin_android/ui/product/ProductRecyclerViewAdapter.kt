package com.neppplus.gudocin_android.ui.product

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.databinding.AdapterProductBinding
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.ui.compose.ComposeActivity

class ProductRecyclerViewAdapter(private val mProductList: List<ProductData>) :
  RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>() {

  inner class ProductViewHolder(private val binding: AdapterProductBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: ProductData) {
      Glide.with(itemView.context).load(data.imageUrl).into(binding.imgProduct)
      binding.txtStore.text = data.store.name
      binding.txtProduct.text = data.name
      binding.txtPrice.text = data.getFormattedPrice()

      binding.btnWriteReview.setOnClickListener {
        val intent = Intent(itemView.context, ComposeActivity::class.java)
        intent.putExtra("product", data)
        itemView.context.startActivity(intent)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
    val binding = AdapterProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ProductViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
    holder.bind(mProductList[position])
  }

  override fun getItemCount() = mProductList.size

}