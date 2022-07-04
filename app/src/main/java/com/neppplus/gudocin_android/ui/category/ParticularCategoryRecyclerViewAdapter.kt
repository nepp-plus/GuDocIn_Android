package com.neppplus.gudocin_android.ui.category

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.AdapterParticularCategoryBinding
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.ui.product.ProductActivity
import com.neppplus.gudocin_android.ui.review.ReviewActivity

class ParticularCategoryRecyclerViewAdapter(
  private val mProductList: List<ProductData>
) : RecyclerView.Adapter<ParticularCategoryRecyclerViewAdapter.ParticularCategoryViewHolder>() {

  inner class ParticularCategoryViewHolder(private val binding: AdapterParticularCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
    private var isReviewOpen = false

    fun bind(data: ProductData) {
      binding.txtProduct.text = data.name
      binding.txtStore.text = data.store.name
      binding.txtPrice.text = data.getFormattedPrice()
      Glide.with(itemView.context).load(data.imageUrl).into(binding.imgProduct)

      if (data.reviews.isEmpty()) {
        binding.llParticularReview.visibility = View.GONE
        binding.llReviewItem.visibility = View.GONE
      } else {
        binding.llParticularReview.visibility = View.VISIBLE

        val firstReview = data.reviews[0]
        binding.txtTitle.text = firstReview.title
        binding.txtReviewer.text = firstReview.user.nickname
        Glide.with(itemView.context).load(firstReview.user.profileImageURL).into(binding.imgProfile)
        firstReview.product = data

        binding.llReviewItem.setOnClickListener {
          val intent = Intent(itemView.context, ReviewActivity::class.java)
          intent.putExtra("review", firstReview)
          itemView.context.startActivity(intent)
        }

        if (!isReviewOpen) {
          binding.llParticularReview.setOnClickListener {
            binding.llReviewItem.visibility = View.VISIBLE
            binding.txtParticularReview.text = itemView.context.getString(R.string.review_close)
            isReviewOpen = true
            notifyDataSetChanged()
          }
        } else {
          binding.llParticularReview.setOnClickListener {
            binding.llReviewItem.visibility = View.GONE
            binding.txtParticularReview.text = itemView.context.getString(R.string.review_view_more)
            isReviewOpen = false
            notifyDataSetChanged()
          }
        }
      }

      binding.llRoot.setOnClickListener {
        val intent = Intent(itemView.context, ProductActivity::class.java)
        intent.putExtra("product_id", data)
        itemView.context.startActivity(intent)
      }
    }

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticularCategoryViewHolder {
    val binding = AdapterParticularCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ParticularCategoryViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ParticularCategoryViewHolder, position: Int) {
    holder.bind(mProductList[position])
  }

  override fun getItemCount() = mProductList.size

}