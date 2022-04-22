package com.neppplus.gudocin_android.ui.adapter.categories

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
import com.neppplus.gudocin_android.ui.activity.ProductActivity
import com.neppplus.gudocin_android.ui.activity.ReviewActivity
import com.neppplus.gudocin_android.model.ProductData

class DetailCategoryRecyclerViewAdapter(
    val mContext: Context,
    val mList: List<ProductData>
) : RecyclerView.Adapter<DetailCategoryRecyclerViewAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layoutRoot = view.findViewById<LinearLayout>(R.id.llRoot)
        val imgProduct = view.findViewById<ImageView>(R.id.imgProduct)
        val txtProduct = view.findViewById<TextView>(R.id.txtProduct)
        val txtStore = view.findViewById<TextView>(R.id.txtStore)
        val txtPrice = view.findViewById<TextView>(R.id.txtPrice)
        val txtDetailReview = view.findViewById<TextView>(R.id.txtDetailReview)
        val layoutReviewDetail = view.findViewById<LinearLayout>(R.id.llDetailReview)
        val layoutReviewItem = view.findViewById<LinearLayout>(R.id.llReviewItem)
        val imgProfile = view.findViewById<ImageView>(R.id.imgProfile)
        val txtTitle = view.findViewById<TextView>(R.id.txtTitle)
        val txtReviewer = view.findViewById<TextView>(R.id.txtReviewer)
        var isReviewOpen = false

        fun bind(data: ProductData) {
            Glide.with(mContext).load(data.imageUrl).into(imgProduct)
            txtProduct.text = data.name
            txtStore.text = data.store.name
            txtPrice.text = data.getFormattedPrice()

            if (data.reviews.size == 0) {
                layoutReviewDetail.visibility = View.GONE
                layoutReviewItem.visibility = View.GONE
            } else {
                layoutReviewDetail.visibility = View.VISIBLE

                val firstReview = data.reviews[0]
                Glide.with(mContext).load(firstReview.user.profileImageURL).into(imgProfile)
                txtTitle.text = firstReview.title
                txtReviewer.text = firstReview.user.nickname
                firstReview.product = data

                layoutReviewItem.setOnClickListener {
                    val myIntent = Intent(mContext, ReviewActivity::class.java)
                    myIntent.putExtra("review", firstReview)
                    mContext.startActivity(myIntent)
                }

                if (isReviewOpen == false) {
                    layoutReviewDetail.setOnClickListener {
                        layoutReviewItem.visibility = View.VISIBLE
                        txtDetailReview.text = "리뷰 닫기"
                        isReviewOpen = true
                        notifyDataSetChanged()
                    }
                } else {
                    layoutReviewDetail.setOnClickListener {
                        layoutReviewItem.visibility = View.GONE
                        txtDetailReview.text = "리뷰 더보기"
                        isReviewOpen = false
                        notifyDataSetChanged()
                    }
                }
            }

            layoutRoot.setOnClickListener {
                val myIntent = Intent(mContext, ProductActivity::class.java)
                myIntent.putExtra("product_id", data)
                mContext.startActivity(myIntent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val row = LayoutInflater.from(mContext)
            .inflate(R.layout.adapter_detail_category, parent, false)
        return ProductViewHolder(row)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}