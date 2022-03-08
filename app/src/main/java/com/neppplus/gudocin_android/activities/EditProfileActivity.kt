package com.neppplus.gudocin_android.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityEditProfileBinding
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

class EditProfileActivity : BaseActivity() {

    lateinit var binding: ActivityEditProfileBinding

    var isPasswordLengthOk = false

    var isDuplicatedOk = false

    val REQ_FOR_GALLERY = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
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
                            Toast.makeText(mContext, "프로필 사진 변경에 실패하였습니다", Toast.LENGTH_SHORT)
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
        binding.edtPassword.addTextChangedListener {
            if (it.toString().length >= 8) {
                binding.txtPasswordCheck.text = " 사용해도 좋은 비밀번호입니다"
                isPasswordLengthOk = true
            } else {
                binding.txtPasswordCheck.text = " 비밀번호는 8글자 이상이어야 합니다"
                isPasswordLengthOk = false
            }
        }

        binding.edtNicknameCheck.addTextChangedListener {
            binding.txtNicknameCheck.text = " 닉네임 중복 확인을 진행해야 합니다"
            isDuplicatedOk = false
        }

        binding.btnNicknameCheck.setOnClickListener {
            val nickname = binding.edtNicknameCheck.text.toString()
            if (nickname.isEmpty()) {
                Toast.makeText(mContext, "닉네임을 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            apiService.getRequestDuplicatedCheck("NICK_NAME", nickname)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            binding.txtNicknameCheck.text = " 사용해도 좋은 닉네임입니다"
                            isDuplicatedOk = true
                        } else {
                            binding.txtNicknameCheck.text = " 중복된 닉네임이 존재합니다"
                            isDuplicatedOk = false
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })

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
        }

        binding.btnPasswordChange.setOnClickListener {
            if (!isPasswordLengthOk) {
                Toast.makeText(mContext, "비밀번호는 8글자 이상이어야 합니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputPassword = binding.edtPassword.text.toString()
            val currentPassword = binding.edtCurrentPassword.text.toString()

            apiService.patchRequestEditPassword(
                "password", inputPassword, currentPassword
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        Toast.makeText(
                            mContext,
                            "비밀번호가 수정되었습니다",
                            Toast.LENGTH_SHORT
                        ).show()

                        ContextUtil.setToken(mContext, br.data.token)
                        GlobalData.loginUser = br.data.user

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
        }

        binding.btnPhoneNumChange.setOnClickListener {
            val inputPhoneNumber = binding.edtPhoneNum.text.toString()
            if (inputPhoneNumber.isEmpty()) {
                Toast.makeText(mContext, "전화번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            apiService.patchRequestEditPhoneNumber(
                "phone", inputPhoneNumber,
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        Toast.makeText(
                            mContext,
                            "전화번호가 수정되었습니다",
                            Toast.LENGTH_SHORT
                        ).show()

                        ContextUtil.setToken(mContext, br.data.token)
                        GlobalData.loginUser = br.data.user

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
        }

        binding.btnNicknameChange.setOnClickListener {
            val inputNickname = binding.edtNickname.text.toString()
            if (inputNickname.isEmpty() || !isDuplicatedOk) {
                Toast.makeText(mContext, "사용 가능한 닉네임을 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            apiService.patchRequestEditNickname(
                "nickname", inputNickname,
            ).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        Toast.makeText(
                            mContext,
                            "닉네임이 수정되었습니다",
                            Toast.LENGTH_SHORT
                        ).show()

                        ContextUtil.setToken(mContext, br.data.token)
                        GlobalData.loginUser = br.data.user

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
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
        btnExplore.visibility = View.GONE
        btnCart.visibility = View.GONE
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