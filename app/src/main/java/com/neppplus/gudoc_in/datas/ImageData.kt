package com.neppplus.gudoc_in.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ImageData(
    var id: Int,
    @SerializedName("img_url")
    var imgUrl: String,
    var index: Int,
    @SerializedName("review_id")
    var reviewId: Int,
) : Serializable {
}