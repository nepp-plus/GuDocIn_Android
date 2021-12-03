package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.RankingRecyclerViewListAdapter
import com.neppplus.gudocin_android.databinding.FragmentRankingBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankingFragment : BaseFragment() {

    lateinit var binding: FragmentRankingBinding

    val mRankingList = ArrayList<ReviewData>()

    lateinit var mReviewAdapter : RankingRecyclerViewListAdapter



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_ranking,container,false)
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

        getRakingListFromServer()

        mReviewAdapter = RankingRecyclerViewListAdapter(mContext, mRankingList)
        binding.rankingRecyclerView.adapter = mReviewAdapter
        binding.rankingRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

    }

    fun getRakingListFromServer(){

        apiService.getRequestRankingList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful){

                    val br = response.body()!!

                    mRankingList.clear()
                    mRankingList.addAll( br.data.reviews )

                    mReviewAdapter.notifyDataSetChanged()
                }


            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }


}