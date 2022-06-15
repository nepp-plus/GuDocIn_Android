package com.neppplus.gudocin_android.viewmodel.init

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.neppplus.gudocin_android.BaseViewModel
import com.neppplus.gudocin_android.SingleLiveEvent
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.repository.init.InitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(private val repository: InitRepository) : BaseViewModel() {
  private val _liveDataList = SingleLiveEvent<BasicResponse>()
  val liveDataList: LiveData<BasicResponse>
    get() = _liveDataList

  fun getRequestInfo() = viewModelScope.launch {
    repository.getRequestInfo(_liveDataList)
  }

  fun patchRequestUpdateUserInfo() = viewModelScope.launch {
    repository.patchRequestUpdateUserInfo(_liveDataList)
  }
}