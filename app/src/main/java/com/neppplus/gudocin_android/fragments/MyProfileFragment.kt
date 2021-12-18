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

//      BaseFragment 상속
class MyProfileFragment : BaseFragment() {


    //    xml에 <layout>으로 묶어주므로 바인딩 가능
    lateinit var binding: FragmentMyProfileBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        바인딩 변수 사용. xml 화면을 바인딩 변수에 대입시키고 바인딩 된 마지막 화면으로 반환.
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        return binding.root


    }

    //  화면이 시작될때 ocActivityCreated
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//    override 한 두개의 함수를 사용한다고 정의
        setValues()
        setupEvents()

    }

    //    setupEvents함수 오버로딩
    override fun setupEvents() {






        binding.btnMyProductPurchase.setOnClickListener {

            val myIntent = Intent(mContext, MyPurchaseListActivity::class.java)
            startActivity(myIntent)


        }


        binding.txtLogOut.setOnClickListener {

//            진짜 로그아웃 할건지? 확인.

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("로그아웃 확인")
            alert.setMessage("정말 로그아웃 하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

//                실제 로그아웃 처리
//                실제 로그아웃 처리 => 저장된 토큰을 "" 으로 돌려주자. (내 폰에 저장된 토큰 삭제)

                ContextUtil.setToken(mContext, "")

//                화면 종료 -> (열려있는 모든 화면을 전부 닫고)  SplashActivity로 이동.

                val myIntent = Intent(mContext, ApproachActivity::class.java)
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(myIntent)

            })
            alert.setNegativeButton("취소", null)

            alert.show()


        }




        binding.txtMyCard.setOnClickListener {

            val myIntent = Intent(mContext, RegistryCardListActivity::class.java)
            startActivity(myIntent)

        }


//     txtEditMyInfo 를 클릭했을 경우
        binding.txtEditMyInfo.setOnClickListener {

//            Intent함수를 통해 현재 화면에서 MyInfoEditActivity 화면으로 넘어감
            val myIntent = Intent(mContext, MyInfoEditActivity::class.java)

            startActivity(myIntent)
        }
//       btnSaveMoney 클릭했을 경우
        binding.btnSaveMoney.setOnClickListener {

//            Intent함수를 통해 현재 화면에서 SaveMonyMyActivity 화면으로 넘어감
            val myIntent = Intent(mContext, MySaveMoneyDetailActivity::class.java)

            startActivity(myIntent)

        }

//        txtMyProductPurnchaseList 클릭했을 경우
        binding.txtMyProductPurnchaseList.setOnClickListener {

//            Intent 함수를 통해 현재 화면에서 MyPurchaseListActivity 화면으로 넘어감
            val myIntent = Intent(mContext, MyPurchaseListActivity::class.java)

            startActivity(myIntent)

        }

//       txtUerPointDetails 클릭 했을 경우
        binding.txtUserPointDetails.setOnClickListener {

//            Intent 함수를 통해 현재 화면에서 SaveMoneyMyActivity 화면으로 넘어감
            val myIntent = Intent(mContext, MySaveMoneyDetailActivity::class.java)

            startActivity(myIntent)

        }

//        txtguide 클릭 했을 경우
        binding.txtguide.setOnClickListener {

//            Intent 함수를 통해 현재 화면에서 CustomerCenterActivity 화면으로 넘어감
            val myIntent = Intent(mContext, CustomerCenterActivity::class.java)

            startActivity(myIntent)

        }

//        txtnotice 클릭 했을 경우
        binding.txtnotice.setOnClickListener {

//            Intent 함수를 통해 현재 화면에서 CustomerCenterActivity 화면으로 넘어감
            val myIntent = Intent(mContext, CustomerCenterActivity::class.java)

            startActivity(myIntent)
        }

//        txtquestions 클릭 했을 경우
        binding.txtquestions.setOnClickListener {

//            Intent 함수를 통해 현재 화면에서 CustomerCenterActivity 화면으로 넘어감
            val myIntent = Intent(mContext, CustomerCenterActivity::class.java)

            startActivity(myIntent)
        }

//        txttermsofuse 클릭했을 경우
        binding.txttermsofuse.setOnClickListener {

//            Intent 함수를 통해 현재 화면에서 CustomerCenterActivity 화면으로 넘어감
            val myIntent = Intent(mContext, CustomerCenterActivity::class.java)

            startActivity(myIntent)

        }


    }

    override fun setValues() {

        binding.txtUserName.text = GlobalData.loginUser!!.nickname


    }
}