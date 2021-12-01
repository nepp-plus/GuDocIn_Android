package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.NavigationActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.BannerData
import com.neppplus.gudocin_android.datas.ReviewData
import java.util.*
import kotlin.collections.ArrayList

class RecyclerVewAdapterForMain
    (val mContext: Context, val mList: List<ReviewData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val mBannerList = ArrayList<BannerData>()
    lateinit var mBannerViewPagerAdapter: BannerViewPagerAdapter

    inner class HeaderViewHolder(row: View) : RecyclerView.ViewHolder(row) {

//     카테고리 부분 작성
//        val mainCategoryFragment = row.findViewById<Fragment>(R.id.mainCategoryFragment)

        val bannerViewPager = row.findViewById<ViewPager>(R.id.bannerViewPager)

        fun bind() {
            mBannerViewPagerAdapter = BannerViewPagerAdapter(
                (mContext as NavigationActivity).supportFragmentManager,
                mBannerList
            )

            bannerViewPager.adapter = mBannerViewPagerAdapter

            var currentPage = 0

            val nextPage = {

                currentPage++

                if (currentPage ==mBannerList.size){
                    currentPage = 0
                }
                bannerViewPager.currentItem = currentPage

            }
            val myHandler = Handler(Looper.getMainLooper())

//            Timer클래스 활용 =>  할 일 (코드)를 2초마다 반복.

            val timer = Timer()
            timer.schedule(object : TimerTask() {
                override fun run() {

                    myHandler.post(nextPage)

                }

            }, 2000, 2000)


        }


    }

    inner class ItemViewHolder(row: View) : RecyclerView.ViewHolder(row) {
        val txtReviewerNickName = row.findViewById<TextView>(R.id.txtReviewerNickName)
        val txtProductName = row.findViewById<TextView>(R.id.txtProductName)
        val txtProductPrice = row.findViewById<TextView>(R.id.txtProductPrice)
        val btnOpenPreview = row.findViewById<LinearLayout>(R.id.btnOpenPreview)
        val imgReviewSomeNail = row.findViewById<ImageView>(R.id.imgReviewSomeNail)
        val imgReviewerImage = row.findViewById<ImageView>(R.id.imgReviewerImage)


        fun bind(data: ReviewData) {

            txtReviewerNickName.text = data.user.nickname
            txtProductName.text = data.product.name
            txtProductPrice.text = data.product.price.toString()
            Glide.with(mContext).load(data.product.imageUrl).into(imgReviewSomeNail)
            Glide.with(mContext).load(data.user.profileImageURL).into(imgReviewerImage)


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
                    .inflate(R.layout.main_top_viewpager_item, parent, false)
                HeaderViewHolder(row)
            }
            else -> {
//                리뷰 아이템

                val row = LayoutInflater.from(mContext)
                    .inflate(R.layout.review_item_for_main, parent, false)
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
