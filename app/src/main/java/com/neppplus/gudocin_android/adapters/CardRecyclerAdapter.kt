package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.CardData

class CardRecyclerAdapter(val mContext: Context, val mList: List<CardData>) :
    RecyclerView.Adapter<CardRecyclerAdapter.CardViewHolder>() {

    inner class CardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtCardNickname = view.findViewById<TextView>(R.id.txtCardNickname)
        val txtCardNum = view.findViewById<TextView>(R.id.txtCardNum)
        val txtCardValidity = view.findViewById<TextView>(R.id.txtCardValidity)
        val txtCardBirthday = view.findViewById<TextView>(R.id.txtCardBirthday)
//        val txtCardPassword = view.findViewById<TextView>(R.id.txtCardPassword)

        fun bind(data: CardData) {
            txtCardNickname.text = data.cardNickname
            txtCardNum.text = data.cardNum
            txtCardValidity.text = data.cardValid
            txtCardBirthday.text = data.cardBirthday
//            txtCardPassword.text = data.cardPwDigit
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val row =
            LayoutInflater.from(mContext).inflate(R.layout.card_management_list_item, parent, false)
        return CardViewHolder(row)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}