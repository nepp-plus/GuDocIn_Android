package com.neppplus.gudocin_android.view.adapter.cart

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
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.cart.CartData
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.network.RetrofitServiceInstance
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRecyclerViewAdapter(
  val mContext: Context,
  private val mList: List<CartData>
) : RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>() {

  inner class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    lateinit var apiService: RetrofitServiceInstance
    private val retrofit = RetrofitService.getRetrofit(mContext)

    private val imgProduct: ImageView = view.findViewById(R.id.imgProduct)
    private val txtProduct: TextView = view.findViewById(R.id.txtProduct)
    private val txtPrice: TextView = view.findViewById(R.id.txtPrice)
    private val imgDelete: ImageView = view.findViewById(R.id.imgDelete)

    fun bind(data: CartData) {
      apiService = retrofit.create(RetrofitServiceInstance::class.java)

      txtProduct.text = data.product.name
      txtPrice.text = data.product.getFormattedPrice()
      Glide.with(mContext).load(data.product.imageUrl).into(imgProduct)

      imgDelete.setOnClickListener {
        apiService.deleteRequestCart(data.product.id).enqueue(object : Callback<BasicResponse> {
          override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
            if (response.isSuccessful) {
              notifyDataSetChanged()
              Toast.makeText(mContext, mContext.getString(R.string.cart_list_delete), Toast.LENGTH_SHORT).show()
            } else {
              val errorJson = JSONObject(response.errorBody()!!.string())
              Log.d(mContext.getString(R.string.error_case), errorJson.toString())

              val message = errorJson.getString("message")
              Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
            }
          }

          override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
        })
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
    val row = LayoutInflater.from(mContext).inflate(R.layout.adapter_cart, parent, false)
    return CartViewHolder(row)
  }

  override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
    holder.bind(mList[position])
  }

  override fun getItemCount() = mList.size

}