package com.neppplus.gudoc_in.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudoc_in.R
import com.neppplus.gudoc_in.adapters.reviews.ReviewListRecyclerViewAdapterForProfile
import com.neppplus.gudoc_in.databinding.FragmentReviewListBinding
import com.neppplus.gudoc_in.datas.BasicResponse
import com.neppplus.gudoc_in.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewListFragment : BaseFragment() {

    val mReviewList = ArrayList<ReviewData>()

    lateinit var binding: FragmentReviewListBinding

    lateinit var mReviewRecyclerViewAdapterForProfile: ReviewListRecyclerViewAdapterForProfile

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_review_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setValues()
        setupEvents()
    }

    override fun setupEvents() {
        mReviewRecyclerViewAdapterForProfile = ReviewListRecyclerViewAdapterForProfile(mContext, mReviewList)
        binding.reviewListRecyclerView.adapter = mReviewRecyclerViewAdapterForProfile
        binding.reviewListRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    override fun setValues() {
        getMyReviewListFromServer()
    }

    fun getMyReviewListFromServer() {
        apiService.getRequestUserReviewList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mReviewList.clear()
                    mReviewList.addAll(br.data.reviews)
                    mReviewRecyclerViewAdapterForProfile.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}