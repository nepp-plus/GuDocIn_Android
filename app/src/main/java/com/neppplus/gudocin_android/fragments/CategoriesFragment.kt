package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.NavigationActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.ProductRecyclerViewAdapter
import com.neppplus.gudocin_android.adapters.SmallCategoriesListAdapter
import com.neppplus.gudocin_android.databinding.FragmentCategoriesBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.datas.SmallCategoriesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragment : BaseFragment() {

    lateinit var binding: FragmentCategoriesBinding

    var mSmallCategoriesList = ArrayList<SmallCategoriesData>()
    var mLargeCategoryId = 2
    var mClickedSmallCategoryNum = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_categories,container,false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.btnCategoriesEat.setOnClickListener {
            mLargeCategoryId = 2
            getSmallCategoryListFromServer()

        }

        binding.btnCategoriesLife.setOnClickListener {
            mLargeCategoryId = 3
            getSmallCategoryListFromServer()

        }


        binding.btnCategoriesWear.setOnClickListener {
            mLargeCategoryId = 1
            getSmallCategoryListFromServer()

        }

    }

    override fun setValues() {
        getSmallCategoryListFromServer()
    }


    fun getSmallCategoryListFromServer(){
        apiService.getRequestSmallCategoryDependOnLarge(mLargeCategoryId).enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful ){



                    val br = response.body()!!

                    mSmallCategoriesList.clear()
                    mSmallCategoriesList.addAll(br.data.small_categories)

                    //                    추가한 카테고리 하나하나에 대한 view 생성
                    binding.smalllCategoryList.removeAllViews()
                    for (sc in mSmallCategoriesList){
                        val view = LayoutInflater.from(mContext).inflate(R.layout.small_categories_item,null)
                        val txtSmallCategoryName = view.findViewById<TextView>(R.id.txtSmallCategoryName)

                        txtSmallCategoryName.text = sc.name

                        view.setOnClickListener {
                            mClickedSmallCategoryNum = sc.id
//                            네비게이션 액티비티 -> 홈프레그먼트
                            val homeFragment = ((requireContext() as NavigationActivity).binding.viewPager.adapter as NavigationActivity.ViewPagerAdapter).getFragment(0)as HomeFragment
                            homeFragment.getReviewListInSmallCategoryFromServer(mClickedSmallCategoryNum)
                        }

                        binding.smalllCategoryList.addView(view)


                    }


                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })


    }




}

