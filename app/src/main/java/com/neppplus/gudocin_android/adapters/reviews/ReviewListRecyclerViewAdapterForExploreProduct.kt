package com.neppplus.gudocin_android.adapters.reviews

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
import com.neppplus.gudocin_android.activities.ReviewActivity
import com.neppplus.gudocin_android.datas.ReviewData

class ReviewListRecyclerViewAdapterForExploreProduct
    (val mContext: Context, val mList: List<ReviewData>) :
    RecyclerView.Adapter<ReviewListRecyclerViewAdapterForExploreProduct.ReviewViewHolder>() {

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val layoutReviewDetail = itemView.findViewById<LinearLayout>(R.id.layoutReviewDetail)
        val imgReviewer = itemView.findViewById<ImageView>(R.id.imgReviewer)
        val txtReviewTitle = itemView.findViewById<TextView>(R.id.txtReviewTitle)
        val txtReviewerName = itemView.findViewById<TextView>(R.id.txtReviewerName)

        fun bind(data: ReviewData) {
            txtReviewerName.text = data.user.nickname
            txtReviewTitle.text = data.title
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
                .inflate(R.layout.review_list_item_for_product, parent, false)
        return ReviewViewHolder(row)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}
