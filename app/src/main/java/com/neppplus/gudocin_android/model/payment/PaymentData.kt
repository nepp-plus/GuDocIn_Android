package com.neppplus.gudocin_android.model.payment

import com.google.gson.annotations.SerializedName
import com.neppplus.gudocin_android.model.subscription.SubscriptionData
import java.io.Serializable
import java.util.*

data class PaymentData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("amount")
  val amount: Int,

  @SerializedName("created_at")
  val createdAt: Date,

  @SerializedName("subscription")
  val subscription: SubscriptionData,
) : Serializable