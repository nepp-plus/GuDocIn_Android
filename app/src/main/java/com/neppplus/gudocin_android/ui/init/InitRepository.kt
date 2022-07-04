package com.neppplus.gudocin_android.ui.init

import androidx.lifecycle.MutableLiveData
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.user.GlobalData
import com.neppplus.gudocin_android.network.RetrofitServiceInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class InitRepository @Inject constructor(private val retrofitServiceInstance: RetrofitServiceInstance) {
  fun getRequestInfo(liveDataList: MutableLiveData<BasicResponse>) {
    val call: Call<BasicResponse> = retrofitServiceInstance.getRequestInfo()
    call.enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          val basicResponse = response.body()!!
          GlobalData.loginUser = basicResponse.data.user
          liveDataList.postValue(response.body())
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
        liveDataList.postValue(null)
      }
    })
  }
}