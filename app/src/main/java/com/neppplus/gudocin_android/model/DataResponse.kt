package com.neppplus.gudocin_android.model

class DataResponse(
    var user: UserData,
    var token: String,
    var reviews: List<ReviewData>,
    var products: List<ProductData>,
    var product: ProductData,
    var carts: List<CartData>,
    var categories: List<CategoryData>,
    var small_categories: List<SmallCategoryData>,
    val banners: List<BannerData>,
    val payments: List<PaymentData>,
    val replies: List<ReplyData>,
    var cards: List<CardData>
) {
}