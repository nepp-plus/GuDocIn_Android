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
import com.neppplus.gudocin_android.ReviewActivity
import com.neppplus.gudocin_android.datas.ReviewData

class ReviewRecyclerViewAdapterForMain
    (val mContext: Context, val mList: List<ReviewData>) :
    RecyclerView.Adapter<ReviewRecyclerViewAdapterForMain.ReviewViewHolder>() {


    inner class ReviewViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {


        val txtProductPrice = itemView.findViewById<TextView>(R.id.txtProductPrice)
        val txtProductName = itemView.findViewById<TextView>(R.id.txtProductName)
        val txtReviewerNickName = itemView.findViewById<TextView>(R.id.txtReviewerNickName)
        val imgReviewSomeNail = itemview.findViewById<ImageView>(R.id.imgReviewSomeNail)
        val imgReviewerImage = itemview.findViewById<ImageView>(R.id.imgReviewerImage)
        val btnGotoReviewDetail1 = itemview.findViewById<LinearLayout>(R.id.btnGotoReviewDetail1)
        val btnGotoReviewDetail2 = itemview.findViewById<LinearLayout>(R.id.btnGotoReviewDetail2)
        val btnOpenPreview = itemview.findViewById<LinearLayout>(R.id.btnOpenPreview)
        val txtCheckedContents = itemview.findViewById<TextView>(R.id.txtCheckedContents)
        val btnWriteRivew = itemview.findViewById<TextView>(R.id.btnWriteRivew)



        fun bind(data: ReviewData) {
            txtProductName.text = data.product.name
            txtProductPrice.text = data.product.price.toString()
            txtReviewerNickName.text = data.user.nickname
            Glide.with(mContext).load(data.product.imageUrl).into(imgReviewSomeNail)
            Glide.with(mContext).load(data.user.profileImageURL).into(imgReviewerImage)

            btnGotoReviewDetail1.setOnClickListener {
//               리뷰 상세 페이지로 넘어가는 인텐트 추가 필요 (Activity명 나오면 추가 예정)
            }
            btnGotoReviewDetail2.setOnClickListener {
//               리뷰 상세 페이지로 넘어가는 인텐트 추가 필요 (Activity명 나오면 추가 예정)
            }
            btnOpenPreview.setOnClickListener {
                txtCheckedContents.visibility = View.VISIBLE
            }

            btnWriteRivew.setOnClickListener {
                val myIntent = Intent(mContext, ReviewActivity::class.java)
                myIntent.putExtra("product", data)
                mContext.startActivity(myIntent)


            }





        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {


        val row =
            LayoutInflater.from(mContext).inflate(R.layout.review_item_for_main, parent, false)
        return ReviewViewHolder(row)

    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}
