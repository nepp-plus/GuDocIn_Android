package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.binding
import com.neppplus.gudocin_android.fragments.MainBannerFragment

class BannerViewPagerAdapter(val imageList: ArrayList<String>, val mContext: Context) :
    RecyclerView.Adapter<BannerViewPagerAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BannerViewPagerAdapter.CustomViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_banner_list, parent, false)
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



