package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.MyReviewListRecyclerviewAdapter
import com.neppplus.gudocin_android.adapters.MySaveMoneyPaymentRecyclerViewAdapter
import com.neppplus.gudocin_android.databinding.FragmentMySavemoneyPaymentBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.CardData
import com.neppplus.gudocin_android.datas.PointLogData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySaveMoneyPaymentFragment: BaseFragment() {

    val mMyPointList = ArrayList<PointLogData>()

    lateinit var mMyPointPaymentRecyclerViewAdapter: MySaveMoneyPaymentRecyclerViewAdapter
    lateinit var binding: FragmentMySavemoneyPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_savemoney_payment,container,false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()


    }
    override fun setupEvents() {

        mMyPointPaymentRecyclerViewAdapter = MySaveMoneyPaymentRecyclerViewAdapter(mContext,mMyPointList)
        binding.mySaveMoneyPaymentRecyclerView.adapter = mMyPointPaymentRecyclerViewAdapter
        binding.mySaveMoneyPaymentRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }

    override fun setValues() {
        getMyReviewListFromServer()

        getMyReviewListFromServer()
    }

    fun getMyReviewListFromServer() {

        apiService.getRequestUserPointList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val br = response.body()!!

                    mMyPointList.clear()
                    mMyPointList.addAll(br.data.pointLogs)

                    mMyPointPaymentRecyclerViewAdapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }


        })


    }
}