package com.neppplus.gudocin_android.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.SaveMoneyMyActivity
import com.neppplus.gudocin_android.databinding.FragmentMyProfileBinding

class MyProfileFragment : BaseFragment() {

    lateinit var binding : FragmentMyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_my_profile,container,false)
        return binding.root
    }
    override fun setupEvents() {

        binding.btnSaveMoney.setOnClickListener {

            val myIntent =  Intent( mContext, SaveMoneyMyActivity::class.java )
            startActivity(myIntent)

        }

    }

    override fun setValues() {

    }
}