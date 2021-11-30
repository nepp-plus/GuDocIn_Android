package com.neppplus.gudocin_android.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.MyProductPurchaseListActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.SaveMonyMyActivity
import com.neppplus.gudocin_android.UserEditActivity
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setValues()
        setupEvents()

    }

    override fun setupEvents() {

        binding.btnSaveMoney.setOnClickListener {

            val myIntent =  Intent( mContext, SaveMonyMyActivity::class.java )

            startActivity(myIntent)

        }

        binding.txtEditUserInfo.setOnClickListener {

            val myIntent = Intent(mContext, UserEditActivity::class.java)

            startActivity(myIntent)
        }

        binding.txtUserPointDetails.setOnClickListener {

            val myIntent =  Intent( mContext, SaveMonyMyActivity::class.java )

            startActivity(myIntent)

        }
        binding.txtMyProductPurnchaseList.setOnClickListener {

            val myIntent = Intent(mContext, MyProductPurchaseListActivity::class.java)

            startActivity(myIntent)
        }

        binding.

    }

    override fun setValues() {

    }
}