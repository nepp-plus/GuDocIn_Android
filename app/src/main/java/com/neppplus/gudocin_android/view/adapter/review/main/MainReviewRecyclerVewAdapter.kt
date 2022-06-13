package com.neppplus.gudocin_android.view.adapter.review.main

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
import com.neppplus.gudocin_android.model.banner.BannerData
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.view.adapter.banner.BannerViewPagerAdapter
import com.neppplus.gudocin_android.view.presenter.activity.main.MainActivity
import com.neppplus.gudocin_android.view.presenter.activity.review.ReviewActivity
import com.willy.ratingbar.BaseRatingBar
import java.util.*

class MainReviewRecyclerVewAdapter
  (val mContext: Context, private val mList: List<ReviewData>) :
  RecyclerView.Adapter<RecyclerView.ViewHolder>() {

  val mBannerList = ArrayList<BannerData>()

  lateinit var mBannerViewPagerAdapter: BannerViewPagerAdapter

  var isBannerViewPagerInit = false

  inner class HeaderViewHolder(row: View) : RecyclerView.ViewHolder(row) {
    private val bannerViewPager: ViewPager = row.findViewById(R.id.vpBanner)

    fun bind() {
      mBannerViewPagerAdapter = BannerViewPagerAdapter((mContext as MainActivity).supportFragmentManager, mBannerList)

      bannerViewPager.adapter = mBannerViewPagerAdapter
      // 바인드 할 때마다 페이징 코드가 누적됨 -> 최초 1회만 설정하도록
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
        // Timer 클래스 활용 ->  할 일(코드)을 2초마다 반복
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
    private val imgReviewer: ImageView = row.findViewById(R.id.imgReviewer)
    private val txtNickName: TextView = row.findViewById(R.id.txtNickName)
    private val imgThumbnail: ImageView = row.findViewById(R.id.imgThumbnail)
    private val txtTitle: TextView = row.findViewById(R.id.txtTitle)
    private val txtProduct: TextView = row.findViewById(R.id.txtProduct)
    private val ratingBar: BaseRatingBar = row.findViewById(R.id.ratingBar)
    private val layoutReviewDetail: LinearLayout = row.findViewById(R.id.llDetailReview)

    fun bind(data: ReviewData) {
      Glide.with(mContext).load(data.user.profileImageURL).into(imgReviewer)
      txtNickName.text = "${data.user.nickname} 님의 리뷰"
      Glide.with(mContext).load(data.thumbNailImg).into(imgThumbnail)
      txtTitle.text = data.title
      txtProduct.text = data.product.name
      ratingBar.rating = data.score.toFloat()

      layoutReviewDetail.setOnClickListener {
        val myIntent = Intent(mContext, ReviewActivity::class.java)
        myIntent.putExtra("review", data)
        mContext.startActivity(myIntent)
      }
    }
  }

  private val HEADER_VIEW_TYPE = 1000
  private val REVIEW_ITEM_TYPE = 1001

  override fun getItemViewType(position: Int): Int {
    return when (position) {
      0 -> HEADER_VIEW_TYPE
      else -> REVIEW_ITEM_TYPE
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return when (viewType) {
      HEADER_VIEW_TYPE -> {
        val row = LayoutInflater.from(mContext).inflate(R.layout.viewpager_home, parent, false)
        HeaderViewHolder(row)
      }
      else -> {
        val row = LayoutInflater.from(mContext).inflate(R.layout.adapter_main_review, parent, false)
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
