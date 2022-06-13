package com.neppplus.gudocin_android.model.image

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImageData(
  var id: Int,
  @SerializedName("img_url")
  var imgUrl: String,
  var index: Int,
  @SerializedName("review_id")
  var reviewId: Int,
) : Serializable {
}