package com.neppplus.gudocin_android.ui.adapter.reviews

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
import com.neppplus.gudocin_android.ui.activity.ReviewActivity
import com.neppplus.gudocin_android.model.ReviewData

class ShoppingReviewRecyclerViewAdapter
    (val mContext: Context, val mList: List<ReviewData>) :
    RecyclerView.Adapter<ShoppingReviewRecyclerViewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val layoutReviewDetail = itemView.findViewById<LinearLayout>(R.id.llDetailReview)
        val imgReviewer = itemView.findViewById<ImageView>(R.id.imgReviewer)
        val txtTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        val txtReviewer = itemView.findViewById<TextView>(R.id.txtReviewer)

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
        val row =
            LayoutInflater.from(mContext)
                .inflate(R.layout.adapter_shopping_review, parent, false)
        return ReviewViewHolder(row)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}
