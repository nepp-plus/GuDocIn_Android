package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentMyProfileBinding

class MyProfileFragment : BaseFragment() {

    lateinit var binding : FragmentMyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         onCreateView(inflater, container, savedInstanceState)
    }
    override fun setupEvents() {

    }

    override fun setValues() {

    }
}