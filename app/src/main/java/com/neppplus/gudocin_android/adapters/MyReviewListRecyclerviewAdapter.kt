package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ReviewDetailActivity
import com.neppplus.gudocin_android.datas.ReviewData

class MyReviewListRecyclerviewAdapter(
    val mContext: Context,
    val mList: ArrayList<ReviewData>
) : RecyclerView.Adapter<MyReviewListRecyclerviewAdapter.MyReviewViewHolder>() {

    inner class MyReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtReviewTitle = view.findViewById<TextView>(R.id.txtReviewTitle)
        val txtProductName = view.findViewById<TextView>(R.id.txtProductName)
        val txtReviewDate = view.findViewById<TextView>(R.id.txtReviewDate)
//        val txtSavedMoneyPaymentCheck = view.findViewById<TextView>(R.id.txtSavedMoneyPaymentCheck)

        val btnReviewCheck = view.findViewById<Button>(R.id.btnReviewCheck)
//        val btnEditReview = view.findViewById<Button>(R.id.btnEditReview)

        fun bind(data: ReviewData) {
            txtReviewTitle.text = data.title
            txtProductName.text = data.product.name
            txtReviewDate.text = data.createdAt

            btnReviewCheck.setOnClickListener {
                val myIntent = Intent(mContext, ReviewDetailActivity::class.java)
                myIntent.putExtra("review", data) // 넘어갈 때 review id 들려 보내야 함
                mContext.startActivity(myIntent)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyReviewViewHolder {
        val row = LayoutInflater.from(mContext)
            .inflate(R.layout.my_review_recyclerview_list_item, parent, false)
        return MyReviewViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyReviewViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}