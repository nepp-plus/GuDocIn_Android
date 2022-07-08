package com.neppplus.gudocin_android.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentHomeBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseFragment
import com.neppplus.gudocin_android.ui.review.main.MainRecyclerVewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment() {

    lateinit var retrofitService: RetrofitService

    val mReviewList = ArrayList<ReviewData>()

    lateinit var mReviewRecyclerAdapter: MainRecyclerVewAdapter

    private val mSmallCategoryNum = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding<FragmentHomeBinding>(inflater, R.layout.fragment_home, container).apply {
        retrofitService =
            Retrofit.getRetrofit(requireContext()).create(RetrofitService::class.java)
        fragment = this@HomeFragment
        initView()
    }.root

    private fun FragmentHomeBinding.initView() {
        getRequestSmallCategoryReview(mSmallCategoryNum)
        getRequestBanner()
        setScrollListener()
        setRecyclerView()
    }

    private fun FragmentHomeBinding.setRecyclerView() {
        mReviewRecyclerAdapter = MainRecyclerVewAdapter(mReviewList)
        rvReview.apply {
            adapter = mReviewRecyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun FragmentHomeBinding.setScrollListener() {
        rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!rvReview.canScrollVertically(1)) {
                    Log.d("SCROLL", "끝났음")
                    imgPageUp.visibility = View.VISIBLE
                } else
                    imgPageUp.visibility = View.GONE
            }
        })
    }

    fun pageUpListener(view: RecyclerView?) {
            view?.smoothScrollToPosition(0)
    }

    fun getRequestSmallCategoryReview(mSmallCategoryNum: Int) {
        retrofitService.getRequestSmallCategoryReview(mSmallCategoryNum)
            .enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!
                        mReviewList.apply {
                            clear()
                            addAll(basicResponse.data.reviews)
                        }
                        mReviewRecyclerAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    Log.d("onFailure", resources.getString(R.string.data_loading_failed))
                }
            })
    }

    private fun getRequestBanner() {
        retrofitService.getRequestBanner().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val basicResponse = response.body()!!
                    mReviewRecyclerAdapter.apply {
                        mBannerList.clear()
                        mBannerList.addAll(basicResponse.data.banners)
                        mBannerViewPagerAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

}