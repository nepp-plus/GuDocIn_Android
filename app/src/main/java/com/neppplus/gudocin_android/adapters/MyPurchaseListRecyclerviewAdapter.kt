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

class MyPurchaseListRecyclerviewAdapter(
    val mContext: Context,
    val mList: ArrayList<PaymentData>
) : RecyclerView.Adapter<MyPurchaseListRecyclerviewAdapter.MyPurchaseListViewHolder>() {

    inner class MyPurchaseListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtProductReviewNoName = view.findViewById<TextView>(R.id.txtProductReviewNoName)
        val txtPurchaseDate = view.findViewById<TextView>(R.id.txtPurchaseDate)
        val txtPaymentInfo = view.findViewById<TextView>(R.id.txtPaymentInfo)
        val txtDeliveryInfo = view.findViewById<TextView>(R.id.txtDeliveryInfo)
        val txtProductPrice = view.findViewById<TextView>(R.id.txtProductPrice)
        val btnReviewIndex = view.findViewById<Button>(R.id.btnReviewIndex)
        val btnCancel = view.findViewById<Button>(R.id.btnCancel)

        fun bind(data: PaymentData) {
            txtProductReviewNoName.text = data.subscription.product.name
            txtProductPrice.text = data.subscription.product.price.toString()
//            txtPurchaseDate.text = data.subscription.createdAt.toString()

            val sdf = SimpleDateFormat("yyyy-M-d H:mm:ss")
            txtPurchaseDate.text = sdf.format(data.createdAt)

            btnReviewIndex.setOnClickListener {
                val myIntent = Intent(mContext, ReviewDetailActivity::class.java)
                myIntent.putExtra("review", data) // 넘어갈 때 review id 들려 보내야 함
                mContext.startActivity(myIntent)
            }

            txtPaymentInfo.setOnClickListener {

            }

            txtDeliveryInfo.setOnClickListener {

            }

            btnReviewIndex.setOnClickListener {

            }

            btnCancel.setOnClickListener {

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