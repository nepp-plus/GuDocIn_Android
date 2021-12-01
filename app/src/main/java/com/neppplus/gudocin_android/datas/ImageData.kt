package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImageData(
    var id: Int,
    @SerializedName("img_url")
    var imgUrl: String,
    var index : Int,
    @SerializedName("review_id")
    var reviewId : Int,

): Serializable {
}