package com.neppplus.gudocin_android.model.category

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SmallCategoryData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("name")
  val name: String,

  @SerializedName("large_category_id")
  val largeCategoryId: Int,
) : Serializable {
}