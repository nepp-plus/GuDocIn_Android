package com.neppplus.gudocin_android.adapters

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
import com.facebook.CallbackManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.api.ServerAPI
import com.neppplus.gudocin_android.api.ServerAPIInterface
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.BasketData
import com.neppplus.gudocin_android.datas.ProductData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasketRecyclerAdapter(val mContext: Context, val mList: List<BasketData>) :
    RecyclerView.Adapter<BasketRecyclerAdapter.BasketViewHolder>() {

    inner class BasketViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imgBasketPhoto = view.findViewById<ImageView>(R.id.imgBasketPhoto)
        val txtBasketProductName = view.findViewById<TextView>(R.id.txtBasketProductName)
        val txtBasketProductPrice = view.findViewById<TextView>(R.id.txtBasketProductPrice)

        lateinit var apiService: ServerAPIInterface

        val retrofit = ServerAPI.getRetrofit(mContext)

        val imgDeleteSubscribe = view.findViewById<ImageView>(R.id.imgDeleteSubscribe)

        fun bind(data: BasketData) {

            txtBasketProductName.text = data.product.name

            txtBasketProductPrice.text = data.product.getFormattedPrice()

            Glide.with(mContext).load(data.product.imageUrl).into(imgBasketPhoto)

            apiService = retrofit.create(ServerAPIInterface::class.java)

            imgDeleteSubscribe.setOnClickListener {

                apiService.deleteRequestProduct(data.product.id).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                        if (response.isSuccessful) {

                            Toast.makeText(
                                mContext,
                                "장바구니 목록이 삭제되었습니다",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                            notifyDataSetChanged()

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {

        val row = LayoutInflater.from(mContext).inflate(R.layout.basket_list_item, parent, false)
        return BasketViewHolder(row)

    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount() = mList.size

}