package com.neppplus.gudocin_android.ui.content.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentProductContentBinding
import com.neppplus.gudocin_android.ui.base.BaseFragment

class ProductContentFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding<FragmentProductContentBinding>(
            inflater,
            R.layout.fragment_product_content,
            container
        ).apply {
            fragment = this@ProductContentFragment
        }.root
    }

}