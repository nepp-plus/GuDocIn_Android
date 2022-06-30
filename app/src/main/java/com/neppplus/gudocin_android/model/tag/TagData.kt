package com.neppplus.gudocin_android.model.tag

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class TagData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("tag")
  val tag: String,

  @SerializedName("review_id")
  val reviewId: Int,
) : Serializable