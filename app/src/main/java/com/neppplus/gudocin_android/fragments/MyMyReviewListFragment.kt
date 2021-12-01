package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentMymyReviewListBinding

class MyMyReviewListFragment : BaseFragment() {

    lateinit var binding : FragmentMymyReviewListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate( inflater, R.layout.fragment_mymy_review_list,container,false )

        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setValues()
        setupEvents()
    }
    override fun setupEvents() {

    }

    override fun setValues() {

    }
}