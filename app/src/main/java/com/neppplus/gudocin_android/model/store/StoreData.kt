package com.neppplus.gudocin_android.model.store

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StoreData(
  var id: Int,
  var name: String,
  @SerializedName("logo_url")
  var logoUrl: String,
) : Serializable {
}