package com.neppplus.gudocin_android.view.presenter.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.view.adapter.payment.PaymentRecyclerViewAdapter
import com.neppplus.gudocin_android.databinding.FragmentPaymentBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.payment.PaymentData
import com.neppplus.gudocin_android.view.presenter.fragment.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentFragment : BaseFragment() {

    lateinit var binding: FragmentPaymentBinding

    val mPaymentList = ArrayList<PaymentData>()

    lateinit var mPaymentRecyclerViewAdapter: PaymentRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        mPaymentRecyclerViewAdapter = PaymentRecyclerViewAdapter(mContext, mPaymentList)
        binding.rvPayment.adapter = mPaymentRecyclerViewAdapter
        binding.rvPayment.layoutManager = LinearLayoutManager(mContext)
    }

    override fun setValues() {
        getPaymentFromServer()
    }

    private fun getPaymentFromServer() {
        apiService.getRequestUserPayment().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mPaymentList.clear()
                    mPaymentList.addAll(br.data.payments)
                    mPaymentRecyclerViewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
        })
    }

}