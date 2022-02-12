package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.ProductRecyclerAdapter
import com.neppplus.gudocin_android.databinding.FragmentProductBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductFragment : BaseFragment() {

    lateinit var binding: FragmentProductBinding

    val mProductList = ArrayList<ProductData>()

    lateinit var mProductRecyclerAdapter: ProductRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
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
        getProductListFromServer()

        mProductRecyclerAdapter = ProductRecyclerAdapter(mContext, mProductList)
        binding.productListRecyclerView.adapter = mProductRecyclerAdapter
        binding.productListRecyclerView.layoutManager =
            StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
    }

    fun getProductListFromServer() {
        apiService.getRequestProductList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    mProductList.clear()
                    mProductList.addAll(response.body()!!.data.products)
                    mProductRecyclerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}