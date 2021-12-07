package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.MyPurchaseListRecyclerviewAdapter
import com.neppplus.gudocin_android.databinding.FragmentPurchaseListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.PaymentData
import com.neppplus.gudocin_android.datas.ProductData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchaseListFragment : BaseFragment() {

    val mMyPurchaseList = ArrayList<PaymentData>()
    lateinit var binding : FragmentPurchaseListBinding
    lateinit var mMyPurchaseListRecyclerViewAdapter : MyPurchaseListRecyclerviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        데이터 바인딩을 같이 활용 -> xml도 지정

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_purchase_list,container,false)
        return binding.root
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()


    }
    override fun setupEvents() {

        mMyPurchaseListRecyclerViewAdapter = MyPurchaseListRecyclerviewAdapter(mContext, mMyPurchaseList)
        binding.myPurchaseRecyclerView.adapter = mMyPurchaseListRecyclerViewAdapter
        binding.myPurchaseRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }

    override fun setValues() {
        getPurchaseListFromServer()

    }

    fun getPurchaseListFromServer() {

        apiService.getRequestUserPurchaseList().enqueue( object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {


                if (response.isSuccessful) {

                    val br = response.body()!!

                   mMyPurchaseList.clear()
                    mMyPurchaseList.addAll(br.data.payments)

                   mMyPurchaseListRecyclerViewAdapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {



            }


        })

    }
}