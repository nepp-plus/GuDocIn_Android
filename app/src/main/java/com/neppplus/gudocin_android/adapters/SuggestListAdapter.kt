package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.ProductData


class SuggestListAdapter(val mContext: Context, resId: Int, val mList: ArrayList<ProductData>)
    : ArrayAdapter<ProductData>(mContext,resId,mList) {


    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var tempRow = convertView
        if (tempRow == null){
            tempRow = mInflater.inflate(R.layout.simple_list_item_1,null)
        }

        val row = tempRow!!
        val data = mList[position]

        val txtProductName = row.findViewById<TextView>(R.id.txtProductName)

        txtProductName.text = data.name


        return row

    }
}