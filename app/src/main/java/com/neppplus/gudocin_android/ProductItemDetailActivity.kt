package com.neppplus.gudocin_android

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.adapters.ReviewRecyclerViewAdapterForProductList
import com.neppplus.gudocin_android.adapters.SmallCategoriesListAdapter
import com.neppplus.gudocin_android.databinding.ActivityProductItemDetailBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductItemDetailActivity : BaseActivity() {

    lateinit var binding: ActivityProductItemDetailBinding
    var selectedItemFromList = 1
    val mReviewList = ArrayList<ReviewData>()
    lateinit var mReviewRecyclerViewAdapterForProductList : ReviewRecyclerViewAdapterForProductList


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_product_item_detail)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {



    }

    override fun setValues() {

        getProductItemDetailFromServer()

        mReviewRecyclerViewAdapterForProductList = ReviewRecyclerViewAdapterForProductList(mContext,mReviewList)
        binding.reviewRecyclerViewForProduct.adapter = mReviewRecyclerViewAdapterForProductList
        binding.reviewRecyclerViewForProduct.layoutManager = LinearLayoutManager(mContext,
            LinearLayoutManager.HORIZONTAL,false)

    }

    fun getProductItemDetailFromServer(){
        apiService.getRequestProductDetail(selectedItemFromList).enqueue( object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
                    val br = response.body()!!
                    binding.txtProductName.text= br.data.product.name
                    binding.txtProductPrice.text = br.data.product.getFormatedPrice()
                    binding.txtProductCompanyName.text = br.data.product.store.name
                    Glide.with(mContext).load(br.data.product.imageUrl).into(binding.imgProduct)

                    mReviewList.clear()
                    mReviewList.addAll(response.body()!!.data.reviews)
                    mReviewRecyclerViewAdapterForProductList.notifyDataSetChanged()
                    
                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }
}