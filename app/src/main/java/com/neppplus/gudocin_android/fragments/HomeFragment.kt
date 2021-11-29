package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.BannerViewPagerAdapter
import com.neppplus.gudocin_android.adapters.ReviewRecyclerViewAdapterForMain
import com.neppplus.gudocin_android.databinding.BannerItemForMainBinding
import com.neppplus.gudocin_android.databinding.FragmentHomeBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding

    val handler = Handler(Looper.getMainLooper()) {
        true
    }


    lateinit var mvpa: BannerViewPagerAdapter
    val mBannerList = ArrayList<String>()

    val mReviewList = ArrayList<ReviewData>()
    lateinit var mReviewRecyclerViewAdapterForMain: ReviewRecyclerViewAdapterForMain


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

        getBannerImgFromServer()

        mvpa = BannerViewPagerAdapter (childFragmentManager,mBannerList)
        binding.mainBannerViewPager.adapter = mvpa


        getReviewListFromServer()

        mReviewRecyclerViewAdapterForMain = ReviewRecyclerViewAdapterForMain(mContext, mReviewList)
        binding.reviewListRecyclerView.adapter = mReviewRecyclerViewAdapterForMain
        binding.reviewListRecyclerView.layoutManager = LinearLayoutManager(mContext)


    }


    fun getReviewListFromServer() {

        apiService.getRequestReviewList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful){

                    var br = response.body()!!
                    mBannerList.clear()
                    mBannerList.addAll(br.data.product.imgUrl)
                    mvpa.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })


    }

    fun getBannerImgFromServer(){

        apiService.getRequestProductList().enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful){

                    var br = response.body()!!
                    mReviewList.clear()
                    mReviewList.addAll(br.data.reviews)
                    mReviewRecyclerViewAdapterForMain.notifyDataSetChanged()
                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }


        })

    }




}