package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.ReviewData

class MainRecyclerAdapter(val mContext:Context, val mList:List<ReviewData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_HEADER = 0
    val TYPE_ITEM = 1

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {

            TYPE_HEADER -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.main_top_view, parent, false)
                return HeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.main_review_list_item, parent, false)
                return HeaderViewHolder(view)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is HeaderViewHolder -> {

            }
            is ItemViewHolder -> {

            }
        }

    }

    override fun getItemCount() = mList.size + 1


}