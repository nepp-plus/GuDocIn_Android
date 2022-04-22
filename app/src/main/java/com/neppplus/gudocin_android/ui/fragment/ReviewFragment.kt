package com.neppplus.gudocin_android.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ui.adapter.reviews.SubscriptionRecyclerViewAdapter
import com.neppplus.gudocin_android.databinding.FragmentReviewBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewFragment : BaseFragment() {

    lateinit var binding: FragmentReviewBinding

    val mReviewList = ArrayList<ReviewData>()

    lateinit var mSubscriptionRecyclerViewAdapter: SubscriptionRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_review, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setValues()
        setupEvents()
    }

    override fun setupEvents() {
        mSubscriptionRecyclerViewAdapter =
            SubscriptionRecyclerViewAdapter(mContext, mReviewList)
        binding.rvReview.adapter = mSubscriptionRecyclerViewAdapter
        binding.rvReview.layoutManager = LinearLayoutManager(mContext)
    }

    override fun setValues() {
        getReviewFromServer()
    }

    fun getReviewFromServer() {
        apiService.getRequestUserReview().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mReviewList.clear()
                    mReviewList.addAll(br.data.reviews)
                    mSubscriptionRecyclerViewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}