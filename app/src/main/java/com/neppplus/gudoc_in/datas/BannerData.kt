package com.neppplus.gudoc_in.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class BannerData(
    var id: Int,
    @SerializedName("img_url")
    var displayImageUrl: String,
    @SerializedName("click_url")
    var clickUrl: String
) : Serializable {
}