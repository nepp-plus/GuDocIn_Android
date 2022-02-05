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
import com.neppplus.gudocin_android.datas.PaymentData
import java.text.SimpleDateFormat

class PurchaseListRecyclerviewAdapter(
    val mContext: Context,
    val mList: ArrayList<PaymentData>
) : RecyclerView.Adapter<PurchaseListRecyclerviewAdapter.MyPurchaseListViewHolder>() {

    inner class MyPurchaseListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtReviewingProductName = view.findViewById<TextView>(R.id.txtReviewingProductName)
        val txtProductPrice = view.findViewById<TextView>(R.id.txtProductPrice)
        val txtPurchaseDate = view.findViewById<TextView>(R.id.txtPurchaseDate)

        val txtDeliveryInfo = view.findViewById<TextView>(R.id.txtDeliveryInfo)
        val txtPaymentInfo = view.findViewById<TextView>(R.id.txtPaymentInfo)

        val btnWriteReview = view.findViewById<Button>(R.id.btnWriteReview)

        fun bind(data: PaymentData) {
            txtReviewingProductName.text = data.subscription.product.name
            txtProductPrice.text = data.subscription.product.price.toString()
            txtPurchaseDate.text = data.subscription.createdAt.toString()

            val sdf = SimpleDateFormat("yyyy-M-d H:mm:ss")
            txtPurchaseDate.text = sdf.format(data.createdAt)

            btnWriteReview.setOnClickListener {
                val myIntent = Intent(mContext, ReviewDetailActivity::class.java)
                myIntent.putExtra("review", data) // 넘어갈 때 review id 들려 보내야 함
                mContext.startActivity(myIntent)
            }

            txtDeliveryInfo.setOnClickListener {

            }

            txtPaymentInfo.setOnClickListener {

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPurchaseListViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.purchase_list_item, parent, false)
        return MyPurchaseListViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyPurchaseListViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}