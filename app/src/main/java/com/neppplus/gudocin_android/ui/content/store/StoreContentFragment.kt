package com.neppplus.gudocin_android.ui.content.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentStoreContentBinding
import com.neppplus.gudocin_android.ui.base.BaseFragment

class StoreContentFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding<FragmentStoreContentBinding>(
            inflater,
            R.layout.fragment_store_content,
            container
        ).apply {
            fragment = this@StoreContentFragment
        }.root
    }

}