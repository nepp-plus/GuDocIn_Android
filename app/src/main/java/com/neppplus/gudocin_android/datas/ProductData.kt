package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.NumberFormat
import java.util.*

class ProductData(

    var id: Int,
    var name: String,
    var price: Int,
    @SerializedName("image_url")
    var imageUrl: String,
    var store : StoreData,
    var reviews : List<ReviewData>,

) : Serializable {

    fun getFormatedPrice(): String {
        return "${NumberFormat.getInstance(Locale.KOREA).format(this.price)} 원"
    }
}