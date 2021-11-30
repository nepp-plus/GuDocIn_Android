package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R

class BannerViewPagerAdapter(val imageList: ArrayList<String>, val mContext: Context) :
    RecyclerView.Adapter<BannerViewPagerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerViewPagerAdapter.CustomViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.banner_list, parent, false)
        return CustomViewHolder(view).apply {
            itemView.setOnClickListener {

            }
        }
    }

    override fun onBindViewHolder(holder: BannerViewPagerAdapter.CustomViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {

        return Int.MAX_VALUE

    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.mainBannerViewPager)
    }

}



