package com.neppplus.gudocin_android.fragments

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.BannerViewPagerAdapter
import com.neppplus.gudocin_android.adapters.CategoriesAdapter
import com.neppplus.gudocin_android.adapters.ReviewRecyclerViewAdapterForMain
import com.neppplus.gudocin_android.databinding.BannerItemForMainBinding
import com.neppplus.gudocin_android.databinding.FragmentCategoriesBinding
import com.neppplus.gudocin_android.databinding.FragmentReviewListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.CategoriesData
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragment : BaseFragment() {

    lateinit var binding: FragmentCategoriesBinding

//    var mIsCategoryClicked = true

    var mSmallCategoriesList = ArrayList<CategoriesData>()
    lateinit var mSmallcateoriesAdapter : CategoriesAdapter



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

            getCategoryListFromServer()

            mSmallcateoriesAdapter = CategoriesAdapter(mContext,R.layout.small_categories_item,mSmallCategoriesList)
            binding.smallcategoryList.adapter = mSmallcateoriesAdapter



        }



    }

    fun getCategoryListFromServer(){
        apiService.getRequestSmallCategory().enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful ){

                    mSmallCategoriesList.clear()
                    mSmallCategoriesList.addAll(response.body()!!.data.categiries)
                    mSmallcateoriesAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })


    }

    override fun setValues() {

    }


}

