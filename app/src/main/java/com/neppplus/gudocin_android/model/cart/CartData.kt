package com.neppplus.gudocin_android.model.cart

import com.neppplus.gudocin_android.model.product.ProductData

data class CartData(
  var id: Int,
  var product: ProductData,
) {
}