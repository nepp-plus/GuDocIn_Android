package com.neppplus.gudocin_android.ui.profile

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityProfileBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.user.GlobalData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.util.ContextUtil
import com.neppplus.gudocin_android.util.URIPathHelper
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.ui.main.MainActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileActivity : BaseActivity() {

  lateinit var binding: ActivityProfileBinding

  lateinit var retrofitService: RetrofitService

  private var isPasswordLengthOk = false

  var isDuplicatedOk = false

  private lateinit var resultLauncher: ActivityResultLauncher<Intent>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
    binding.apply {
      retrofitService = Retrofit.getRetrofit(this@ProfileActivity).create(RetrofitService::class.java)
      activity = this@ProfileActivity
      initView()
    }
  }

  private fun ActivityProfileBinding.initView() {
    passwordChangedListener()
    nicknameChangedListener()
    getRequestInfo()
    profileChange()
    loginUserProvider()
    actionBarVisibility()
  }

  private fun ActivityProfileBinding.passwordChangedListener() {
    edtPassword.addTextChangedListener {
      if (it.toString().length >= 8) {
        txtPasswordCheck.text = resources.getString(R.string.password_pass)
        isPasswordLengthOk = true
      } else {
        txtPasswordCheck.text = resources.getString(R.string.password_length)
        isPasswordLengthOk = false
      }
    }
  }

  private fun ActivityProfileBinding.nicknameChangedListener() {
    edtNicknameCheck.addTextChangedListener {
      txtNicknameCheck.text = resources.getString(R.string.nickname_duplicated)
      isDuplicatedOk = false
    }
  }

  private fun actionBarVisibility() {
    shopping.visibility = View.GONE
    cart.visibility = View.GONE
  }

  // 실제 파일 경로 읽는 권한 필요 (업로드 가능)
  fun checkPermission() {
    val permissionListener: PermissionListener = object : PermissionListener {
      override fun onPermissionGranted() {
        // 갤러리 왕복 이동
        val intent = Intent()
        intent.action = Intent.ACTION_PICK
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        resultLauncher.launch(intent)
      }

      override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
        Toast.makeText(this@ProfileActivity, resources.getString(R.string.gallery_permission_nothing), Toast.LENGTH_SHORT).show()
      }
    }

    TedPermission.create()
      .setPermissionListener(permissionListener)
      .setRationaleMessage(resources.getString(R.string.gallery_permission_need))
      .setDeniedMessage(resources.getString(R.string.gallery_permission_process))
      .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
      .check()
  }

  private fun ActivityProfileBinding.profileChange() {
    resultLauncher =
      registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == RESULT_OK) {
          val selectedImageUri = it.data?.data!!
          Log.d("Selected Image Uri", selectedImageUri.toString())

          // Uri -> 실제 첨부 가능 파일 변환 -> 실제 경로 추출 통해 Retrofit 첨부 가능
          val file = File(
            URIPathHelper().getPath(this@ProfileActivity, selectedImageUri).toString()
          )

          // Retrofit 첨부 가능한 RequestBody 형태 가공
          val fileReqBody = RequestBody.create(MediaType.get("image/*"), file)

          // 실제 첨부 데이터 변경
          val body = MultipartBody.Part.createFormData(
            "profile_image",
            "myFile.jpg",
            fileReqBody
          )

          retrofitService.putRequestProfile(body).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
              if (response.isSuccessful) {
                Toast.makeText(this@ProfileActivity, resources.getString(R.string.profile_change_success), Toast.LENGTH_SHORT).show()

                // 사용자 선택 사진 (selectedImageUri) 프로필 반영
                Glide.with(this@ProfileActivity).load(selectedImageUri).into(imgProfile)
              } else {
                Toast.makeText(this@ProfileActivity, resources.getString(R.string.profile_change_failed), Toast.LENGTH_SHORT).show()
              }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
              Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
          })
        }
      }
  }

  fun passwordChange() {
    if (!isPasswordLengthOk) {
      Toast.makeText(this@ProfileActivity, resources.getString(R.string.password_length), Toast.LENGTH_SHORT).show()
      return
    }
    val password = binding.edtPassword.text.toString()
    val newPassword = binding.edtNewPassword.text.toString()

    retrofitService.patchRequestPassword("password", password, newPassword)
      .enqueue(object : Callback<BasicResponse> {
        override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
          if (response.isSuccessful) {
            val basicResponse = response.body()!!
            Toast.makeText(this@ProfileActivity, resources.getString(R.string.password_change), Toast.LENGTH_SHORT).show()

            ContextUtil.setToken(this@ProfileActivity, basicResponse.data.token)
            GlobalData.loginUser = basicResponse.data.user

            startMain()
          }
        }

        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
          Log.d("onFailure", resources.getString(R.string.data_loading_failed))
        }
      })
  }

  fun phoneNumChange() {
    val inputPhoneNumber = binding.edtPhoneNum.text.toString()
    if (inputPhoneNumber.isEmpty()) {
      Toast.makeText(this@ProfileActivity, resources.getString(R.string.phone_num_input), Toast.LENGTH_SHORT).show()
      return
    }

    retrofitService.patchRequestPhoneNumber("phone", inputPhoneNumber)
      .enqueue(object : Callback<BasicResponse> {
        override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
          if (response.isSuccessful) {
            val basicResponse = response.body()!!
            Toast.makeText(this@ProfileActivity, resources.getString(R.string.phone_num_change), Toast.LENGTH_SHORT).show()

            ContextUtil.setToken(this@ProfileActivity, basicResponse.data.token)
            GlobalData.loginUser = basicResponse.data.user

            startMain()
          }
        }

        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
          Log.d("onFailure", resources.getString(R.string.data_loading_failed))
        }
      })
  }

  fun nicknameCheck() {
    val nickname = binding.edtNicknameCheck.text.toString()
    if (nickname.isEmpty()) {
      Toast.makeText(this@ProfileActivity, resources.getString(R.string.nickname_input), Toast.LENGTH_SHORT).show()
      return
    }

    retrofitService.getRequestDuplicateCheck("NICK_NAME", nickname)
      .enqueue(object : Callback<BasicResponse> {
        override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
          if (response.isSuccessful) {
            binding.txtNicknameCheck.text = resources.getString(R.string.nickname_pass)
            isDuplicatedOk = true
          } else {
            binding.txtNicknameCheck.text = resources.getString(R.string.nickname_exist)
            isDuplicatedOk = false
          }
        }

        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
          Log.d("onFailure", resources.getString(R.string.data_loading_failed))
        }
      })
  }

  fun nicknameChange() {
    val inputNickname = binding.edtNickname.text.toString()
    if (inputNickname.isEmpty() || !isDuplicatedOk) {
      Toast.makeText(this@ProfileActivity, resources.getString(R.string.nickname_usable),
        Toast.LENGTH_SHORT).show()
      return
    }

    retrofitService.patchRequestNickname("nickname", inputNickname)
      .enqueue(object : Callback<BasicResponse> {
        override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
          if (response.isSuccessful) {
            val basicResponse = response.body()!!
            Toast.makeText(this@ProfileActivity, resources.getString(R.string.nickname_change), Toast.LENGTH_SHORT).show()

            ContextUtil.setToken(this@ProfileActivity, basicResponse.data.token)
            GlobalData.loginUser = basicResponse.data.user

            startMain()
          }
        }

        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
          Log.d("onFailure", resources.getString(R.string.data_loading_failed))
        }
      })
  }

  private fun ActivityProfileBinding.getRequestInfo() {
    retrofitService.getRequestInfo().enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          val basicResponse = response.body()!!
          Glide.with(this@ProfileActivity).load(basicResponse.data.user.profileImageURL).into(imgProfile)
          txtNickname.text = basicResponse.data.user.nickname
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
        Log.d("onFailure", resources.getString(R.string.data_loading_failed))
      }
    })
  }

  private fun ActivityProfileBinding.loginUserProvider() {
    when (GlobalData.loginUser!!.provider) {
      "kakao" -> {
        imgProvider.apply {
          setImageResource(R.drawable.kakao_logo)
          visibility = View.VISIBLE
        }
      }

      "facebook" -> {
        imgProvider.apply {
          setImageResource(R.drawable.facebook_logo)
          visibility = View.VISIBLE
        }
      }

      "google" -> {
        imgProvider.apply {
          setImageResource(R.drawable.google_logo)
          visibility = View.VISIBLE
        }
      }

      else -> {
        imgProvider.visibility = View.GONE
      }
    }
  }

  fun startMain() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
  }

}