package com.neppplus.gudocin_android.view.adapter.review.subscription

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.databinding.AdapterSubscriptionBinding
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.view.presenter.activity.review.ReviewActivity

class SubscriptionRecyclerViewAdapter(
  val mContext: Context,
  private val mList: ArrayList<ReviewData>
) : RecyclerView.Adapter<SubscriptionRecyclerViewAdapter.ReviewViewHolder>() {

  inner class ReviewViewHolder(private val binding: AdapterSubscriptionBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: ReviewData) {
      binding.llCheckReview.setOnClickListener {
        val myIntent = Intent(mContext, ReviewActivity::class.java)
        myIntent.putExtra("review", data) // 넘어갈 때 review id 들려 보내야 함
        mContext.startActivity(myIntent)
      }
      binding.txtTitle.text = data.title
      binding.txtProduct.text = data.product.name
      binding.txtDate.text = data.createdAt
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
    val binding = AdapterSubscriptionBinding.inflate(LayoutInflater.from(mContext), parent, false)
    return ReviewViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
    holder.bind(mList[position])
  }

  override fun getItemCount() = mList.size

}