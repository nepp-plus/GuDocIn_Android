package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName

class DataResponse(
    var user: UserData,
    var token: String,
    var reviews: List<ReviewData>,
    var products: List<ProductData>,
    var product: ProductData,
    var carts: List<BasketData>,
    var categories :List<CategoriesData>,
    var small_categories :List<SmallCategoriesData>,
    val banners : List<BannerData>,
    val payments: List<PaymentData>,
    @SerializedName("point_logs")
    val pointLogs: List<PointLogData>,
    var cards: List<CardData>,
    val replies: List<ReplyData>,

) {
}