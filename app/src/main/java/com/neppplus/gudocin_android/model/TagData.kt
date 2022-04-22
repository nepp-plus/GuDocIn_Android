package com.neppplus.gudocin_android.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class TagData(
    var id: Int,
    var tag: String,
    @SerializedName("review_id")
    var reviewId: Int,
) : Serializable {
}