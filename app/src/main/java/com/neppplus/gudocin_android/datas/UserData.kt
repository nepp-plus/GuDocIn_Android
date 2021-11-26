package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class UserData(

    var id: Int,
    var email: String,
    var provider : String,
    var uid : String?,

    @SerializedName("nick_name")
    var nickname: String,

    @SerializedName("profile_img")
    var profileImageURL: String



) :Serializable {
}