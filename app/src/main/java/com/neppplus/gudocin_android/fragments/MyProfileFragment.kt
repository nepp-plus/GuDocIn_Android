package com.neppplus.gudocin_android.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.*
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
        binding.txtMyProductPurnchaseList.setOnClickListener {

            val myIntent =  Intent( mContext, MyPurchaseListActivity::class.java )

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


        binding.txtguide.setOnClickListener {

            val myIntent = Intent(mContext, CustomerCenterActivity::class.java)

            startActivity(myIntent)

        }
        binding.txtnotice.setOnClickListener {

            val myIntent = Intent(mContext, CustomerCenterActivity::class.java)

            startActivity(myIntent)
        }

        binding.txtquestions.setOnClickListener {

            val myIntent = Intent(mContext, CustomerCenterActivity::class.java)

            startActivity(myIntent)
        }

        binding.txttermsofuse.setOnClickListener {

            val myIntent = Intent(mContext, CustomerCenterActivity::class.java)

            startActivity(myIntent)

        }



    }

    override fun setValues() {

    }
}