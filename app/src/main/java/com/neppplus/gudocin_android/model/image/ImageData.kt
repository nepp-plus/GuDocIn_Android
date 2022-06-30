package com.neppplus.gudocin_android.model.image

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ImageData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("img_url")
  val imgUrl: String,

  @SerializedName("index")
  val index: Int,

  @SerializedName("review_id")
  val reviewId: Int,
) : Serializable {
}