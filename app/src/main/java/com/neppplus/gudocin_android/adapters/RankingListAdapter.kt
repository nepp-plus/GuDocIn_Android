package com.neppplus.gudocin_android.adapters

import android.content.Context
import com.neppplus.gudocin_android.datas.ReviewData
import com.neppplus.gudocin_android.datas.ReviewRakingData

class RankingListAdapter(

    val mContext:Context,
    val mList: List<ReviewRakingData>
) : ArrayList<ReviewData>() {
}