package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ReviewData(

    var id : Int,
    var title : String,
    var content : String,
    var score : Double,

    @SerializedName("review_count")
    var reviewCount : Int,
    @SerializedName("user_id")
    var userId : Int,
    @SerializedName("product_id")
    var productId : Int,
    @SerializedName("thumbnail_img")
    var thumbNailImg : String,
    @SerializedName("created_at")
    var createdAt : String,
    @SerializedName("tag_list")
    var tagList : String,

    var product : ProductData,
    var user: UserData,
    var images: List<ImageData>,
    var tags: List<TagData>,

    ) : Serializable{
}