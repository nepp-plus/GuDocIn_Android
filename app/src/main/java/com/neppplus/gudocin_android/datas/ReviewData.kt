package com.neppplus.gudocin_android.datas

import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.*

class ReviewData(

    var id : Int,
    var title : String,
    var content : String,
    var score : Double,

    var product : ProductData,
    var user: UserData,

    ) : Serializable{
}