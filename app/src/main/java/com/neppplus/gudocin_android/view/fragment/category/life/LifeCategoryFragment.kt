package com.neppplus.gudocin_android.view.fragment.category.life

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.view.adapter.category.CategoryRecyclerViewAdapter
import com.neppplus.gudocin_android.view.adapter.category.DetailCategoryRecyclerViewAdapter
import com.neppplus.gudocin_android.databinding.FragmentLifeCategoryBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.model.category.SmallCategoryData
import com.neppplus.gudocin_android.view.fragment.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LifeCategoryFragment : BaseFragment() {

    lateinit var binding: FragmentLifeCategoryBinding

    val mSmallCategoryList = ArrayList<SmallCategoryData>()

    val mProductList = ArrayList<ProductData>()

    lateinit var mCategoryRecyclerViewAdapter: CategoryRecyclerViewAdapter

    lateinit var mDetailCategoryRecyclerAdapter: DetailCategoryRecyclerViewAdapter

    var mLargeCategoryId = 3

    var mClickedSmallCategoryNum = 11

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_life_category,
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
        getCategoryFromServer()

        mCategoryRecyclerViewAdapter =
            CategoryRecyclerViewAdapter(mSmallCategoryList)

        getProductFromServer()

        mDetailCategoryRecyclerAdapter =
            DetailCategoryRecyclerViewAdapter(mProductList)
        binding.rvProduct.adapter = mDetailCategoryRecyclerAdapter
        binding.rvProduct.layoutManager = LinearLayoutManager(mContext)
    }

    fun getProductFromServer() {
        apiService.getRequestSmallCategoriesProduct(mClickedSmallCategoryNum).enqueue(object :
            Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mProductList.clear()
                    mProductList.addAll(br.data.products)
                    mDetailCategoryRecyclerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

    fun getCategoryFromServer() {
        apiService.getRequestSmallCategoryDependOnLarge(mLargeCategoryId).enqueue(object :
            Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    binding.llCategory.removeAllViews()
                    val br = response.body()!!
                    mSmallCategoryList.clear()
                    mSmallCategoryList.addAll(br.data.small_categories)

//                    추가한 카테고리 하나하나에 대한 view 생성
                    for (sc in mSmallCategoryList) {
                        val view = LayoutInflater.from(mContext)
                            .inflate(R.layout.adapter_category, null)
                        val txtSmallCategoryList =
                            view.findViewById<TextView>(R.id.txtCategory)
                        txtSmallCategoryList.text = sc.name

                        view.setOnClickListener {
                            mClickedSmallCategoryNum = sc.id
                            getProductFromServer()
                        }
                        binding.llCategory.addView(view)
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}