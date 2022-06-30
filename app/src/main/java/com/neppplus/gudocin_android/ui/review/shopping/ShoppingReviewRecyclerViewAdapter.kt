package com.neppplus.gudocin_android.ui.review.shopping

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.databinding.AdapterShoppingReviewBinding
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.ui.review.ReviewActivity

class ShoppingReviewRecyclerViewAdapter
  (val mContext: Context, private val mList: List<ReviewData>) :
  RecyclerView.Adapter<ShoppingReviewRecyclerViewAdapter.ReviewViewHolder>() {

  inner class ReviewViewHolder(private val binding: AdapterShoppingReviewBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: ReviewData) {
      binding.txtReviewer.text = data.user.nickname
      binding.txtTitle.text = data.title
      Glide.with(mContext).load(data.user.profileImageURL).into(binding.imgReviewer)

      binding.llDetailReview.setOnClickListener {
        val myIntent = Intent(mContext, ReviewActivity::class.java)
        myIntent.putExtra("review", data) // 넘어갈 때 review id 들려 보내야 함
        mContext.startActivity(myIntent)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
    val binding = AdapterShoppingReviewBinding.inflate(LayoutInflater.from(mContext), parent, false)
    return ReviewViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
    holder.bind(mList[position])
  }

  override fun getItemCount() = mList.size

}
