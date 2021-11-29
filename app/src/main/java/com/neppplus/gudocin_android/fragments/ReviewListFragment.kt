package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.ReviewRecyclerViewAdapterForMain
import com.neppplus.gudocin_android.databinding.FragmentReviewListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewListFragment : BaseFragment() {


    lateinit var binding: FragmentReviewListBinding
    val mReviewList = ArrayList<ReviewData>()
    lateinit var mReviewRecyclerViewAdapterForMain: ReviewRecyclerViewAdapterForMain

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_review_list,container,false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {



    }

    override fun setValues() {

        getReviewListFromServer()

        mReviewRecyclerViewAdapterForMain = ReviewRecyclerViewAdapterForMain(mContext,mReviewList)
        binding.reviewListRecyclerView.adapter = mReviewRecyclerViewAdapterForMain
        binding.reviewListRecyclerView.layoutManager = LinearLayoutManager(mContext)



    }

    fun getReviewListFromServer(){





    }

}