package com.neppplus.gudocin_android.model.product

import com.google.gson.annotations.SerializedName
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.model.store.StoreData
import java.io.Serializable
import java.text.NumberFormat
import java.util.*

data class ProductData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("name")
  val name: String,

  @SerializedName("price")
  val price: Int,

  @SerializedName("image_url")
  val imageUrl: String,

  @SerializedName("store")
  val store: StoreData,

  @SerializedName("reviews")
  val reviews: List<ReviewData>,
) : Serializable {
  fun getFormattedPrice(): String {
    return "${NumberFormat.getInstance(Locale.KOREA).format(this.price)}원"
  }
}