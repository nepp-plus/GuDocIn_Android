package com.neppplus.gudocin_android.view.presenter.fragment.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentProductBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.view.adapter.product.ProductRecyclerViewAdapter
import com.neppplus.gudocin_android.view.presenter.fragment.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductFragment : BaseFragment() {

  lateinit var binding: FragmentProductBinding

  val mProductList = ArrayList<ProductData>()

  lateinit var mProductRecyclerViewAdapter: ProductRecyclerViewAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupEvents()
    setValues()
  }

  override fun setupEvents() {}

  override fun setValues() {
    getProductFromServer()

    mProductRecyclerViewAdapter = ProductRecyclerViewAdapter(mContext, mProductList)
    binding.rvProduct.adapter = mProductRecyclerViewAdapter
    binding.rvProduct.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
  }

  private fun getProductFromServer() {
    apiService.getRequestProduct().enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          mProductList.clear()
          mProductList.addAll(response.body()!!.data.products)
          mProductRecyclerViewAdapter.notifyDataSetChanged()
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
    })
  }

}