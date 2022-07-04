package com.neppplus.gudocin_android.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentHomeBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.ui.base.BaseFragment
import com.neppplus.gudocin_android.ui.review.main.MainRecyclerVewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding

    val mReviewList = ArrayList<ReviewData>()

    lateinit var mMainReviewRecyclerAdapter: MainRecyclerVewAdapter

    private var mClickedSmallCategoryNum = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {}

    override fun setValues() {
        getBannerFromServer()
        getReviewFromServer(mClickedSmallCategoryNum)
        mMainReviewRecyclerAdapter = MainRecyclerVewAdapter(mContext, mReviewList)

        binding.rvReview.apply {
            adapter = mMainReviewRecyclerAdapter
            layoutManager = LinearLayoutManager(mContext)

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!binding.rvReview.canScrollVertically(1)) {
                        Log.d("SCROLL", "끝났음")
                        binding.imgPageUp.visibility = View.VISIBLE
                        pageUpListener(binding.rvReview)
                    } else {
                        binding.imgPageUp.visibility = View.GONE
                    }
                }
            })
        }
    }

    fun pageUpListener(view: RecyclerView?) {
        binding.imgPageUp.setOnClickListener {
            view?.smoothScrollToPosition(0)
        }
    }

    fun getReviewFromServer(mClickedSmallCategoryNum: Int) {
        apiService.getRequestSmallCategoryReview(mClickedSmallCategoryNum)
            .enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!
                        mReviewList.clear()
                        mReviewList.addAll(basicResponse.data.reviews)
                        mMainReviewRecyclerAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    Log.d("onFailure", resources.getString(R.string.data_loading_failed))
                }
            })
    }

    private fun getBannerFromServer() {
        apiService.getRequestBanner().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val basicResponse = response.body()!!
                    mMainReviewRecyclerAdapter.mBannerList.clear()
                    mMainReviewRecyclerAdapter.mBannerList.addAll(basicResponse.data.banners)
                    mMainReviewRecyclerAdapter.mBannerViewPagerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

}


