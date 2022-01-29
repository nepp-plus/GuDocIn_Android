package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class PointLogData(
    var id: Int,
    @SerializedName("user_id")
    var userId: Int,
    @SerializedName("payment_id")
    var paymentId: Int,
    @SerializedName("review_id")
    var reviewId: Int,
    var amount: Int,
    var type: String,
    @SerializedName("created_at")
    var createdAt: Date,
    var payment: PaymentData,
) : Serializable {
}