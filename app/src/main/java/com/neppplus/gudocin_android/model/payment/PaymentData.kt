package com.neppplus.gudocin_android.model.payment

import com.google.gson.annotations.SerializedName
import com.neppplus.gudocin_android.model.subscription.SubscriptionData
import java.io.Serializable
import java.util.*

data class PaymentData(
  var id: Int,
  var amount: Int,
  @SerializedName("created_at")
  var createdAt: Date,
  var subscription: SubscriptionData,
) : Serializable {
}