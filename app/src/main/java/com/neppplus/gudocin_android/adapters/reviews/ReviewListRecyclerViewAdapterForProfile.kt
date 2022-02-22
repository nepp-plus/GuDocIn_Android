package com.neppplus.gudocin_android.adapters.reviews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.activities.ReviewActivity
import com.neppplus.gudocin_android.datas.ReviewData

class ReviewListRecyclerViewAdapterForProfile(
    val mContext: Context,
    val mList: ArrayList<ReviewData>
) : RecyclerView.Adapter<ReviewListRecyclerViewAdapterForProfile.ReviewViewHolder>() {

    inner class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtReviewTitle = view.findViewById<TextView>(R.id.txtReviewTitle)
        val txtProductName = view.findViewById<TextView>(R.id.txtProductName)
        val txtReviewDate = view.findViewById<TextView>(R.id.txtReviewDate)
        val txtReviewCheck = view.findViewById<TextView>(R.id.txtReviewCheck)
//      val btnReviewCheck = view.findViewById<Button>(R.id.btnReviewCheck)

        fun bind(data: ReviewData) {
            txtReviewTitle.text = data.title
            txtProductName.text = data.product.name
            txtReviewDate.text = data.createdAt

            txtReviewCheck.setOnClickListener {
                val myIntent = Intent(mContext, ReviewActivity::class.java)
                myIntent.putExtra("review", data) // 넘어갈 때 review id 들려 보내야 함
                mContext.startActivity(myIntent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val row = LayoutInflater.from(mContext)
            .inflate(R.layout.review_list_item_for_profile, parent, false)
        return ReviewViewHolder(row)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}