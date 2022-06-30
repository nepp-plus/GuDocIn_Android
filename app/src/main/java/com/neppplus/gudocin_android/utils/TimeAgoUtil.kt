package com.neppplus.gudocin_android.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeAgoUtil {

    // Date 값을 넣으면 -> 현재 시간과 비교해서 -> 몇시간 전인지 알려줌
    companion object {
        private val dateFormat = SimpleDateFormat("yyyy년 M월 d일")

        fun getTimeAgoString(date: Date): String {
//            현재 시간 추출 -> Local TimeZone 설정되어있음
            val now = Calendar.getInstance()
//            재료로 들어오는 date -> DateDeserializer 클래스를 통해 시차 보정 되어있음
            val timeDiff = now.timeInMillis - date.time

            if (timeDiff < 5 * 1000) {
//                5초 이내에 작성된 글인 경우
                return "몇초 전"
            } else if (timeDiff < 60 * 1000) {
//                1분 이내인 경우
                val second = timeDiff / 1000
                return "${second}초 전"
            } else if (timeDiff < 60 * 60 * 1000) {
//                1시간 이내인 경우
                val minute = timeDiff / 1000 / 60
                return "${minute}분 전"
            } else if (timeDiff < 24 * 60 * 60 * 1000) {
//                1일 이내인 경우
                val hour = timeDiff / 1000 / 60 / 60
                return "${hour}시간 전"
            } else {
                return dateFormat.format(date)
            }
        }
    }

}