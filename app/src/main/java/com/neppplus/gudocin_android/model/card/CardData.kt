package com.neppplus.gudocin_android.model.card

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CardData(
  @SerializedName("card_num")
  var cardNum: String,
  @SerializedName("card_nickname")
  var cardNickname: String,
  @SerializedName("mm_yy")
  var cardValid: String,
  @SerializedName("birthday")
  var cardBirth: String,
  @SerializedName("password_2digit")
  var cardPW: String
) : Serializable {
}