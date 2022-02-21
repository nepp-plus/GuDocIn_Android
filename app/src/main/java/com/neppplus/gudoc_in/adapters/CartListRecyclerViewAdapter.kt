package com.neppplus.gudoc_in.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudoc_in.R
import com.neppplus.gudoc_in.api.ServerAPI
import com.neppplus.gudoc_in.api.ServerAPIInterface
import com.neppplus.gudoc_in.datas.BasicResponse
import com.neppplus.gudoc_in.datas.CartData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartListRecyclerViewAdapter(
    val mContext: Context,
    val mList: List<CartData>
) : RecyclerView.Adapter<CartListRecyclerViewAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgProduct = view.findViewById<ImageView>(R.id.imgProduct)
        val txtProductName = view.findViewById<TextView>(R.id.txtProductName)
        val txtPrice = view.findViewById<TextView>(R.id.txtPrice)

        lateinit var apiService: ServerAPIInterface
        val retrofit = ServerAPI.getRetrofit(mContext)

        val imgDeleteSubscribe = view.findViewById<ImageView>(R.id.imgDelete)
//      val btnSubscribe = view.findViewById<Button>(R.id.btnSubscribe)

        fun bind(data: CartData) {
            txtProductName.text = data.product.name
            txtPrice.text = data.product.getFormattedPrice()
            Glide.with(mContext).load(data.product.imageUrl).into(imgProduct)

            apiService = retrofit.create(ServerAPIInterface::class.java)

            imgDeleteSubscribe.setOnClickListener {
                apiService.deleteRequestProduct(data.product.id)
                    .enqueue(object : Callback<BasicResponse> {
                        override fun onResponse(
                            call: Call<BasicResponse>,
                            response: Response<BasicResponse>
                        ) {
                            if (response.isSuccessful) {
                                notifyDataSetChanged()
                                Toast.makeText(
                                    mContext,
                                    "장바구니 목록이 삭제되었습니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                val errorJson = JSONObject(response.errorBody()!!.string())
                                Log.d("에러경우", errorJson.toString())

                                val message = errorJson.getString("message")
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                        }
                    })
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.cart_list_item, parent, false)
        return CartViewHolder(row)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(mList[position])
        /* holder.btnSubscribe.setOnClickListener {
            val myIntent = Intent(mContext, PaymentActivity::class.java)
            startActivity(holder.btnSubscribe.context, myIntent, null)
        } */
    }

    override fun getItemCount() = mList.size

}