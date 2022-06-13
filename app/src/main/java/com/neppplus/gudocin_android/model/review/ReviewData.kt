package com.neppplus.gudocin_android.model.review

import com.google.gson.annotations.SerializedName
import com.neppplus.gudocin_android.model.tag.TagData
import com.neppplus.gudocin_android.model.user.UserData
import com.neppplus.gudocin_android.model.image.ImageData
import com.neppplus.gudocin_android.model.product.ProductData
import java.io.Serializable

data class ReviewData(
  var id: Int,
  var title: String,
  var content: String,
  var score: Double,
  @SerializedName("review_count")
  var reviewCount: Int,
  @SerializedName("user_id")
  var userId: Int,
  @SerializedName("product_id")
  var productId: Int,
  @SerializedName("thumbnail_img")
  var thumbNailImg: String,
  @SerializedName("created_at")
  var createdAt: String,
  @SerializedName("tag_list")
  var tagList: String,
  var product: ProductData,
  var user: UserData,
  var images: List<ImageData>,
  var tags: List<TagData>,
) : Serializable {
}