package com.neppplus.gudocin_android.model.banner

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BannerData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("img_url")
  val displayImageUrl: String,

  @SerializedName("click_url")
  val clickUrl: String
) : Serializable