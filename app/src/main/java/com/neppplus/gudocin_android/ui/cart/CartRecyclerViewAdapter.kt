package com.neppplus.gudocin_android.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.databinding.AdapterCartBinding
import com.neppplus.gudocin_android.model.cart.CartData
import com.neppplus.gudocin_android.network.RetrofitService

class CartRecyclerViewAdapter(private val mCartList: ArrayList<CartData>) :
    RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(view: View, data: CartData, position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class CartViewHolder(private val binding: AdapterCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        lateinit var retrofitService: RetrofitService

        fun bind(data: CartData) {
            binding.txtProduct.text = data.product.name
            binding.txtPrice.text = data.product.getFormattedPrice()
            Glide.with(itemView.context).load(data.product.imageUrl).into(binding.imgProduct)

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.imgDelete.setOnClickListener {
                    listener?.onItemClick(itemView, data, position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = AdapterCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(mCartList[position])
    }

    override fun getItemCount() = mCartList.size

}