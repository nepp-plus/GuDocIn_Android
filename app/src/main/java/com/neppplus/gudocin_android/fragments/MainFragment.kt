package com.neppplus.gudocin_android.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.MainRecyclerAdapter
import com.neppplus.gudocin_android.databinding.FragmentMainBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : BaseFragment() {

    lateinit var binding: FragmentMainBinding
    lateinit var mMainRecyclerAdapter : MainRecyclerAdapter

    val mReviewList = ArrayList<ReviewData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
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

        mMainRecyclerAdapter = MainRecyclerAdapter(mContext, mReviewList)
        binding.mainRecyclerView.adapter = mMainRecyclerAdapter
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(mContext)

        getAllReviewData()
    }



    fun getAllReviewData() {
        apiService.getRequestReviewList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                val br = response.body()!!

                mReviewList.clear()
                mReviewList.addAll(br.data.reviews)

                mMainRecyclerAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }
}