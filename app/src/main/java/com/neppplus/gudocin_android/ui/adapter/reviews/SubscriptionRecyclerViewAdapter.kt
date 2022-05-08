package com.neppplus.gudocin_android.ui.adapter.reviews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ui.activity.ReviewActivity
import com.neppplus.gudocin_android.model.ReviewData

class SubscriptionRecyclerViewAdapter(
    val mContext: Context,
    val mList: ArrayList<ReviewData>
) : RecyclerView.Adapter<SubscriptionRecyclerViewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layoutReviewCheck = view.findViewById<LinearLayout>(R.id.llCheckReview)
        val txtTitle = view.findViewById<TextView>(R.id.txtTitle)
        val txtProduct = view.findViewById<TextView>(R.id.txtProduct)
        val txtDate = view.findViewById<TextView>(R.id.txtDate)

        fun bind(data: ReviewData) {
            layoutReviewCheck.setOnClickListener {
                val myIntent = Intent(mContext, ReviewActivity::class.java)
                myIntent.putExtra("review", data) // 넘어갈 때 review id 들려 보내야 함
                mContext.startActivity(myIntent)
            }
            txtTitle.text = data.title
            txtProduct.text = data.product.name
            txtDate.text = data.createdAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val row = LayoutInflater.from(mContext)
            .inflate(R.layout.adapter_subscription, parent, false)
        return ReviewViewHolder(row)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}