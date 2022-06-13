package com.neppplus.gudocin_android.view.presenter.activity.cart

import androidx.lifecycle.MutableLiveData
import com.neppplus.gudocin_android.model.cart.CartData
import com.neppplus.gudocin_android.network.RetrofitServiceInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartRepository constructor(private val retrofitServiceInstance: RetrofitServiceInstance) {
  fun apiCall(liveDataList: MutableLiveData<List<CartData>>) {
    val call: Call<List<CartData>> = retrofitServiceInstance.getRequestCart()
    call.enqueue(object : Callback<List<CartData>> {
      override fun onResponse(call: Call<List<CartData>>, response: Response<List<CartData>>) {
        liveDataList.postValue(response.body())
      }

      override fun onFailure(call: Call<List<CartData>>, t: Throwable) {
        liveDataList.postValue(null)
      }
    })
  }
}