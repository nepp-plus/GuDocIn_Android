package com.neppplus.gudocin_android.model.cart

import com.google.gson.annotations.SerializedName
import com.neppplus.gudocin_android.model.product.ProductData

data class CartData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("product")
  val product: ProductData,
)