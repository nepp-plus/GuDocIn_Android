package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.util.*

class BasketData(

    var id: Int,
    var name: String,
    var price: Int,
    @SerializedName("image_url")
    var imageURL: String,

    ) {

    fun getFormattedPrice(): String {

        return "${NumberFormat.getInstance(Locale.KOREA).format(this.price)} 원"

    }

}