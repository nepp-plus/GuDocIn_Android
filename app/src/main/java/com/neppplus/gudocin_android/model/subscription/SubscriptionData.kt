package com.neppplus.gudocin_android.model.subscription

import com.google.gson.annotations.SerializedName
import com.neppplus.gudocin_android.model.product.ProductData
import java.io.Serializable
import java.util.*

data class SubscriptionData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("user_id")
  val userId: Int,

  @SerializedName("product_id")
  val productId: Int,

  @SerializedName("created_at")
  val createdAt: Date,

  @SerializedName("stopped_at")
  val stoppedAt: Date?,

  @SerializedName("product")
  val product: ProductData,
) : Serializable