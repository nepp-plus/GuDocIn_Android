package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SmallCategoryData(
    var id: Int,
    var name: String,
    @SerializedName("large_category_id")
    var largeCategoryId: Int,
) : Serializable {
}