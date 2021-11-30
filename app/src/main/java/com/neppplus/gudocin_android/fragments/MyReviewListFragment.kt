package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import com.neppplus.gudocin_android.databinding.FragmentMyReviewListBinding
import com.neppplus.gudocin_android.datas.ReviewData


class MyReviewListFragment : BaseFragment() {

    lateinit var binding : FragmentMyReviewListBinding

    val mReviewList = ArrayList<ReviewData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setupEvents() {

    }

    override fun setValues() {


    }

}