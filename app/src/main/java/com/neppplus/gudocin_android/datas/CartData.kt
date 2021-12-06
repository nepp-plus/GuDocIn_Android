package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CartData(

    var id: Int,
    @SerializedName("user_id")
    var userId: String,
    @SerializedName("product_id")
    var productId: String,
    var product : ProductData

) : Serializable {
}