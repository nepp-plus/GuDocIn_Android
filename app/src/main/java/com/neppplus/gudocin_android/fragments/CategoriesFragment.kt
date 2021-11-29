package com.neppplus.gudocin_android.fragments

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.BannerViewPagerAdapter
import com.neppplus.gudocin_android.adapters.ReviewRecyclerViewAdapterForMain
import com.neppplus.gudocin_android.databinding.BannerItemForMainBinding
import com.neppplus.gudocin_android.databinding.FragmentCategoriesBinding
import com.neppplus.gudocin_android.databinding.FragmentReviewListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragment : BaseFragment() {

    lateinit var binding: FragmentCategoriesBinding

    var isCategoryClicked = true



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
             val tempSmallcategories = ArrayList<CategoriesData>

//    val tagBox = LayoutInflater.from(mContext).inflate(R.layout.tag_list_item,null)
//                val txtTag = tagBox.findViewById<TextView>(R.id.txtTag)
//                txtTag.text = "#${tag}"

        }



    }

    override fun setValues() {

    }


}

