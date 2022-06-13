package com.neppplus.gudocin_android.model.banner

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BannerData(
  var id: Int,
  @SerializedName("img_url")
  var displayImageUrl: String,
  @SerializedName("click_url")
  var clickUrl: String
) : Serializable {
}