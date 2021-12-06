package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.BasketRecyclerAdapter
import com.neppplus.gudocin_android.databinding.FragmentBasketListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.BasketData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasketListFragment : BaseFragment() {

    lateinit var binding: FragmentBasketListBinding

    val mBasketList = ArrayList<BasketData>()

    lateinit var mBasketRecyclerAdapter: BasketRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_basket_list, container, false)
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

        getBasketListFromServer()

        mBasketRecyclerAdapter = BasketRecyclerAdapter(mContext, mBasketList)
        binding.basketListRecyclerView.adapter = mBasketRecyclerAdapter
        binding.basketListRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }

    fun getBasketListFromServer() {

        apiService.getRequestBasketList("X-Http-Token").enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    mBasketList.clear()

                    mBasketList.addAll(response.body()!!.data.baskets)

                    mBasketRecyclerAdapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

}