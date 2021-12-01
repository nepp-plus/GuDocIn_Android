package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentReviewDetailBinding

class ReviewDetailFragment : BaseFragment() {

    lateinit var binding: FragmentReviewDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_review_detail,container,false)
        return binding.root
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}