package com.neppplus.gudocin_android

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.adapters.ProductRecyclerViewAdapter
import com.neppplus.gudocin_android.adapters.SmallCategoriesListAdapter
import com.neppplus.gudocin_android.databinding.ActivityWearCategoryListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.datas.SmallCategoriesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WearCategoryListActivity : BaseActivity() {

    lateinit var binding: ActivityWearCategoryListBinding

    val mSmallCategoryList = ArrayList<SmallCategoriesData>()
    lateinit var mSmallCategoryListAdapter: SmallCategoriesListAdapter

    var mLargeCategoryId = 1
    var mClickedSmallCategoryNum = 10

    val mProductList = ArrayList<ProductData>()
    lateinit var mProductRecyclerAdapter: ProductRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_wear_category_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        binding.txtSelectedCategoryName.text = "의류 구독"

        getSmallCategoryListFromServer()
        mSmallCategoryListAdapter = SmallCategoriesListAdapter(mContext, mSmallCategoryList)

        getProductListInSmallCategoryFromServer()
        mProductRecyclerAdapter = ProductRecyclerViewAdapter(mContext, mProductList)
        binding.productListRecyclerView.adapter = mProductRecyclerAdapter
        binding.productListRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getProductListInSmallCategoryFromServer() {
        apiService.getRequestSmallCategoriesItemList(mClickedSmallCategoryNum).enqueue(object :
            Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mProductList.clear()
                    mProductList.addAll(br.data.products)
                    mProductRecyclerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

    fun getSmallCategoryListFromServer() {
        apiService.getRequestSmallCategoryDependOnLarge(mLargeCategoryId).enqueue(object :
            Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    binding.smallCategoryList.removeAllViews()
                    val br = response.body()!!
                    mSmallCategoryList.clear()
                    mSmallCategoryList.addAll(br.data.small_categories)

//                    추가한 카테고리 하나하나에 대한 view 생성
                    for (sc in mSmallCategoryList) {
                        val view = LayoutInflater.from(mContext)
                            .inflate(R.layout.small_categories_item, null)
                        val txtSmallCategoryName =
                            view.findViewById<TextView>(R.id.txtSmallCategoryName)
                        txtSmallCategoryName.text = sc.name

                        view.setOnClickListener {
                            mClickedSmallCategoryNum = sc.id
                            getProductListInSmallCategoryFromServer()
                        }
                        binding.smallCategoryList.addView(view)
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}