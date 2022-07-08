package com.neppplus.gudocin_android.model.category

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CategoryData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("name")
  val name: String,

  @SerializedName("smallCategories")
  val smallCategory: SmallCategoryData,
) : Serializable