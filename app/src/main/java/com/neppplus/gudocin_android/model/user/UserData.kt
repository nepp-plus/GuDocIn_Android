package com.neppplus.gudocin_android.model.user

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("email")
  val email: String,

  @SerializedName("provider")
  val provider: String,

  @SerializedName("uid")
  val uid: String?,

  @SerializedName("nick_name")
  val nickname: String,

  @SerializedName("profile_img")
  val profileImageURL: String,

  @SerializedName("receive_email")
  val receiveEmail: String,

  @SerializedName("phone")
  val phone: String,

  @SerializedName("point")
  val point: Int,
) : Serializable