package com.neppplus.gudocin_android.api

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class DateDesirializer : JsonDeserializer<Date> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date {

//        시간 파싱 (여기서 수정 X. 쓸곳에 가져다가 맞게 수정해서 쓰세요)

        val dateStr = json?.asString

        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        val date = sdf.parse(dateStr)!!

        val now = Calendar.getInstance()

        date.time += now.timeZone.rawOffset

        return date

    }
}