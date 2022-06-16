package com.neppplus.gudocin_android.viewmodel.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.neppplus.gudocin_android.BaseViewModel
import com.neppplus.gudocin_android.SingleLiveEvent
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.cart.CartData
import com.neppplus.gudocin_android.repository.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val repository: CartRepository) : BaseViewModel() {
  private val _liveDataList = SingleLiveEvent<BasicResponse>()
  val liveDataList: LiveData<BasicResponse>
    get() = _liveDataList

  private val mCartData: CartData? = null

  fun getCart() = viewModelScope.launch {
    repository.getRequestCart(_liveDataList)
  }

  fun deleteCart() = viewModelScope.launch {
    mCartData?.let { repository.deleteRequestCart(_liveDataList, it) }
  }
}