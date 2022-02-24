package com.neppplus.gudocin_android.adapters.reviews

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.activities.MainActivity
import com.neppplus.gudocin_android.activities.ReviewActivity
import com.neppplus.gudocin_android.adapters.MainBannerViewPagerAdapter
import com.neppplus.gudocin_android.datas.BannerData
import com.neppplus.gudocin_android.datas.ReviewData
import com.willy.ratingbar.BaseRatingBar
import java.util.*
import kotlin.collections.ArrayList

class ReviewListRecyclerVewAdapterForMain
    (val mContext: Context, val mList: List<ReviewData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val mBannerList = ArrayList<BannerData>()

    lateinit var mMainBannerViewPagerAdapter: MainBannerViewPagerAdapter

    var isBannerViewPagerInit = false

    inner class HeaderViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        val bannerViewPager = row.findViewById<ViewPager>(R.id.bannerViewPager)

        fun bind() {
            mMainBannerViewPagerAdapter = MainBannerViewPagerAdapter(
                (mContext as MainActivity).supportFragmentManager,
                mBannerList
            )

            bannerViewPager.adapter = mMainBannerViewPagerAdapter
//            바인드 할 때마다 페이징 코드가 누적됨 -> 최초 1회만 설정하도록
            if (!isBannerViewPagerInit) {
                var currentPage = 0
                val nextPage = {
                    currentPage++
                    if (currentPage == mBannerList.size) {
                        currentPage = 0
                    }
                    bannerViewPager.currentItem = currentPage
                }
                val myHandler = Handler(Looper.getMainLooper())
//            Timer 클래스 활용 ->  할 일(코드)을 2초마다 반복
                val timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        myHandler.post(nextPage)
                    }
                }, 2000, 2000)
                isBannerViewPagerInit = true
            }
        }

    }

    inner class ItemViewHolder(row: View) : RecyclerView.ViewHolder(row) {

        val imgReviewerImage = row.findViewById<ImageView>(R.id.imgReviewerImage)
        val txtReviewerNickName = row.findViewById<TextView>(R.id.txtReviewerNickName)
        val imgReviewThumbnail = row.findViewById<ImageView>(R.id.imgReviewThumbnail)
        val txtReviewTitle = row.findViewById<TextView>(R.id.txtReviewTitle)
        val txtProductName = row.findViewById<TextView>(R.id.txtProductName)
        val ratingBar = row.findViewById<BaseRatingBar>(R.id.ratingBar)

        val layoutReviewDetail = row.findViewById<LinearLayout>(R.id.layoutReviewDetail)

        fun bind(data: ReviewData) {
            Glide.with(mContext).load(data.user.profileImageURL).into(imgReviewerImage)
            txtReviewerNickName.text = "${data.user.nickname} 님의 리뷰"
            Glide.with(mContext).load(data.thumbNailImg).into(imgReviewThumbnail)
            txtReviewTitle.text = data.title
            txtProductName.text = data.product.name
            ratingBar.rating = data.score.toFloat()

            layoutReviewDetail.setOnClickListener {
                val myIntent = Intent(mContext, ReviewActivity::class.java)
                myIntent.putExtra("review", data)
                mContext.startActivity(myIntent)
            }
        }

    }

    val HEADER_VIEW_TYPE = 1000
    val REVIEW_ITEM_TYPE = 1001

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER_VIEW_TYPE
            else -> REVIEW_ITEM_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER_VIEW_TYPE -> {
                val row = LayoutInflater.from(mContext)
                    .inflate(R.layout.main_view_pager, parent, false)
                HeaderViewHolder(row)
            }
            else -> {
//                리뷰 아이템
                val row = LayoutInflater.from(mContext)
                    .inflate(R.layout.review_list_item_for_main, parent, false)
                ItemViewHolder(row)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.bind()
            }
            is ItemViewHolder -> {
                holder.bind(mList[position - 1])
            }
        }
    }

    override fun getItemCount() = mList.size + 1

}
