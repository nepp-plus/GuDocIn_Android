package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.text.NumberFormat
import java.util.*

class BasketData(

    var id: Int,
    var product: ProductData,

    ) {

//    ProductData 함수 활용

//    fun getFormattedPrice(): String {
//        return "${NumberFormat.getInstance(Locale.KOREA).format(this.product.price)} 원"
//    }

}