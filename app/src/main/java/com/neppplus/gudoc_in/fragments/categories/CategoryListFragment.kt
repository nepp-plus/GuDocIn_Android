package com.neppplus.gudoc_in.fragments.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.neppplus.gudoc_in.activities.MainActivity
import com.neppplus.gudoc_in.R
import com.neppplus.gudoc_in.databinding.FragmentCategoryListForMainBinding
import com.neppplus.gudoc_in.datas.BasicResponse
import com.neppplus.gudoc_in.datas.SmallCategoryData
import com.neppplus.gudoc_in.fragments.BaseFragment
import com.neppplus.gudoc_in.fragments.MainFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryListFragment : BaseFragment() {

    lateinit var binding: FragmentCategoryListForMainBinding

    var mSmallCategoriesList = ArrayList<SmallCategoryData>()

    var mLargeCategoryId = 1
    var mClickedSmallCategoryNum = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_category_list_for_main,
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
        binding.imgFoodCategory.setOnClickListener {
            mLargeCategoryId = 1
            getSmallCategoryListFromServer()
        }
        binding.imgClothesCategory.setOnClickListener {
            mLargeCategoryId = 2
            getSmallCategoryListFromServer()
        }
        binding.imgLifeCategory.setOnClickListener {
            mLargeCategoryId = 3
            getSmallCategoryListFromServer()
        }
    }

    override fun setValues() {
        getSmallCategoryListFromServer()
    }

    fun getSmallCategoryListFromServer() {
        if (isInitialized) {
            apiService.getRequestSmallCategoryDependOnLarge(mLargeCategoryId)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            val br = response.body()!!
                            mSmallCategoriesList.clear()
                            mSmallCategoriesList.addAll(br.data.small_categories)

//                        추가한 카테고리 하나하나에 대한 view 생성
                            binding.layoutSmallCategoryList.removeAllViews()
                            for (sc in mSmallCategoriesList) {
                                val view = LayoutInflater.from(mContext)
                                    .inflate(R.layout.category_list_item_for_all, null)
                                val txtSmallCategoryList =
                                    view.findViewById<TextView>(R.id.txtSmallCategoryList)
                                txtSmallCategoryList.text = sc.name

                                view.setOnClickListener {
                                    mClickedSmallCategoryNum = sc.id
//                                    MainActivity -> HomeFragment
                                    val homeFragment =
                                        ((requireContext() as MainActivity).binding.viewPager.adapter as MainActivity.ViewPagerAdapter).getFragment(
                                            0
                                        ) as MainFragment
                                    homeFragment.getReviewListInSmallCategoryFromServer(
                                        mClickedSmallCategoryNum
                                    )
                                }
                                binding.layoutSmallCategoryList.addView(view)
                            }
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
        }
    }

}