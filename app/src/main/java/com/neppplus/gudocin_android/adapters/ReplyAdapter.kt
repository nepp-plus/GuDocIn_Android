package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.ReplyData

class ReplyAdapter(
    val mContext: Context,
    val resId: Int,
    val mList: List<ReplyData>) : ArrayAdapter<ReplyData>(mContext,resId,mList) {


    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
        if (tempRow == null){
            tempRow = mInflater.inflate(R.layout.reply_list_item,null)
        }

        val row = tempRow!!
        return row

    }
}