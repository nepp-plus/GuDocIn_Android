package com.neppplus.gudocin_android.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class SubscriptionData(
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