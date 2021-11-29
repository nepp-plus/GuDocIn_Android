package com.neppplus.gudocin_android.datas

class DataResponse(
    var user: UserData,
    var token: String,
    var reviews: List<ReviewData>,
    var product: List<ProductData>,
    var categiries :List<CategoriesData>,
) {
}