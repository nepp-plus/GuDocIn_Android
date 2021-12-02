package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.content.Intent
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ReviewActivity
import com.neppplus.gudocin_android.ReviewDetailActivity
import com.neppplus.gudocin_android.datas.ReviewData

class ReviewRecyclerViewAdapterForProductList
    (val mContext: Context, val mList: List<ReviewData>) :
    RecyclerView.Adapter<ReviewRecyclerViewAdapterForProductList.ReviewViewHolder>() {

//   이 어댑터는 상품 상세에서 돌리는 리뷰 리스트에 사용합니다.

    inner class ReviewViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {



        val txtReviewerNickName = itemView.findViewById<TextView>(R.id.txtReviewerNickName)
        val imgReviewerImage = itemview.findViewById<ImageView>(R.id.imgReviewerImage)
        val txtReviewTitle = itemView.findViewById<TextView>(R.id.txtReviewTitle)
        val btnGotoReviewDetail = itemview.findViewById<LinearLayout>(R.id.btnGotoReviewDetail)


        fun bind(data: ReviewData) {
            txtReviewerNickName.text = data.user.nickname
            txtReviewTitle.text = data.title
            Glide.with(mContext).load(data.user.profileImageURL).into(imgReviewerImage)


            btnGotoReviewDetail.setOnClickListener {
//               리뷰 상세 페이지로 넘어가는 인텐트 추가 필요 (Activity명 나오면 추가 예정)
                val myIntent = Intent(mContext, ReviewDetailActivity::class.java)
                myIntent.putExtra("review",data)
                mContext.startActivity(myIntent)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {

        val row =
            LayoutInflater.from(mContext).inflate(R.layout.review_item_for_product_detail, parent, false)
        return ReviewViewHolder(row)

    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}
