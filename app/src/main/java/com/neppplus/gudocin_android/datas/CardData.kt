package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName

class CardData(

    @SerializedName("card_num")
    var cardNum: String,

    @SerializedName("card_nickname")
    var cardNickname: String,

    @SerializedName("mm_yy")
    var valid: String,

    var birthday: String,

    @SerializedName("password_2digit")
    var cardDigit: String

) {
}