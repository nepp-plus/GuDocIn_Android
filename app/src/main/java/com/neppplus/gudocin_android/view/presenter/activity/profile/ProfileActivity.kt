package com.neppplus.gudocin_android.view.presenter.activity.profile

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
import com.neppplus.gudocin_android.model.GlobalData
import com.neppplus.gudocin_android.util.context.ContextUtil
import com.neppplus.gudocin_android.util.uri.URIPathHelper
import com.neppplus.gudocin_android.view.presenter.activity.BaseActivity
import com.neppplus.gudocin_android.view.presenter.activity.main.MainActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileActivity : BaseActivity() {

  lateinit var binding: ActivityProfileBinding

  var isPasswordLengthOk = false

  var isDuplicatedOk = false

  private lateinit var resultLauncher: ActivityResultLauncher<Intent>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
    binding.view = this
    setupEvents()
    setValues()
  }

  override fun setupEvents() {
    binding.edtPassword.addTextChangedListener {
      if (it.toString().length >= 8) {
        binding.txtPasswordCheck.text = resources.getString(R.string.password_pass)
        isPasswordLengthOk = true
      } else {
        binding.txtPasswordCheck.text = resources.getString(R.string.password_length)
        isPasswordLengthOk = false
      }
    }

    binding.edtNicknameCheck.addTextChangedListener {
      binding.txtNicknameCheck.text = resources.getString(R.string.nickname_duplicated)
      isDuplicatedOk = false
    }
  }

  override fun setValues() {
    getInfoFromServer()
    profileChange()
    loginUserProvider()
    btnShopping.visibility = View.GONE
    btnCart.visibility = View.GONE
  }

  // 실제 파일 경로를 읽는 권한 필요 (업로드 가능해짐)
  fun checkPermission(view: View) {
    var permissionListener: PermissionListener = object : PermissionListener {
      override fun onPermissionGranted() {
        // (안드로이드 제공) 갤러리로 왕복 이동
        val myIntent = Intent()
        myIntent.action = Intent.ACTION_PICK
        myIntent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        resultLauncher.launch(myIntent)
      }

      override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
        Toast.makeText(mContext, resources.getString(R.string.gallery_permission_nothing), Toast.LENGTH_SHORT).show()
      }
    }
    TedPermission.create()
      .setPermissionListener(permissionListener)
      .setRationaleMessage(resources.getString(R.string.gallery_permission_need))
      .setDeniedMessage(resources.getString(R.string.gallery_permission_process))
      .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
      .check()
  }

  private fun profileChange() {
    resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
      if (it.resultCode == RESULT_OK) {
        val selectedImageUri = it.data?.data!!
        Log.d("Selected Image Uri", selectedImageUri.toString())
//        Uri -> 실제 첨부 가능한 파일로 변환 -> 실제 경로를 추출해서 Retrofit 에 첨부할 수 있게 됨
        val file = File(URIPathHelper().getPath(mContext, selectedImageUri))
//        파일을 Retrofit 에 첨부 가능한 RequestBody 형태로 가공
        val fileReqBody = RequestBody.create(MediaType.get("image/*"), file)
//        실제 첨부 데이터로 변경
        val body = MultipartBody.Part.createFormData("profile_image", "myFile.jpg", fileReqBody)

        apiService.putRequestProfile(body).enqueue(object : Callback<BasicResponse> {
          override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
            if (response.isSuccessful) {
              Toast.makeText(mContext, resources.getString(R.string.profile_change_success), Toast.LENGTH_SHORT).show()
//                사용자가 선택한 사진(selectedImageUri)을 프로필 ImageView 에 반영
              Glide.with(mContext).load(selectedImageUri).into(binding.imgProfile)
            } else {
              Toast.makeText(mContext, resources.getString(R.string.profile_change_failed), Toast.LENGTH_SHORT).show()
            }
          }

          override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
        })
      }
    }
  }

  fun passwordChange(view: View) {
    if (!isPasswordLengthOk) {
      Toast.makeText(mContext, resources.getString(R.string.password_length), Toast.LENGTH_SHORT).show()
      return
    }
    val password = binding.edtPassword.text.toString()
    val newPassword = binding.edtNewPassword.text.toString()

    apiService.patchRequestPassword("password", password, newPassword).enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          val br = response.body()!!
          Toast.makeText(mContext, resources.getString(R.string.password_change), Toast.LENGTH_SHORT).show()

          ContextUtil.setToken(mContext, br.data.token)
          GlobalData.loginUser = br.data.user
          startMain()
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
    })
  }

  fun phoneNumChange(view: View) {
    val inputPhoneNumber = binding.edtPhoneNum.text.toString()
    if (inputPhoneNumber.isEmpty()) {
      Toast.makeText(mContext, resources.getString(R.string.phone_num_input), Toast.LENGTH_SHORT).show()
      return
    }

    apiService.patchRequestPhoneNumber("phone", inputPhoneNumber).enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          val br = response.body()!!
          Toast.makeText(mContext, resources.getString(R.string.phone_num_change), Toast.LENGTH_SHORT).show()
          ContextUtil.setToken(mContext, br.data.token)
          GlobalData.loginUser = br.data.user
          startMain()
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
    })
  }

  fun nicknameCheck(view: View) {
    val nickname = binding.edtNicknameCheck.text.toString()
    if (nickname.isEmpty()) {
      Toast.makeText(mContext, resources.getString(R.string.nickname_input), Toast.LENGTH_SHORT).show()
      return
    }

    apiService.getRequestDuplicatedCheck("NICK_NAME", nickname).enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          binding.txtNicknameCheck.text = resources.getString(R.string.nickname_pass)
          isDuplicatedOk = true
        } else {
          binding.txtNicknameCheck.text = resources.getString(R.string.nickname_exist)
          isDuplicatedOk = false
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
    })
  }

  fun nicknameChange(view: View) {
    val inputNickname = binding.edtNickname.text.toString()
    if (inputNickname.isEmpty() || !isDuplicatedOk) {
      Toast.makeText(mContext, resources.getString(R.string.nickname_usable), Toast.LENGTH_SHORT).show()
      return
    }

    apiService.patchRequestNickname("nickname", inputNickname).enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          val br = response.body()!!
          Toast.makeText(mContext, resources.getString(R.string.nickname_change), Toast.LENGTH_SHORT).show()
          ContextUtil.setToken(mContext, br.data.token)
          GlobalData.loginUser = br.data.user
          startMain()
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
    })
  }

  private fun getInfoFromServer() {
    apiService.getRequestInfo().enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          val br = response.body()!!
          Glide.with(mContext).load(br.data.user.profileImageURL).into(binding.imgProfile)
          binding.txtNickname.text = br.data.user.nickname
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
    })
  }

  private fun loginUserProvider() {
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

  fun startMain() {
    val myIntent = Intent(mContext, MainActivity::class.java)
    startActivity(myIntent)
    finish()
  }

}