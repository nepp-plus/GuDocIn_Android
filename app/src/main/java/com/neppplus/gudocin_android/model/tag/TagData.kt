package com.neppplus.gudocin_android.model.tag

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TagData(
  var id: Int,
  var tag: String,
  @SerializedName("review_id")
  var reviewId: Int,
) : Serializable {
}