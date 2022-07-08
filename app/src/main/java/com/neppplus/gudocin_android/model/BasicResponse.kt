package com.neppplus.gudocin_android.model

import com.google.gson.annotations.SerializedName

data class BasicResponse(
  @SerializedName("code")
  val code: Int,

  @SerializedName("message")
  val message: String,

  @SerializedName("data")
  val data: DataResponse,
)