package com.neppplus.gudoc_in.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class ReplyData(
    var id: Int,
    @SerializedName("user_id")
    var userId: Int,
    @SerializedName("review_id")
    var reviewId: Int,
    var content: String,
    @SerializedName("created_at")
    var createdAt: Date,
    val user: UserData
) : Serializable {
}