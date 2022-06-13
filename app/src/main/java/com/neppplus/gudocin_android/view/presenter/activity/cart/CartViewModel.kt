package com.neppplus.gudocin_android.view.presenter.activity.cart

import androidx.lifecycle.MutableLiveData
import com.neppplus.gudocin_android.BaseViewModel
import com.neppplus.gudocin_android.model.cart.CartData

class CartViewModel constructor(private val repository: CartRepository) : BaseViewModel() {
  var liveDataList: MutableLiveData<List<CartData>> = MutableLiveData()

  fun getLiveDataObserver(): MutableLiveData<List<CartData>> {
    return liveDataList
  }

  fun loadDataList() {
    repository.apiCall(liveDataList)
  }
}