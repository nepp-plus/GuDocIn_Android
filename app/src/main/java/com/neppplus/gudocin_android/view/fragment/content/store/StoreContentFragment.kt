package com.neppplus.gudocin_android.view.fragment.content.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentStoreContentBinding
import com.neppplus.gudocin_android.model.store.StoreData
import com.neppplus.gudocin_android.view.fragment.BaseFragment

class StoreContentFragment(mStoreData: StoreData) : BaseFragment() {

  lateinit var binding: FragmentStoreContentBinding

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_content, container, false)
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    setupEvents()
    setValues()
  }

  override fun setupEvents() {}

  override fun setValues() {}

}