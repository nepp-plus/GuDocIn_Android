package com.neppplus.gudocin_android.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.model.PaymentData

class PaymentRecyclerViewAdapter(
    val mContext: Context,
    val mList: ArrayList<PaymentData>
) : RecyclerView.Adapter<PaymentRecyclerViewAdapter.PaymentListViewHolder>() {

    inner class PaymentListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: PaymentData) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentListViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.adapter_payment, parent, false)
        return PaymentListViewHolder(row)
    }

    override fun onBindViewHolder(holder: PaymentListViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}