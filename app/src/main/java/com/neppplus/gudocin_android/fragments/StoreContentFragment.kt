package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentStoreContentBinding
import com.neppplus.gudocin_android.datas.StoreData

class StoreContentFragment(mStoreData: StoreData) : BaseFragment() {

    lateinit var binding: FragmentStoreContentBinding
    lateinit var mStoreData: StoreData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_store_content, container, false)
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
        fun bind(data: StoreData) {
            binding.txtStoreName.text = mStoreData.name
            Glide.with(mContext).load(mStoreData.logoUrl).into(binding.imgStoreUrl)
        }
    }

}