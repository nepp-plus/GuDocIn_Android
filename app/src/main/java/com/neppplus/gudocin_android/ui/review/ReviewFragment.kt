package com.neppplus.gudocin_android.ui.review

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentReviewBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseFragment
import com.neppplus.gudocin_android.ui.review.subscription.SubscriptionRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewFragment : BaseFragment() {

    lateinit var binding: FragmentReviewBinding

    lateinit var retrofitService: RetrofitService

    val mReviewList = ArrayList<ReviewData>()

    lateinit var mSubscriptionRecyclerViewAdapter: SubscriptionRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding<FragmentReviewBinding>(inflater, R.layout.fragment_review, container).apply {
        retrofitService =
            Retrofit.getRetrofit(requireContext()).create(RetrofitService::class.java)
        fragment = this@ReviewFragment
        initView()
    }.root

    private fun FragmentReviewBinding.setRecyclerView() {
        mSubscriptionRecyclerViewAdapter =
            SubscriptionRecyclerViewAdapter(mReviewList)
        rvReview.apply {
            adapter = mSubscriptionRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun FragmentReviewBinding.initView() {
        getRequestUserReview()
        setRecyclerView()
    }

    private fun getRequestUserReview() {
        retrofitService.getRequestUserReview().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val basicResponse = response.body()!!
                    mReviewList.apply {
                        clear()
                        addAll(basicResponse.data.reviews)
                    }
                    mSubscriptionRecyclerViewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

}