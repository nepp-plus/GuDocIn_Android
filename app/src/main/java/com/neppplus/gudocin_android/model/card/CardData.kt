package com.neppplus.gudocin_android.model.card

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CardData(
  @SerializedName("card_num")
  val cardNum: String,

  @SerializedName("card_nickname")
  val cardNickname: String,

  @SerializedName("mm_yy")
  val cardValid: String,

  @SerializedName("birthday")
  val cardBirth: String,

  @SerializedName("password_2digit")
  val cardPW: String
) : Serializable