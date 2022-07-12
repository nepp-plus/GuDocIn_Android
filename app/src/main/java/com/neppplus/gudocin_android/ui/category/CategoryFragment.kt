package com.neppplus.gudocin_android.ui.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentCategoryBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.category.SmallCategoryData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseFragment
import com.neppplus.gudocin_android.ui.home.HomeFragment
import com.neppplus.gudocin_android.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryFragment : BaseFragment() {

    lateinit var retrofitService: RetrofitService

    private var mLargeCategoryId = 1

    private var mSmallCategoryNum = 1

    val mSmallCategoryList = ArrayList<SmallCategoryData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding<FragmentCategoryBinding>(inflater, R.layout.fragment_category, container).apply {
        retrofitService = Retrofit.getRetrofit(requireContext()).create(RetrofitService::class.java)
        fragment = this@CategoryFragment
        categoryListener()
        initView()
    }.root

    private fun FragmentCategoryBinding.categoryListener() {
        val onClickListener = View.OnClickListener { view ->
            when (view) {
                imgFoodCategory -> {
                    mLargeCategoryId = 1
                    getRequestLargeCategory()
                }

                imgClothesCategory -> {
                    mLargeCategoryId = 2
                    getRequestLargeCategory()
                }

                imgLifeCategory -> {
                    mLargeCategoryId = 3
                    getRequestLargeCategory()
                }
            }
        }
        imgFoodCategory.setOnClickListener(onClickListener)
        imgClothesCategory.setOnClickListener(onClickListener)
        imgLifeCategory.setOnClickListener(onClickListener)
    }

    private fun FragmentCategoryBinding.setCategoryImage() {
        Glide.with(requireContext()).load(R.drawable.food_category_icon).into(imgFoodCategory)
        Glide.with(requireContext()).load(R.drawable.clothes_category_icon).into(imgClothesCategory)
        Glide.with(requireContext()).load(R.drawable.life_category_icon).into(imgLifeCategory)
    }

    private fun FragmentCategoryBinding.initView() {
        getRequestLargeCategory()
        setCategoryImage()
    }

    private fun FragmentCategoryBinding.getCategoryLayout() {
        llCategory.removeAllViews()
        for (category in mSmallCategoryList) {
            val view = LayoutInflater.from(requireContext())
                .inflate(R.layout.adapter_category, null)

            val txtSmallCategory = view.findViewById<TextView>(R.id.txtCategory)
            txtSmallCategory.text = category.name

            view.setOnClickListener {
                mSmallCategoryNum = category.id

                val homeFragment = ((requireContext() as MainActivity)
                    .binding.viewPager.adapter as MainActivity.ViewPagerAdapter)
                    .getFragment(0) as HomeFragment

                homeFragment.getRequestSmallCategoryReview(mSmallCategoryNum)
            }
            llCategory.addView(view)
        }
    }

    private fun FragmentCategoryBinding.getRequestLargeCategory() {
        retrofitService.getRequestLargeCategory(mLargeCategoryId)
            .enqueue(object : Callback<BasicResponse> {
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