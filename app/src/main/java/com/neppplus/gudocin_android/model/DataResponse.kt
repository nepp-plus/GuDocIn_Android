package com.neppplus.gudocin_android.model

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
  val user: UserData,
  val token: String,
  val reviews: List<ReviewData>,
  val products: List<ProductData>,
  val product: ProductData,
  val carts: List<CartData>,
  val categories: List<CategoryData>,
  val small_categories: List<SmallCategoryData>,
  val banners: List<BannerData>,
  val payments: List<PaymentData>,
  val replies: List<ReplyData>,
  val cards: List<CardData>
) {
}