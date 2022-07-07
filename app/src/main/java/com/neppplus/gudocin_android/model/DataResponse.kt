package com.neppplus.gudocin_android.model

import com.google.gson.annotations.SerializedName
import com.neppplus.gudocin_android.model.banner.BannerData
import com.neppplus.gudocin_android.model.card.CardData
import com.neppplus.gudocin_android.model.cart.CartData
import com.neppplus.gudocin_android.model.category.CategoryData
import com.neppplus.gudocin_android.model.category.SmallCategoryData
import com.neppplus.gudocin_android.model.payment.PaymentData
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.model.reply.ReplyData
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.model.user.UserData

data class DataResponse(
  @SerializedName("user")
  val user: UserData,

  @SerializedName("token")
  val token: String,

  @SerializedName("reviews")
  val reviews: List<ReviewData>,

  @SerializedName("products")
  val products: List<ProductData>,

  @SerializedName("product")
  val product: ProductData,

  @SerializedName("carts")
  val carts: List<CartData>,

  @SerializedName("categories")
  val categories: List<CategoryData>,

  @SerializedName("small_categories")
  val smallCategories: List<SmallCategoryData>,

  @SerializedName("banners")
  val banners: List<BannerData>,

  @SerializedName("payments")
  val payments: List<PaymentData>,

  @SerializedName("replies")
  val replies: List<ReplyData>,

  @SerializedName("cards")
  val cards: List<CardData>
)