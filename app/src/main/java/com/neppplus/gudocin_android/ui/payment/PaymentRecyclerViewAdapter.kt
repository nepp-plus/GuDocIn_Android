package com.neppplus.gudocin_android.ui.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.databinding.AdapterPaymentBinding
import com.neppplus.gudocin_android.model.payment.PaymentData

class PaymentRecyclerViewAdapter(
  private val mList: ArrayList<PaymentData>
) : RecyclerView.Adapter<PaymentRecyclerViewAdapter.PaymentViewHolder>() {

  inner class PaymentViewHolder(private val binding: AdapterPaymentBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: PaymentData) {}
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
    val binding = AdapterPaymentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return PaymentViewHolder(binding)
  }

  override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
    holder.bind(mList[position])
  }

  override fun getItemCount() = mList.size

}