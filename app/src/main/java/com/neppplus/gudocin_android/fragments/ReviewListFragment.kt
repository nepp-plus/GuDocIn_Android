package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.reviews.SubscriptionReviewListRecyclerViewAdapter
import com.neppplus.gudocin_android.databinding.FragmentReviewListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewListFragment : BaseFragment() {

    val mReviewList = ArrayList<ReviewData>()

    lateinit var binding: FragmentReviewListBinding

    lateinit var mSubscriptionReviewRecyclerViewAdapter: SubscriptionReviewListRecyclerViewAdapter

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
        mSubscriptionReviewRecyclerViewAdapter = SubscriptionReviewListRecyclerViewAdapter(mContext, mReviewList)
        binding.reviewListRecyclerView.adapter = mSubscriptionReviewRecyclerViewAdapter
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
                    mSubscriptionReviewRecyclerViewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}