package com.neppplus.gudoc_in.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudoc_in.R
import com.neppplus.gudoc_in.adapters.PaymentListRecyclerViewAdapter
import com.neppplus.gudoc_in.databinding.FragmentPaymentListBinding
import com.neppplus.gudoc_in.datas.BasicResponse
import com.neppplus.gudoc_in.datas.PaymentData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentListFragment : BaseFragment() {

    lateinit var binding: FragmentPaymentListBinding

    val mPaymentList = ArrayList<PaymentData>()

    lateinit var mPaymentListRecyclerViewAdapter: PaymentListRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_payment_list, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        mPaymentListRecyclerViewAdapter =
            PaymentListRecyclerViewAdapter(mContext, mPaymentList)
        binding.paymentRecyclerView.adapter = mPaymentListRecyclerViewAdapter
        binding.paymentRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    override fun setValues() {
        getpaymentListFromServer()
    }

    fun getpaymentListFromServer() {
        apiService.getRequestUserpaymentList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mPaymentList.clear()
                    mPaymentList.addAll(br.data.payments)
                    mPaymentListRecyclerViewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}