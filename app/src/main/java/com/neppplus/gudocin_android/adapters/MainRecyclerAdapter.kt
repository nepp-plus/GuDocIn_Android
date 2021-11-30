package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.neppplus.gudocin_android.NavigationActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.ReviewData
import java.util.*

class MainRecyclerAdapter(val mContext:Context, val mList:List<ReviewData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val TYPE_HEADER = 0
    val TYPE_ITEM = 1

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val bannerViewPager = view.findViewById<ViewPager>(R.id.bannerViewPager)
        lateinit var mPagerAdapter : BannerViewPagerAdapter

        fun bind() {

            val tempList = arrayListOf(
                "https://publy.imgix.net/images/2021/08/24/1629783164_071axFm8po8k4vUlxbRXCmi7NgMHCtyqQ880y1fd.jpeg?fm=pjpg",
                "https://img1.daumcdn.net/thumb/R1280x0.fjpg/?fname=http://t1.daumcdn.net/brunch/service/user/zZX/image/PoUmWOJMQg8DHzrn9PIuCIuwCwk",
                "https://appservice-img.s3.amazonaws.com/apps/1y6qAY2wGRPrrCgZDbN9QB/KR/list/image?1543310731"
            )

            var currentPage = 0
            val runnable = {
                if (currentPage == tempList.size) {
                    currentPage = 0
                }

                bannerViewPager.currentItem = currentPage++
            }

            val myHandler = Handler(Looper.getMainLooper())
            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {
                    myHandler.post(runnable)
                }

            }, 0, 2500)

            Log.d("뷰페이저객체", bannerViewPager.toString())


            mPagerAdapter = BannerViewPagerAdapter( (mContext as NavigationActivity).supportFragmentManager, tempList )
            bannerViewPager.adapter = mPagerAdapter
        }

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
                HeaderViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(mContext).inflate(R.layout.main_review_list_item, parent, false)
                ItemViewHolder(view)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is HeaderViewHolder -> {
                holder.bind()
            }
            is ItemViewHolder -> {

            }
        }

    }

    override fun getItemCount() = mList.size + 1


}