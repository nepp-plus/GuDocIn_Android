package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.CardData

class MySaveMoneyPaymentRecyclerViewAdapter(

    val mContext: Context,
    val mList: ArrayList<CardData>
):RecyclerView.Adapter<MySaveMoneyPaymentRecyclerViewAdapter.MySaveMoneyPaymentViewHolder>()
{

    inner class MySaveMoneyPaymentViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val txtPointDay = view.findViewById<TextView>(R.id.txtPointDay)
        val txtGudocIn = view.findViewById<TextView>(R.id.txtGudocIn)
        val txtPointMoney = view.findViewById<TextView>(R.id.txtPointMoney)

        fun bind(data : CardData) {

            txtPointDay.text = data.createdAt.toString()
            txtPointMoney.text = data.amount.toString()


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySaveMoneyPaymentViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.my_savemoney_payment_item, parent, false)
        return MySaveMoneyPaymentViewHolder(row)

    }

    override fun onBindViewHolder(holder: MySaveMoneyPaymentViewHolder, position: Int) {


        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

    }
}