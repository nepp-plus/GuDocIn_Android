package com.neppplus.gudocin_android.view.adapter.review.subscription

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.view.presenter.activity.review.ReviewActivity

class SubscriptionRecyclerViewAdapter(
  val mContext: Context,
  private val mList: ArrayList<ReviewData>
) : RecyclerView.Adapter<SubscriptionRecyclerViewAdapter.ReviewViewHolder>() {

  inner class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val layoutReviewCheck: LinearLayout = view.findViewById(R.id.llCheckReview)
    private val txtTitle: TextView = view.findViewById(R.id.txtTitle)
    private val txtProduct: TextView = view.findViewById(R.id.txtProduct)
    private val txtDate: TextView = view.findViewById(R.id.txtDate)

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
    val row = LayoutInflater.from(mContext).inflate(R.layout.adapter_subscription, parent, false)
    return ReviewViewHolder(row)
  }

  override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
    holder.bind(mList[position])
  }

  override fun getItemCount() = mList.size

}