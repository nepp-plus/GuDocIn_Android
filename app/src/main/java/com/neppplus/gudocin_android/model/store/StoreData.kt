package com.neppplus.gudocin_android.model.store

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StoreData(
  @SerializedName("id")
  val id: Int,

  @SerializedName("name")
  val name: String,

  @SerializedName("logo_url")
  val logoUrl: String,
) : Serializable