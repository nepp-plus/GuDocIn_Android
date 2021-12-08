package com.neppplus.gudocin_android.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ReviewDetailActivity
import com.neppplus.gudocin_android.adapters.ProductContentViewPagerAdapter
import com.neppplus.gudocin_android.databinding.FragmentStoreContentBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreContentFragment : BaseFragment() {

    lateinit var binding : FragmentStoreContentBinding
    lateinit var mProductData :ProductData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_store_content,container,false)
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

            fun bind(data: ProductData) {
            binding.txtStoreName.text = data.store.name
            Glide.with(mContext).load(data.store.logoUrl).into(binding.imgStoreUrl)
        }

    }


}