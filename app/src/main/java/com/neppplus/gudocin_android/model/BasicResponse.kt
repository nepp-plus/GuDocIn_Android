package com.neppplus.gudocin_android.model

data class BasicResponse(
  val code: Int,
  val message: String,
  val data: DataResponse,
)