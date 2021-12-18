package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class CardData(
    @SerializedName("card_num")
    var cardNum: String,

    @SerializedName("card_nickname")
    var cardNickname: String,

    @SerializedName("mm_yy")
    var cardValid: String,

    var cardBirthday: String,

//    @SerializedName("password_2digit")
//    var cardPwDigit: String


) :Serializable{


}