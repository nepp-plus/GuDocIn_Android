package com.neppplus.gudocin_android.ui.payment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentPaymentBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.payment.PaymentData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentFragment : BaseFragment() {

    lateinit var binding: FragmentPaymentBinding

    lateinit var retrofitService: RetrofitService

    lateinit var mPaymentRecyclerViewAdapter: PaymentRecyclerViewAdapter

    val mPaymentList = ArrayList<PaymentData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding<FragmentPaymentBinding>(inflater, R.layout.fragment_payment, container).apply {
        retrofitService =
            Retrofit.getRetrofit(requireContext()).create(RetrofitService::class.java)
        fragment = this@PaymentFragment
        initView()
    }.root

    private fun FragmentPaymentBinding.setRecyclerView() {
        mPaymentRecyclerViewAdapter = PaymentRecyclerViewAdapter(mPaymentList)
        rvPayment.apply {
            adapter = mPaymentRecyclerViewAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun FragmentPaymentBinding.initView() {
        getRequestUserPayment()
        setRecyclerView()
    }

    private fun getRequestUserPayment() {
        retrofitService.getRequestUserPayment().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val basicResponse = response.body()!!
                    mPaymentList.apply {
                        clear()
                        addAll(basicResponse.data.payments)
                    }
                    mPaymentRecyclerViewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

}