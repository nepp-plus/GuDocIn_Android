package com.neppplus.gudocin_android.ui.category.life

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ui.category.CategoryRecyclerViewAdapter
import com.neppplus.gudocin_android.ui.category.ParticularCategoryRecyclerViewAdapter
import com.neppplus.gudocin_android.databinding.FragmentLifeCategoryBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.model.category.SmallCategoryData
import com.neppplus.gudocin_android.ui.base.BaseFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LifeCategoryFragment : BaseFragment() {

    lateinit var binding: FragmentLifeCategoryBinding

    val mSmallCategoryList = ArrayList<SmallCategoryData>()

    val mProductList = ArrayList<ProductData>()

    private lateinit var mCategoryRecyclerViewAdapter: CategoryRecyclerViewAdapter

    lateinit var mParticularCategoryRecyclerAdapter: ParticularCategoryRecyclerViewAdapter

    private var mLargeCategoryId = 3

    var mClickedSmallCategoryNum = 11

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    override fun setupEvents() {}

    override fun setValues() {
        getCategoryFromServer()
        mCategoryRecyclerViewAdapter = CategoryRecyclerViewAdapter(mSmallCategoryList)

        getProductFromServer()
        mParticularCategoryRecyclerAdapter = ParticularCategoryRecyclerViewAdapter(mProductList)

        binding.rvProduct.apply {
            adapter = mParticularCategoryRecyclerAdapter
            layoutManager = LinearLayoutManager(mContext)
        }
    }

    fun getProductFromServer() {
        apiService.getRequestSmallCategory(mClickedSmallCategoryNum).enqueue(object :
            Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val basicResponse = response.body()!!
                    mProductList.clear()
                    mProductList.addAll(basicResponse.data.products)
                    mParticularCategoryRecyclerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

    private fun getCategoryFromServer() {
        apiService.getRequestLargeCategory(mLargeCategoryId).enqueue(object :
            Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    binding.llCategory.removeAllViews()
                    val basicResponse = response.body()!!
                    mSmallCategoryList.clear()
                    mSmallCategoryList.addAll(basicResponse.data.smallCategories)

                    for (smallCategory in mSmallCategoryList) {
                        val view = LayoutInflater.from(mContext)
                            .inflate(R.layout.adapter_category, null)

                        val txtSmallCategoryList =
                            view.findViewById<TextView>(R.id.txtCategory)
                        txtSmallCategoryList.text = smallCategory.name

                        view.setOnClickListener {
                            mClickedSmallCategoryNum = smallCategory.id
                            getProductFromServer()
                        }

                        binding.llCategory.addView(view)
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

}