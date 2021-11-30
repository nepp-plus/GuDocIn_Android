package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.BannerViewPagerAdapter
import com.neppplus.gudocin_android.adapters.ReviewRecyclerViewAdapterForMain
import com.neppplus.gudocin_android.databinding.FragmentHomeBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : BaseFragment() {

    var  homeBannerHandeler = HomeBannerHandeler()

    lateinit var binding: FragmentHomeBinding


    val bannerPosition = Int.MAX_VALUE / 2
    private val intervalTime = 1500.toLong()

    lateinit var mBannerViewPagerAdapter: BannerViewPagerAdapter
//    activity?.let { HomeBannerAdapter(bannerList, it) }

    val mBannerList = ArrayList<ProductData>()

    val mReviewList = ArrayList<ReviewData>()

    lateinit var mReviewRecyclerViewAdapterForMain: ReviewRecyclerViewAdapterForMain

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        getBannerImgFromServer()

        binding.mainBannerViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        binding.mainBannerViewPager.setCurrentItem(bannerPosition, false)

        binding.mainBannerViewPager.adapter = mBannerViewPagerAdapter

        binding.mainBannerViewPager.apply {
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                    when (state) {
                        ViewPager2.SCROLL_STATE_DRAGGING -> autoScrollStop()
                        ViewPager2.SCROLL_STATE_IDLE -> autoScrollStart(intervalTime)
                    }
                }
            })

        }

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

        mReviewRecyclerViewAdapterForMain = ReviewRecyclerViewAdapterForMain(mContext, mReviewList)
        binding.reviewListRecyclerView.adapter = mReviewRecyclerViewAdapterForMain
        binding.reviewListRecyclerView.layoutManager = LinearLayoutManager(mContext)


    }


    fun autoScrollStop(){
        homeBannerHandeler.removeMessages(0)
    }

    fun autoScrollStart(intercaltime:Long){
    homeBannerHandeler.removeMessages(0)
    homeBannerHandeler.sendEmptyMessageDelayed(0,intervalTime)
    }

    inner class HomeBannerHandeler:Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what ==0){
                binding.mainBannerViewPager.setCurrentItem(bannerPosition,true)
                autoScrollStart(intervalTime)
            }
        }
    }

    fun getBannerImgFromServer() {

        apiService.getRequestProductList(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
                    var br = response.body()!!
                    mBannerList.clear()

                    for (products in br.data.products) {
                        mBannerList.add(products.imageUrl)
                    }
                    mBannerViewPagerAdapter.notifyDataSetChanged()


                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }


    fun getReviewListFromServer() {

        apiService.getRequestReviewList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {

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