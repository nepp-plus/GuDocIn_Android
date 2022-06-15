package com.neppplus.gudocin_android.model.reply

import com.google.gson.annotations.SerializedName
import com.neppplus.gudocin_android.model.user.UserData
import java.io.Serializable
import java.util.*

data class ReplyData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("user_id")
  val userId: Int,

  @SerializedName("review_id")
  val reviewId: Int,

  @SerializedName("content")
  val content: String,

  @SerializedName("created_at")
  val createdAt: Date,

  @SerializedName("user")
  val user: UserData
) : Serializable