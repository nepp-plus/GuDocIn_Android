package com.neppplus.gudocin_android.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.databinding.AdapterCartBinding
import com.neppplus.gudocin_android.model.cart.CartData

class CartRecyclerViewAdapter() : RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>() {
  private var listData: List<CartData>? = null

  fun setListData(listData: List<CartData>) {
    this.listData = listData
  }

  interface OnItemClickListener {
    fun onItemClick(position: Int)
  }

  private lateinit var listener: OnItemClickListener

  fun setOnItemClickListener(listener: OnItemClickListener) {
    this.listener = listener
  }

  inner class CartViewHolder(private val binding: AdapterCartBinding) : RecyclerView.ViewHolder(binding.root) {
    init {
      itemView.setOnClickListener {
        listener.onItemClick(adapterPosition)
      }
    }

    fun bind(data: CartData) {
      binding.txtProduct.text = data.product.name
      binding.txtPrice.text = data.product.getFormattedPrice()
      Glide.with(itemView.context).load(data.product.imageUrl).into(binding.imgProduct)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
    val binding = AdapterCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return CartViewHolder(binding)
  }

  override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
    listData?.let { holder.bind(it[position]) }
  }

  override fun getItemCount(): Int {
    if (listData == null) return 0
    return listData!!.size
  }
}