package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ReviewDetailActivity
import com.neppplus.gudocin_android.datas.ReviewData

class MyReviewListRecyclerviewAdapter (
    val mContext : Context,
    val mList: ArrayList<ReviewData>
        ) : RecyclerView.Adapter<MyReviewListRecyclerviewAdapter.MyReviewViewHolder>() {



    inner class  MyReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {


        val imgReviewPicture = view.findViewById<ImageView>(R.id.imgReviewPicture)
        val imgUserProfile = view.findViewById<ImageView>(R.id.imgUserProfile)
        val txtReviwer = view.findViewById<TextView>(R.id.txtReviwer)
        val txtReviewTitle = view.findViewById<TextView>(R.id.txtReviewTitle)

        fun bind(data: ReviewData) {
            txtReviwer.text = data.user.nickname
            txtReviewTitle.text = data.title
            Glide.with(mContext).load(data.product.imageUrl).into(imgReviewPicture)
            Glide.with(mContext).load(data.user.profileImageURL).into(imgUserProfile)

            imgReviewPicture.setOnClickListener {

                val myIntent = Intent(mContext, ReviewDetailActivity::class.java)
                myIntent.putExtra("review", data)
                mContext.startActivity(myIntent)
            }
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ??? {

        val row = LayoutInflater.from(mContext).inflate(R.layout.mymy_review_recyclerview_list_item, parent, false)
        return MyReviewViewHolder(row)
    }

    override fun onBindViewHolder(holder: ???, position: Int) {

    }

    override fun getItemCount() = mList.size
}