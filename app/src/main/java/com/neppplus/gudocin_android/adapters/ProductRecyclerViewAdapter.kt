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
import com.bumptech.glide.Glide
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
        val txtReviewTitle = view.findViewById<TextView>(R.id.txtReviewTitle)
        val txtReviewWriterName = view.findViewById<TextView>(R.id.txtReviewWriterName)
        val btnGotoReviewDetail = view.findViewById<ImageView>(R.id.btnGotoReviewDetail)
        val imgReview = view.findViewById<ImageView>(R.id.imgReview)
        val imgProduct = view.findViewById<ImageView>(R.id.imgProduct)
        var isReviewOpen = false


        fun bind(data: ProductData) {
            txtProductName.text = data.name
            txtProductPrice.text = data.getFormatedPrice()
            txtProductCompanyName.text = data.store.name
            Glide.with(mContext).load(data.imageUrl).into(imgProduct)

            if (data.reviews.size==0){
                btnMoreReview.visibility = View.GONE
                reviewItemLayout.visibility = View.GONE
            }
            else{
                btnMoreReview.visibility = View.VISIBLE
                val firstReview = data.reviews[0]
                txtReviewTitle.text= firstReview.title
                txtReviewWriterName.text = firstReview.user.nickname
                Glide.with(mContext).load(firstReview.user.profileImageURL).into(imgReview)

                btnGotoReviewDetail.setOnClickListener {
                    val myIntent = Intent(mContext, ReviewDetailActivity::class.java)
                    myIntent.putExtra("reviewId",firstReview.id)
                    mContext.startActivity(myIntent)
                }

                if (isReviewOpen==false){
                    btnMoreReview.setOnClickListener {
                        reviewItemLayout.visibility = View.VISIBLE
                        txtOpenReview.text= "리뷰 닫기"
                        isReviewOpen =true
                        notifyDataSetChanged()
                    }
                }
                else{
                    btnMoreReview.setOnClickListener {
                        reviewItemLayout.visibility = View.GONE
                        txtOpenReview.text= "리뷰 더보기.."
                        isReviewOpen =false
                        notifyDataSetChanged()
                    }
                }


            }

            rootLayout.setOnClickListener {

                val myIntent = Intent(mContext, ProductItemDetailActivity::class.java)
                mContext.startActivity(myIntent)
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