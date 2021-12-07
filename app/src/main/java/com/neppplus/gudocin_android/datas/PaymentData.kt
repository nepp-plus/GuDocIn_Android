package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class PaymentData (


        var id : Int,
        @SerializedName("subscription_id")
        var subscriptionId : Int,
        var amount: Int,
        @SerializedName("created_at")
        var createdAt: Date,
        var subscription : SubscriptionData

        ) : Serializable {
}