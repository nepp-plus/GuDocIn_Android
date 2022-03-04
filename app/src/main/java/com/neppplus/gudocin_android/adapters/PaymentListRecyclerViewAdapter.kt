package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.PaymentData

class PaymentListRecyclerViewAdapter(
    val mContext: Context,
    val mList: ArrayList<PaymentData>
) : RecyclerView.Adapter<PaymentListRecyclerViewAdapter.PaymentListViewHolder>() {

    inner class PaymentListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: PaymentData) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentListViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.payment_list_item, parent, false)
        return PaymentListViewHolder(row)
    }

    override fun onBindViewHolder(holder: PaymentListViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}