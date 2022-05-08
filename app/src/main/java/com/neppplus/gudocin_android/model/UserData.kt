package com.neppplus.gudocin_android.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class UserData(
    var id: Int,
    var email: String,
    var provider: String,
    var uid: String?,
    @SerializedName("nick_name")
    var nickname: String,
    @SerializedName("profile_img")
    var profileImageURL: String,
    @SerializedName("receive_email")
    var receiveEmail: String,
    var phone: String,
    var point: Int,
) : Serializable {
}