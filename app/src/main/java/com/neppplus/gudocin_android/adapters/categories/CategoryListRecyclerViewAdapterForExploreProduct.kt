package com.neppplus.gudocin_android.adapters.categories

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
import com.neppplus.gudocin_android.activities.ProductActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.activities.ReviewActivity
import com.neppplus.gudocin_android.datas.ProductData

class CategoryListRecyclerViewAdapterForExploreProduct(
    val mContext: Context,
    val mList: List<ProductData>
) :
    RecyclerView.Adapter<CategoryListRecyclerViewAdapterForExploreProduct.ProductViewHolder>() {

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val layoutRoot = view.findViewById<LinearLayout>(R.id.layoutRoot)
        val imgProduct = view.findViewById<ImageView>(R.id.imgProduct)
        val txtStoreName = view.findViewById<TextView>(R.id.txtStoreName)
        val txtProductName = view.findViewById<TextView>(R.id.txtProductName)
        val txtPrice = view.findViewById<TextView>(R.id.txtPrice)
        val layoutReviewDetail = view.findViewById<LinearLayout>(R.id.layoutReviewDetail)
        val txtReviewDetail = view.findViewById<TextView>(R.id.txtReviewDetail)

        val layoutReviewItem = view.findViewById<LinearLayout>(R.id.layoutReviewItem)
        val imgReview = view.findViewById<ImageView>(R.id.imgReview)
        val txtReviewTitle = view.findViewById<TextView>(R.id.txtReviewTitle)
        val txtReviewWriter = view.findViewById<TextView>(R.id.txtReviewer)
        val imgReviewDetail = view.findViewById<ImageView>(R.id.imgReviewDetail)

        var isReviewOpen = false

        fun bind(data: ProductData) {
            Glide.with(mContext).load(data.imageUrl).into(imgProduct)
            txtStoreName.text = data.store.name
            txtProductName.text = data.name
            txtPrice.text = data.getFormattedPrice()

            if (data.reviews.size == 0) {
                layoutReviewDetail.visibility = View.GONE
                layoutReviewItem.visibility = View.GONE
            } else {
                layoutReviewDetail.visibility = View.VISIBLE
                val firstReview = data.reviews[0]
                Glide.with(mContext).load(firstReview.user.profileImageURL).into(imgReview)
                txtReviewTitle.text = firstReview.title
                txtReviewWriter.text = firstReview.user.nickname
                firstReview.product = data

                imgReviewDetail.setOnClickListener {
                    val myIntent = Intent(mContext, ReviewActivity::class.java)
                    myIntent.putExtra("review", firstReview)
                    mContext.startActivity(myIntent)
                }

                if (isReviewOpen == false) {
                    layoutReviewDetail.setOnClickListener {
                        layoutReviewItem.visibility = View.VISIBLE
                        txtReviewDetail.text = "리뷰 닫기"
                        isReviewOpen = true
                        notifyDataSetChanged()
                    }
                } else {
                    layoutReviewDetail.setOnClickListener {
                        layoutReviewItem.visibility = View.GONE
                        txtReviewDetail.text = "리뷰 더보기"
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
            .inflate(R.layout.category_list_item_for_explore_product, parent, false)
        return ProductViewHolder(row)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}