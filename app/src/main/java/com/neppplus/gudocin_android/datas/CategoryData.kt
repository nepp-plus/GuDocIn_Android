package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CategoryData(
    var id: Int,
    var name: String,
    @SerializedName("small_categories")
    var smallCategory: SmallCategoryData,
) : Serializable {
}
