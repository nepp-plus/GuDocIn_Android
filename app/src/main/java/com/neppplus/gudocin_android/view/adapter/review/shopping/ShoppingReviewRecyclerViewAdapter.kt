package com.neppplus.gudocin_android.view.adapter.review.shopping

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.view.presenter.activity.review.ReviewActivity

class ShoppingReviewRecyclerViewAdapter
  (val mContext: Context, private val mList: List<ReviewData>) :
  RecyclerView.Adapter<ShoppingReviewRecyclerViewAdapter.ReviewViewHolder>() {

  inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val layoutReviewDetail: LinearLayout = itemView.findViewById(R.id.llDetailReview)
    private val imgReviewer: ImageView = itemView.findViewById(R.id.imgReviewer)
    private val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
    private val txtReviewer: TextView = itemView.findViewById(R.id.txtReviewer)

    fun bind(data: ReviewData) {
      txtReviewer.text = data.user.nickname
      txtTitle.text = data.title
      Glide.with(mContext).load(data.user.profileImageURL).into(imgReviewer)

      layoutReviewDetail.setOnClickListener {
        val myIntent = Intent(mContext, ReviewActivity::class.java)
        myIntent.putExtra("review", data) // 넘어갈 때 review id 들려 보내야 함
        mContext.startActivity(myIntent)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
    val row = LayoutInflater.from(mContext).inflate(R.layout.adapter_shopping_review, parent, false)
    return ReviewViewHolder(row)
  }

  override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
    holder.bind(mList[position])
  }

  override fun getItemCount() = mList.size

}
