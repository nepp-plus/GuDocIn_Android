package com.neppplus.gudocin_android.ui.payment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.model.payment.PaymentData

class PaymentRecyclerViewAdapter(
    private val mList: ArrayList<PaymentData>
) : RecyclerView.Adapter<PaymentRecyclerViewAdapter.PaymentViewHolder>() {

    inner class PaymentViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentViewHolder {
        val row =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_payment, parent, false)
        return PaymentViewHolder(row)
    }

    override fun onBindViewHolder(holder: PaymentViewHolder, position: Int) {
        mList[position]
    }

    override fun getItemCount() = mList.size

}