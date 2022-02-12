package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.ReviewListRecyclerVewAdapterForMain
import com.neppplus.gudocin_android.databinding.FragmentHomeBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding

    val mReviewList = ArrayList<ReviewData>()

    lateinit var mMainReviewListRecyclerAdapter: ReviewListRecyclerVewAdapterForMain

    var mClickedSmallCategoryNum = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
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
        getBannerListFromServer()
        // CategoryFragment 에서 소분류 클릭한 숫자 받아오기
        getReviewListInSmallCategoryFromServer(mClickedSmallCategoryNum)

        mMainReviewListRecyclerAdapter = ReviewListRecyclerVewAdapterForMain(mContext, mReviewList)
        binding.reviewListRecyclerView.adapter = mMainReviewListRecyclerAdapter
        binding.reviewListRecyclerView.layoutManager = LinearLayoutManager(mContext)

        binding.reviewListRecyclerView.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.reviewListRecyclerView.canScrollVertically(1)) {
                    Log.d("SCROLL", "끝났음");
                    binding.imgPageUp.visibility = View.VISIBLE
                    pageUpListener(binding.reviewListRecyclerView)
                } else {
                    binding.imgPageUp.visibility = View.GONE
                }
            }
        })
    }

    fun pageUpListener(view: RecyclerView?) {
        binding.imgPageUp.setOnClickListener {
            view?.smoothScrollToPosition(0)
        }
    }

    fun getReviewListInSmallCategoryFromServer(mClickedSmallCategoryNum: Int) {
        if (isInitialized) {
            apiService.getRequestSmallCategorysItemReviewList(mClickedSmallCategoryNum)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            var br = response.body()!!
                            mReviewList.clear()
                            mReviewList.addAll(br.data.reviews)
                            mMainReviewListRecyclerAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
        }
    }

    fun getBannerListFromServer() {
        apiService.getRequestMainBanner().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mMainReviewListRecyclerAdapter.mBannerList.clear()
                    mMainReviewListRecyclerAdapter.mBannerList.addAll(br.data.banners)
                    mMainReviewListRecyclerAdapter.mBannerViewPagerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}


