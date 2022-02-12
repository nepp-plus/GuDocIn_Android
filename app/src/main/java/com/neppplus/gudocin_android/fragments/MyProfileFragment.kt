package com.neppplus.gudocin_android.fragments

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.gudocin_android.EditProfileActivity
import com.neppplus.gudocin_android.InitialActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.SubscriptionHistoryActivity
import com.neppplus.gudocin_android.databinding.FragmentMyProfileBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.utils.ContextUtil
import com.neppplus.gudocin_android.utils.URIPathHelper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_FOR_GALLERY) {
            if (resultCode == Activity.RESULT_OK) {
                val selectedImageUri = data?.data!!
                Log.d("selectedImageUri", selectedImageUri.toString())
//                Uri -> 실제 첨부 가능한 파일로 변환 -> 실제 경로를 추출해서 Retrofit 에 첨부할 수 있게 됨
                val file = File(URIPathHelper().getPath(mContext, selectedImageUri))
//                파일을 Retrofit 에 첨부 가능한 RequestBody 형태로 가공
                val fileReqBody = RequestBody.create(MediaType.get("image/*"), file)
//                실제 첨부 데이터로 변경
                val body =
                    MultipartBody.Part.createFormData("profile_image", "myFile.jpg", fileReqBody)
                apiService.putRequestProfileImg(body).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(mContext, "프로필 사진이 변경되었습니다", Toast.LENGTH_SHORT).show()
                            //                            사용자가 선택한 사진(selectedImageUri)을 프로필 ImageView 에 반영
                            Glide.with(mContext).load(selectedImageUri).into(binding.imgProfile)
                        } else {
                            Toast.makeText(mContext, "프로필 사진 변경에 실패했습니다", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            }
        }
    }

    override fun setupEvents() {

        binding.imgProfile.setOnClickListener {
//            실제 파일 경로를 읽는 권한 필요 (업로드 가능해짐)
            val pl = object : PermissionListener {
                override fun onPermissionGranted() {
//            갤러리(안드로이드 제공)로 이동 (왕복 이동)
                    val myIntent = Intent()
                    myIntent.action = Intent.ACTION_PICK
                    myIntent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
                    startActivityForResult(myIntent, REQ_FOR_GALLERY)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(mContext, "갤러리 조회 권한이 없습니다", Toast.LENGTH_SHORT).show()
                }
            }
            TedPermission.create()
                .setPermissionListener(pl)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()
        }

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

        binding.txtEditMyInfo.setOnClickListener {
            val myIntent = Intent(mContext, EditProfileActivity::class.java)
            startActivity(myIntent)
        }

        binding.txtPurchaseReviewList.setOnClickListener {
            val myIntent = Intent(mContext, SubscriptionHistoryActivity::class.java)
            startActivity(myIntent)
        }

    }

    override fun setValues() {
        getMyInfoFromServer()

            when (GlobalData.loginUser!!.provider) {
                "kakao" -> {
                    binding.imgProvider.setImageResource(R.drawable.kakao_logo)
                    binding.imgProvider.visibility = View.VISIBLE
                }
                "facebook" -> {
                    binding.imgProvider.setImageResource(R.drawable.facebook_logo)
                    binding.imgProvider.visibility = View.VISIBLE
                }
                "google" -> {
                    binding.imgProvider.setImageResource(R.drawable.google_logo)
                    binding.imgProvider.visibility = View.VISIBLE
                }
                else -> {
                    binding.imgProvider.visibility = View.GONE
                }
            }
    }

    fun getMyInfoFromServer() {
        apiService.getRequestMyInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    binding.txtNickname.text = br.data.user.nickname
                    Glide.with(mContext).load(br.data.user.profileImageURL).into(binding.imgProfile)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}