package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.CardData

class CardInfoAdapter(
    val mContext: Context,
    val mList: List<CardData>
) : RecyclerView.Adapter<CardInfoAdapter.ViewHolder>() {

    interface OnItemClick {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClick {
        fun onItemLongClick(position: Int)
    }

    var oic: OnItemClick? = null
    var oilc: OnItemLongClick? = null

    fun setOnItemClick(onItemClick: OnItemClick) {
        this.oic = onItemClick
    }

    fun setOnItemLongClick(onItemLongClick: OnItemLongClick) {
        this.oilc = onItemLongClick
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtCardNickname = view.findViewById<TextView>(R.id.txtCardNickname)
        val txtCardNum = view.findViewById<TextView>(R.id.txtCardNum)

        fun bind(data: CardData, position: Int) {
            txtCardNickname.text = data.cardNickname
            txtCardNum.text = data.cardNum

            if (oic != null) {
                view.setOnClickListener {
                    oic!!.onItemClick(position)
                }
            }
            if (oilc != null) {
                view.setOnLongClickListener {
                    oilc!!.onItemLongClick(position)
                    return@setOnLongClickListener true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.card_info_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position], position)
    }

    override fun getItemCount() = mList.size

}