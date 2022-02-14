package com.neppplus.gudoc_in.fragments.contents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neppplus.gudoc_in.R
import com.neppplus.gudoc_in.databinding.FragmentStoreContentBinding
import com.neppplus.gudoc_in.datas.StoreData
import com.neppplus.gudoc_in.fragments.BaseFragment

class StoreContentFragment(mStoreData: StoreData) : BaseFragment() {

    lateinit var binding: FragmentStoreContentBinding

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

    }

}