package com.neppplus.gudoc_in.fragments.contents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neppplus.gudoc_in.R
import com.neppplus.gudoc_in.databinding.FragmentProductContentBinding
import com.neppplus.gudoc_in.datas.ProductData
import com.neppplus.gudoc_in.fragments.BaseFragment

class ProductContentFragment(val mProductData: ProductData) : BaseFragment() {

    lateinit var binding: FragmentProductContentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_content, container, false)
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

    }

}