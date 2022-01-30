package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.MyReviewListRecyclerviewAdapter
import com.neppplus.gudocin_android.databinding.FragmentMyReviewListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReviewListFragment : BaseFragment() {

    val mMyReviewList = ArrayList<ReviewData>()

    lateinit var binding: FragmentMyReviewListBinding
    lateinit var mReviewRecyclerviewAdapter: MyReviewListRecyclerviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_my_review_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setValues()
        setupEvents()
    }

    override fun setupEvents() {
        mReviewRecyclerviewAdapter = MyReviewListRecyclerviewAdapter(mContext, mMyReviewList)
        binding.myReviewListRecyclerView.adapter = mReviewRecyclerviewAdapter
        binding.myReviewListRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    override fun setValues() {
        getMyReviewListFromServer()
    }

    fun getMyReviewListFromServer() {
        apiService.getRequestUserReviewList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mMyReviewList.clear()
                    mMyReviewList.addAll(br.data.reviews)
                    mReviewRecyclerviewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}