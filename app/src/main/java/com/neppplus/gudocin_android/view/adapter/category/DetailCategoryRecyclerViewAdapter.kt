package com.neppplus.gudocin_android.view.adapter.category

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
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.view.presenter.activity.product.ProductActivity
import com.neppplus.gudocin_android.view.presenter.activity.review.ReviewActivity

class DetailCategoryRecyclerViewAdapter(
  val mContext: Context,
  private val mList: List<ProductData>
) : RecyclerView.Adapter<DetailCategoryRecyclerViewAdapter.ProductViewHolder>() {

  inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val layoutRoot: LinearLayout = view.findViewById(R.id.llRoot)
    private val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
    private val txtProduct: TextView = view.findViewById(R.id.txtProduct)
    private val txtStore: TextView = view.findViewById(R.id.txtStore)
    private val txtPrice: TextView = view.findViewById(R.id.txtPrice)
    private val txtDetailReview: TextView = view.findViewById(R.id.txtDetailReview)
    private val layoutReviewDetail: LinearLayout = view.findViewById(R.id.llDetailReview)
    private val layoutReviewItem: LinearLayout = view.findViewById(R.id.llReviewItem)
    private val imgProfile: ImageView = view.findViewById(R.id.imgProfile)
    private val txtTitle: TextView = view.findViewById(R.id.txtTitle)
    private val txtReviewer: TextView = view.findViewById(R.id.txtReviewer)
    private var isReviewOpen = false

    fun bind(data: ProductData) {
      Glide.with(mContext).load(data.imageUrl).into(imgProduct)
      txtProduct.text = data.name
      txtStore.text = data.store.name
      txtPrice.text = data.getFormattedPrice()

      if (data.reviews.isEmpty()) {
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

        if (!isReviewOpen) {
          layoutReviewDetail.setOnClickListener {
            layoutReviewItem.visibility = View.VISIBLE
            txtDetailReview.text = mContext.getString(R.string.review_close)
            isReviewOpen = true
            notifyDataSetChanged()
          }
        } else {
          layoutReviewDetail.setOnClickListener {
            layoutReviewItem.visibility = View.GONE
            txtDetailReview.text = mContext.getString(R.string.review_view_more)
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
    val row = LayoutInflater.from(mContext).inflate(R.layout.adapter_detail_category, parent, false)
    return ProductViewHolder(row)
  }

  override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
    holder.bind(mList[position])
  }

  override fun getItemCount() = mList.size

}