package com.neppplus.gudocin_android

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.adapters.BasketRecyclerAdapter
import com.neppplus.gudocin_android.databinding.ActivityBasketListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.BasketData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BasketListActivity : BaseActivity() {

    lateinit var binding: ActivityBasketListBinding

    val mBasketList = ArrayList<BasketData>()

    lateinit var mBasketRecyclerAdapter: BasketRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_basket_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        getBasketListFromServer()

        mBasketRecyclerAdapter = BasketRecyclerAdapter(mContext, mBasketList)

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