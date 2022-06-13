package com.neppplus.gudocin_android.model.subscription

import com.google.gson.annotations.SerializedName
import com.neppplus.gudocin_android.model.product.ProductData
import java.io.Serializable
import java.util.*

data class SubscriptionData(
  var id: Int,
  @SerializedName("user_id")
  var userId: Int,
  @SerializedName("product_id")
  var productId: Int,
  @SerializedName("created_at")
  var createdAt: Date,
  @SerializedName("stopped_at")
  var stoppedAt: Date?,
  var product: ProductData,
) : Serializable {
}