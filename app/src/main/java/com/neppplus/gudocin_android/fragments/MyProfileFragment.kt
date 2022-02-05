package com.neppplus.gudocin_android.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.*
import com.neppplus.gudocin_android.databinding.FragmentMyProfileBinding
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.utils.ContextUtil

class MyProfileFragment : BaseFragment() {

    lateinit var binding: FragmentMyProfileBinding

    val REQ_FOR_GALLERY = 1000

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.txtLogOut.setOnClickListener {
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("로그아웃 확인")
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                ContextUtil.setToken(mContext, "")

                val myIntent = Intent(mContext, InitialActivity::class.java)
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(myIntent)
            })
            alert.setNegativeButton("취소", null)
            alert.show()
        }

        binding.txtMyCard.setOnClickListener {
            val myIntent = Intent(mContext, CardManagementListActivity::class.java)
            startActivity(myIntent)
        }

        binding.txtEditMyInfo.setOnClickListener {
            val myIntent = Intent(mContext, EditMyInfoActivity::class.java)
            startActivity(myIntent)
        }

        binding.txtPurchaseReviewList.setOnClickListener {
            val myIntent = Intent(mContext, PurchaseReviewListActivity::class.java)
            startActivity(myIntent)
        }

    }

    override fun setValues() {
        binding.txtUserName.text = GlobalData.loginUser!!.nickname
    }

}