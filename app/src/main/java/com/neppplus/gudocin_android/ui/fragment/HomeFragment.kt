package com.neppplus.gudocin_android.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ui.adapter.reviews.MainReviewRecyclerVewAdapter
import com.neppplus.gudocin_android.databinding.FragmentHomeBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding

    val mReviewList = ArrayList<ReviewData>()

    lateinit var mMainReviewRecyclerAdapter: MainReviewRecyclerVewAdapter

    var mClickedSmallCategoryNum = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
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
        getBannerFromServer()
        getReviewFromServer(mClickedSmallCategoryNum)

        mMainReviewRecyclerAdapter = MainReviewRecyclerVewAdapter(mContext, mReviewList)
        binding.rvReview.adapter = mMainReviewRecyclerAdapter
        binding.rvReview.layoutManager = LinearLayoutManager(mContext)

        binding.rvReview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!binding.rvReview.canScrollVertically(1)) {
                    Log.d("SCROLL", "끝났음");
                    binding.imgPageUp.visibility = View.VISIBLE
                    pageUpListener(binding.rvReview)
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

    fun getReviewFromServer(mClickedSmallCategoryNum: Int) {
        if (isInitialized) {
            apiService.getRequestSmallCategoriesReview(mClickedSmallCategoryNum)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            var br = response.body()!!
                            mReviewList.clear()
                            mReviewList.addAll(br.data.reviews)
                            mMainReviewRecyclerAdapter.notifyDataSetChanged()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
        }
    }

    fun getBannerFromServer() {
        apiService.getRequestMainBanner().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mMainReviewRecyclerAdapter.mBannerList.clear()
                    mMainReviewRecyclerAdapter.mBannerList.addAll(br.data.banners)
                    mMainReviewRecyclerAdapter.mBannerViewPagerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}


