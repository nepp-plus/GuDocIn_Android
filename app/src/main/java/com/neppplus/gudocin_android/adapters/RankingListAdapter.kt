package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.ReviewRakingData

class RankingListAdapter(
    val mContext: Context,
    val mList: List<ReviewRakingData>) : RecyclerView.Adapter<RankingListAdapter.RakingViewHolder>() {

    inner class RakingViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RakingViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.ranking_list_item, parent, false)
        return RakingViewHolder(row)

    }

    override fun onBindViewHolder(holder: RakingViewHolder, position: Int) {

    }

    override fun getItemCount() = mList.size

}