package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.ProductItemDetailActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ReviewDetailActivity
import com.neppplus.gudocin_android.datas.ProductData

class ProductRecyclerViewAdapter(val mContext: Context, val mList: List<ProductData>) :
    RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>() {


    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val rootLayout = view.findViewById<LinearLayout>(R.id.rootLayout)
        val txtProductName = view.findViewById<TextView>(R.id.txtProductName)
        val txtProductPrice = view.findViewById<TextView>(R.id.txtProductPrice)
        val txtProductCompanyName = view.findViewById<TextView>(R.id.txtProductCompanyName)
        val reviewItemLayout = view.findViewById<LinearLayout>(R.id.reviewItemLayout)
        val btnMoreReview = view.findViewById<LinearLayout>(R.id.btnMoreReview)
        val txtOpenReview = view.findViewById<TextView>(R.id.txtOpenReview)
        var reviewItemVisible = false
        val txtReviewTitle = view.findViewById<TextView>(R.id.txtReviewTitle)
        val txtReviewWriterName = view.findViewById<TextView>(R.id.txtReviewWriterName)
        val btnGotoReviewDetail = view.findViewById<ImageView>(R.id.btnGotoReviewDetail)


        fun bind(data: ProductData) {
            txtProductName.text = data.name
            txtProductPrice.text = data.getFormatedPrice()
            txtProductCompanyName.text = data.store.name
//            txtReviewTitle.text = data.

            btnGotoReviewDetail.setOnClickListener {
                val myIntent = Intent(mContext, ReviewDetailActivity::class.java)
//                myIntent.putExtra("review",data.review.id) // 넘어갈 때 review id 들려 보내야 함
                mContext.startActivity(myIntent)
            }


            rootLayout.setOnClickListener {

                val myIntent = Intent(mContext, ProductItemDetailActivity::class.java)
                mContext.startActivity(myIntent)
            }

            btnMoreReview.setOnClickListener {
                reviewItemLayout.visibility = View.VISIBLE
                reviewItemVisible = true
            }

            if (reviewItemVisible == true) {

                txtOpenReview.text = "리뷰 닫기"

                btnMoreReview.setOnClickListener {
                    reviewItemLayout.visibility = View.INVISIBLE
                    reviewItemVisible = false
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(row)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size


}