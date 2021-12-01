package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.SmallCategoriesListAdapter
import com.neppplus.gudocin_android.databinding.FragmentCategoriesBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.SmallCategoriesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragment : BaseFragment() {

    lateinit var binding: FragmentCategoriesBinding

//    var mIsCategoryClicked = true

    var mSmallCategoriesList = ArrayList<SmallCategoriesData>()
    lateinit var mSmallcateoriesListAdapter : SmallCategoriesListAdapter
    var mLargeCategoryId = 1



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

        binding.btnCategriesEat.setOnClickListener {
            mLargeCategoryId = 1


        }

        binding.btnCategriesLife.setOnClickListener {
            mLargeCategoryId = 3


        }


        binding.btnCategriesWear.setOnClickListener {
            mLargeCategoryId = 2


        }



    }



    override fun setValues() {
        getSmallCategoryListFromServer()
        mSmallcateoriesListAdapter = SmallCategoriesListAdapter(mContext,mSmallCategoriesList)
        binding.smallcategoryRecyclerView.adapter = mSmallcateoriesListAdapter
        binding.smallcategoryRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }


    fun getSmallCategoryListFromServer(){
        apiService.getRequestSmallCategoryDependOnLarge(mLargeCategoryId).enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful ){

                    mSmallCategoriesList.clear()
                    mSmallCategoriesList.addAll(response.body()!!.data.small_categories)
                    mSmallcateoriesListAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })


    }


}

