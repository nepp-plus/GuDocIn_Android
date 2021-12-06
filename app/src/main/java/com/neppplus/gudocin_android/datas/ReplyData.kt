package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable


class ReplyData(
    var id: Int,
    @SerializedName("user_id")
    var userId: Int,
    @SerializedName("review_id")
    var reviewId: Int,
    var content: String,
    @SerializedName("created_at")
    var createdAt: String,
    var user: UserData
): Serializable {
}