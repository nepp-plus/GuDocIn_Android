package com.neppplus.gudocin_android.ui.review.main

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.AdapterHomeBinding
import com.neppplus.gudocin_android.databinding.AdapterReviewBinding
import com.neppplus.gudocin_android.model.banner.BannerData
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.ui.banner.BannerViewPagerAdapter
import com.neppplus.gudocin_android.ui.main.MainActivity
import com.neppplus.gudocin_android.ui.review.ReviewActivity
import java.util.*

class MainRecyclerVewAdapter(
    private val mList: List<ReviewData>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val mBannerList = ArrayList<BannerData>()

    lateinit var mBannerViewPagerAdapter: BannerViewPagerAdapter

    var isBannerViewPagerInit = false

    inner class HeaderViewHolder(private val binding: AdapterHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind() {
            mBannerViewPagerAdapter = BannerViewPagerAdapter(
                (itemView.context as MainActivity)
                    .supportFragmentManager, mBannerList
            )

            // 바인드 시 페이징 코드 누적 -> 최초 1회만 설정
            binding.vpBanner.adapter = mBannerViewPagerAdapter
            if (!isBannerViewPagerInit) {
                var currentPage = 0

                val nextPage = {
                    currentPage++
                    if (currentPage == mBannerList.size) currentPage = 0
                    binding.vpBanner.currentItem = currentPage
                }

                // Timer 클래스 활용 -> 2초마다 반복
                val handler = Handler(Looper.getMainLooper())
                val timer = Timer()

                timer.schedule(object : TimerTask() {
                    override fun run() {
                        handler.post(nextPage)
                    }
                }, 2000, 2000)

                isBannerViewPagerInit = true
            }
        }
    }

    inner class ItemViewHolder(private val binding: AdapterReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ReviewData) {
            Glide.with(itemView.context).load(data.user.profileImageURL).into(binding.imgReviewer)
            Glide.with(itemView.context).load(data.thumbNailImg).into(binding.imgThumbnail)

            binding.txtNickName.text = itemView.context.getString(R.string.review_user)
                .replace("{user}", data.user.nickname)
            binding.txtTitle.text = data.title
            binding.txtProduct.text = data.product.name

            binding.ratingBar.rating = data.score.toFloat()

            binding.llDetailReview.setOnClickListener {
                val intent = Intent(itemView.context, ReviewActivity::class.java)
                intent.putExtra("review", data)
                itemView.context.startActivity(intent)
            }
        }
    }

    private val headerViewType = 1000
    private val itemViewType = 1001

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> headerViewType
            else -> itemViewType
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            headerViewType -> {
                val binding =
                    AdapterHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(binding)
            }

            else -> {
                val binding =
                    AdapterReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ItemViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.onBind()
            }

            is ItemViewHolder -> {
                holder.bind(mList[position - 1])
            }
        }
    }

    override fun getItemCount() = mList.size + 1

}