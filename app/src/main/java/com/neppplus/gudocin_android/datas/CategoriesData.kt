package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.text.NumberFormat
import java.util.*

class CategoriesData (

    var id: Int,
    var name: String,
   @SerializedName("large_category_id")
    var largeCategory : Int,

//스몰 카테고리 추가 필요함

) : Serializable {}
