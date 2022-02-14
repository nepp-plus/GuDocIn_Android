package com.neppplus.gudoc_in.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TagData(
    var id: Int,
    var tag: String,
    @SerializedName("review_id")
    var reviewId: Int,
) : Serializable {
}