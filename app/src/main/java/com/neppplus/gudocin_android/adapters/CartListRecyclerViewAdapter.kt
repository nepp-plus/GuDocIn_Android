package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.api.ServerAPI
import com.neppplus.gudocin_android.api.ServerAPIInterface
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.CartData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartListRecyclerViewAdapter(
    val mContext: Context,
    val mList: List<CartData>
) : RecyclerView.Adapter<CartListRecyclerViewAdapter.CartViewHolder>() {

    inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgCartProductPhoto = view.findViewById<ImageView>(R.id.imgCartProductPhoto)
        val txtCartProductName = view.findViewById<TextView>(R.id.txtCartProductName)
        val txtCartProductPrice = view.findViewById<TextView>(R.id.txtCartProductPrice)

        lateinit var apiService: ServerAPIInterface
        val retrofit = ServerAPI.getRetrofit(mContext)

        val imgDeleteSubscribe = view.findViewById<ImageView>(R.id.imgDeleteSubscribe)
//      val btnSubscribe = view.findViewById<Button>(R.id.btnSubscribe)

        fun bind(data: CartData) {
            txtCartProductName.text = data.product.name
            txtCartProductPrice.text = data.product.getFormattedPrice()
            Glide.with(mContext).load(data.product.imageUrl).into(imgCartProductPhoto)

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