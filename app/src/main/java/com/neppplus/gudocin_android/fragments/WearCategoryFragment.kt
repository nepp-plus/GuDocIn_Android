package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.CategoryAdapter
import com.neppplus.gudocin_android.adapters.ProductRecyclerViewAdapter
import com.neppplus.gudocin_android.databinding.FragmentWearCategoryBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.datas.SmallCategoryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WearCategoryFragment : BaseFragment() {

    lateinit var binding: FragmentWearCategoryBinding

    val mSmallCategoryList = ArrayList<SmallCategoryData>()
    lateinit var mCategoryAdapter: CategoryAdapter

    var mLargeCategoryId = 2
    var mClickedSmallCategoryNum = 6

    val mProductList = ArrayList<ProductData>()
    lateinit var mProductRecyclerAdapter: ProductRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_wear_category,
                container,
                false
            )
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
        binding.txtSelectedCategory.text = "의류구독"
        getSmallCategoryListFromServer()
        mCategoryAdapter = CategoryAdapter(mContext, mSmallCategoryList)

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
                            .inflate(R.layout.category_list_item, null)
                        val txtSmallCategory =
                            view.findViewById<TextView>(R.id.txtSmallCategory)
                        txtSmallCategory.text = sc.name

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