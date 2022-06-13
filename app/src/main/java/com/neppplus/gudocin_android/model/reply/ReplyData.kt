package com.neppplus.gudocin_android.model.reply

import com.google.gson.annotations.SerializedName
import com.neppplus.gudocin_android.model.user.UserData
import java.io.Serializable
import java.util.*

data class ReplyData(
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