package com.neppplus.gudocin_android.ui.product

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentProductBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductFragment : BaseFragment() {

    lateinit var binding: FragmentProductBinding

    lateinit var retrofitService: RetrofitService

    lateinit var mProductRecyclerViewAdapter: ProductRecyclerViewAdapter

    val mProductList = ArrayList<ProductData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding<FragmentProductBinding>(inflater, R.layout.fragment_product, container).apply {
        fragment = this@ProductFragment
        retrofitService = Retrofit
            .getRetrofit(requireContext())
            .create(RetrofitService::class.java)
        initView()
    }.root

    private fun FragmentProductBinding.initView() {
        getRequestProduct()
        setProductRecyclerView()
    }

    private fun FragmentProductBinding.setProductRecyclerView() {
        mProductRecyclerViewAdapter = ProductRecyclerViewAdapter(mProductList)

        rvProduct.apply {
            adapter = mProductRecyclerViewAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }
    }

    private fun getRequestProduct() {
        retrofitService.getRequestProduct().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    mProductList.apply {
                        clear()
                        addAll(response.body()!!.data.products)
                    }
                    mProductRecyclerViewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

}