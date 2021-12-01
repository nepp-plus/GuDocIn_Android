package com.neppplus.gudocin_android

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.adapters.ProductRecyclerViewAdapter
import com.neppplus.gudocin_android.databinding.ActivityEatCategoryListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EatCategoryListActivity : BaseActivity() {

    lateinit var binding:ActivityEatCategoryListBinding
    val mProductList = ArrayList<ProductData>()
    lateinit var mProductRecyclerAdapter : ProductRecyclerViewAdapter
    var mClickedSmallCategoryNum =7

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_eat_category_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        getProductListInSmallCategoryFromServer()

        mProductRecyclerAdapter = ProductRecyclerViewAdapter(mContext,mProductList)
        binding.productListRecyclerView.adapter = mProductRecyclerAdapter
        binding.productListRecyclerView.layoutManager = LinearLayoutManager(mContext)
//

    }

    fun getProductListInSmallCategoryFromServer(){

        apiService.getRequestSmallCategorysItemList(mClickedSmallCategoryNum).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful ){

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