package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.PointLogData
import java.text.SimpleDateFormat

class MySaveMoneyPaymentRecyclerViewAdapter(

    val mContext: Context,
    val mList: ArrayList<PointLogData>
) : RecyclerView.Adapter<MySaveMoneyPaymentRecyclerViewAdapter.MySaveMoneyPaymentViewHolder>() {

    inner class MySaveMoneyPaymentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtPointDay = view.findViewById<TextView>(R.id.txtPointDay)
        val txtGudocIn = view.findViewById<TextView>(R.id.txtGuDocIn)
        val txtPointMoney = view.findViewById<TextView>(R.id.txtPointMoney)

        fun bind(data: PointLogData) {
//            txtPointDay.text = data.createdAt.toString()
            txtPointMoney.text = data.amount.toString()
            txtGudocIn.text = data.payment.subscription.product.store.name

            val sdf = SimpleDateFormat("yyyy.MM.dd ")
            txtPointDay.text = sdf.format(data.createdAt)
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MySaveMoneyPaymentViewHolder {
        val row =
            LayoutInflater.from(mContext).inflate(R.layout.my_save_money_payment_item, parent, false)
        return MySaveMoneyPaymentViewHolder(row)
    }

    override fun onBindViewHolder(holder: MySaveMoneyPaymentViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}