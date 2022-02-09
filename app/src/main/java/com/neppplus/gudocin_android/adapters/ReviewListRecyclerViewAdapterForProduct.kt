package com.neppplus.gudocin_android.adapters

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
import com.neppplus.gudocin_android.ReviewDetailActivity
import com.neppplus.gudocin_android.datas.ReviewData

class ReviewListRecyclerViewAdapterForProduct
    (val mContext: Context, val mList: List<ReviewData>) :
    RecyclerView.Adapter<ReviewListRecyclerViewAdapterForProduct.ReviewViewHolder>() {

    // 이 어댑터는 상품 상세화면의 리뷰 리스트에 사용합니다
    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtReviewWriterName = itemView.findViewById<TextView>(R.id.txtReviewWriterName)
        val imgReviewerImage = itemView.findViewById<ImageView>(R.id.imgReviewerImage)
        val txtReviewTitle = itemView.findViewById<TextView>(R.id.txtReviewTitle)
        val btnGotoReviewDetail = itemView.findViewById<LinearLayout>(R.id.btnGotoReviewDetail)

        fun bind(data: ReviewData) {
            txtReviewWriterName.text = data.user.nickname
            txtReviewTitle.text = data.title
            Glide.with(mContext).load(data.user.profileImageURL).into(imgReviewerImage)

            btnGotoReviewDetail.setOnClickListener {
                val myIntent = Intent(mContext, ReviewDetailActivity::class.java)
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
