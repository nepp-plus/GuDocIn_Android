package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentRankingBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import com.neppplus.gudocin_android.datas.ReviewRakingData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankingFragment : BaseFragment() {

    lateinit var binding: FragmentRankingBinding

    val mRankingList = ArrayList<ReviewRakingData>()



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
    }

    fun getRakingListFromServer(){

        apiService.getRequestRankingList().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful){

                    val br = response.body()!!

                    mRankingList.clear()


                }


            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }


}