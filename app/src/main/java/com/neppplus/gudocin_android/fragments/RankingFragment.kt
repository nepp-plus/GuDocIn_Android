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
    lateinit var mReviewAdapter: RankingRecyclerViewListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_ranking, container, false)
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
        binding.rankingRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)

//        binding.btnSortData.setOnClickListener{
//            sortData()
//        }
    }

    fun getRakingListFromServer() {
        apiService.getRequestRankingList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mRankingList.clear()
                    mRankingList.addAll(br.data.reviews)

/* //                    정렬 바꿔보기
                    Collections.sort(br,Comparator<Any?> { lhs, rhs ->
                        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                            if (lhs.getId() > rhs.getId()) -1 else if (lhs.customInt < rhs.customInt) 1 else 0
                        }) */
                    mReviewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

/*    fun sortData(condition: Boolean){

        if (condition) {
            mRankingList.sort()
        } else {
            mRankingList.reverse()
        }
        mReviewAdapter.notifyDataSetChanged()

    } */

}