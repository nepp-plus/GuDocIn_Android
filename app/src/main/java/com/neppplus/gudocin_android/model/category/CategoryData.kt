package com.neppplus.gudocin_android.model.category

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryData(
  var id: Int,
  var name: String,
  @SerializedName("small_categories")
  var smallCategory: SmallCategoryData,
) : Serializable {
}
