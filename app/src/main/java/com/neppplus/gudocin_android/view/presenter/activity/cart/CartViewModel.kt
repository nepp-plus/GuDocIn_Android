package com.neppplus.gudocin_android.view.presenter.activity.cart

import androidx.lifecycle.MutableLiveData
import com.neppplus.gudocin_android.BaseViewModel
import com.neppplus.gudocin_android.model.BasicResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: CartRepository) : BaseViewModel() {
  var liveDataList: MutableLiveData<BasicResponse> = MutableLiveData()

  fun getLiveDataObserver(): MutableLiveData<BasicResponse> {
    return liveDataList
  }

  fun loadDataList() {
    repository.apiCall(liveDataList)
  }
}