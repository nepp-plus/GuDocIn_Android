package com.neppplus.gudocin_android.ui.category.clothes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentClothesCategoryBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.category.SmallCategoryData
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseFragment
import com.neppplus.gudocin_android.ui.category.ParticularCategoryRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ClothesCategoryFragment : BaseFragment() {

    lateinit var retrofitService: RetrofitService

    val mSmallCategoryList = ArrayList<SmallCategoryData>()

    val mProductList = ArrayList<ProductData>()

    lateinit var mParticularCategoryRecyclerAdapter: ParticularCategoryRecyclerViewAdapter

    private val mLargeCategoryId = 2

    private var mSmallCategoryNum = 6

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding<FragmentClothesCategoryBinding>(
        inflater,
        R.layout.fragment_clothes_category,
        container
    ).apply {
        fragment = this@ClothesCategoryFragment
        retrofitService = Retrofit.getRetrofit(requireContext()).create(RetrofitService::class.java)
        initView()
    }.root

    private fun FragmentClothesCategoryBinding.initView() {
        getRequestLargeCategory()
        setParticularCategoryRecyclerView()
        getRequestSmallCategory()
    }

    private fun FragmentClothesCategoryBinding.setParticularCategoryRecyclerView() {
        mParticularCategoryRecyclerAdapter = ParticularCategoryRecyclerViewAdapter(mProductList)

        rvProduct.apply {
            adapter = mParticularCategoryRecyclerAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun getRequestSmallCategory() {
        retrofitService.getRequestSmallCategory(mSmallCategoryNum).enqueue(object :
            Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val basicResponse = response.body()!!
                    mProductList.apply {
                        clear()
                        addAll(basicResponse.data.products)
                    }
                    mParticularCategoryRecyclerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

    private fun FragmentClothesCategoryBinding.getCategoryLayout() {
        llCategory.removeAllViews()
        for (category in mSmallCategoryList) {
            val view =
                LayoutInflater.from(requireContext()).inflate(R.layout.adapter_category, null)

            val txtSmallCategory = view.findViewById<TextView>(R.id.txtCategory)
            txtSmallCategory.text = category.name

            view.setOnClickListener {
                mSmallCategoryNum = category.id
                getRequestSmallCategory()
            }
            llCategory.addView(view)
        }
    }

    private fun FragmentClothesCategoryBinding.getRequestLargeCategory() {
        retrofitService.getRequestLargeCategory(mLargeCategoryId).enqueue(object :
            Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val basicResponse = response.body()!!
                    mSmallCategoryList.apply {
                        clear()
                        addAll(basicResponse.data.smallCategories)
                    }
                    getCategoryLayout()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

}