package com.neppplus.gudocin_android.ui.init

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.neppplus.gudocin_android.ui.base.BaseViewModel
import com.neppplus.gudocin_android.util.SingleLiveData
import com.neppplus.gudocin_android.model.BasicResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitViewModel @Inject constructor(private val repository: InitRepository) : BaseViewModel() {
  private val _liveDataList = SingleLiveData<BasicResponse>()
  val liveDataList: LiveData<BasicResponse>
    get() = _liveDataList

  fun getRequestInfo() = viewModelScope.launch {
    repository.getRequestInfo(_liveDataList)
  }
}