package com.neppplus.gudocin_android.ui.review.subscription

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.databinding.AdapterSubscriptionBinding
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.ui.review.ReviewActivity

class SubscriptionRecyclerViewAdapter(
    private val mReviewList: ArrayList<ReviewData>
) : RecyclerView.Adapter<SubscriptionRecyclerViewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(private val binding: AdapterSubscriptionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ReviewData) {
            binding.txtTitle.text = data.title
            binding.txtProduct.text = data.product.name
            binding.txtDate.text = data.createdAt

            binding.llCheckReview.setOnClickListener {
                val intent = Intent(itemView.context, ReviewActivity::class.java)
                intent.putExtra("review", data) // 넘어갈 때 review id 들려 보내야 함
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val binding =
            AdapterSubscriptionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReviewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(mReviewList[position])
    }

    override fun getItemCount() = mReviewList.size

}