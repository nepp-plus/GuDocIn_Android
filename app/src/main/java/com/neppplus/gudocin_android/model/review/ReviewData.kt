package com.neppplus.gudocin_android.model.review

import com.google.gson.annotations.SerializedName
import com.neppplus.gudocin_android.model.tag.TagData
import com.neppplus.gudocin_android.model.user.UserData
import com.neppplus.gudocin_android.model.image.ImageData
import com.neppplus.gudocin_android.model.product.ProductData
import java.io.Serializable

data class ReviewData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("title")
  val title: String,

  @SerializedName("content")
  val content: String,

  @SerializedName("score")
  val score: Double,

  @SerializedName("review_count")
  val reviewCount: Int,

  @SerializedName("user_id")
  val userId: Int,

  @SerializedName("product_id")
  val productId: Int,

  @SerializedName("thumbnail_img")
  val thumbNailImg: String,

  @SerializedName("created_at")
  val createdAt: String,

  @SerializedName("tag_list")
  val tagList: String,

  @SerializedName("product")
  var product: ProductData,

  @SerializedName("user")
  val user: UserData,

  @SerializedName("images")
  val images: List<ImageData>,

  @SerializedName("tags")
  val tags: List<TagData>,
) : Serializable