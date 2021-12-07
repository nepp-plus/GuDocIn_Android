package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.datas.ProductData

class MyPurchaseListRecyclerviewAdapter(
    val mContext : Context,
    val mList: ArrayList<ProductData>
) : RecyclerView.Adapter<MyPurchaseListRecyclerviewAdapter.MyPucrchaseListViewHolder>() {


    inner class MyPucrchaseListViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ??? {

    }

    override fun onBindViewHolder(holder: ???, position: Int) {

    }

    override fun getItemCount(): Int {

    }
}