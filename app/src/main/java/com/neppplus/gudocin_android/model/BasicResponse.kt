package com.neppplus.gudocin_android.model

data class BasicResponse(
  var code: Int,
  var message: String,
  var data: DataResponse,
) {
}