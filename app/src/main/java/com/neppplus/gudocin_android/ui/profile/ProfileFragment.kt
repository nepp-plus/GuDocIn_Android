package com.neppplus.gudocin_android.ui.profile

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
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentProfileBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.user.GlobalData
import com.neppplus.gudocin_android.util.Context
import com.neppplus.gudocin_android.util.URIPathHelper
import com.neppplus.gudocin_android.ui.init.InitActivity
import com.neppplus.gudocin_android.ui.subscription.SubscriptionActivity
import com.neppplus.gudocin_android.ui.base.BaseFragment
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileFragment : BaseFragment() {

  lateinit var binding: FragmentProfileBinding

  val REQ_FOR_GALLERY = 1000

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
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
        // Uri -> 실제 첨부 가능한 파일로 변환 -> 실제 경로를 추출해서 Retrofit 에 첨부할 수 있게 됨
        val file = File(URIPathHelper().getPath(mContext, selectedImageUri))
        // 파일을 Retrofit 에 첨부 가능한 RequestBody 형태로 가공
        val fileReqBody = RequestBody.create(MediaType.get("image/*"), file)
        // 실제 첨부 데이터로 변경
        val body = MultipartBody.Part.createFormData("profile_image", "myFile.jpg", fileReqBody)

        apiService.putRequestProfile(body).enqueue(object : Callback<BasicResponse> {
          override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
            if (response.isSuccessful) {
              Toast.makeText(mContext, resources.getString(R.string.profile_change_success), Toast.LENGTH_SHORT).show()
              // 사용자가 선택한 사진(selectedImageUri)을 프로필 ImageView 에 반영
              Glide.with(mContext).load(selectedImageUri).into(binding.imgProfile)
            } else {
              Toast.makeText(mContext, resources.getString(R.string.profile_change_failed), Toast.LENGTH_SHORT)
                .show()
            }
          }

          override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
        })
      }
    }
  }

  override fun setupEvents() {
    binding.imgProfile.setOnClickListener {
      // 실제 파일 경로를 읽는 권한 필요 (업로드 가능해짐)
      val pl = object : PermissionListener {
        override fun onPermissionGranted() {
          // 갤러리(안드로이드 제공)로 이동 (왕복 이동)
          val myIntent = Intent()
          myIntent.action = Intent.ACTION_PICK
          myIntent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
          startActivityForResult(myIntent, REQ_FOR_GALLERY)
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
          Toast.makeText(mContext, resources.getString(R.string.gallery_permission_nothing), Toast.LENGTH_SHORT).show()
        }
      }
      TedPermission.create()
        .setPermissionListener(pl)
        .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
        .check()
    }

    binding.txtLogOut.setOnClickListener {
      val alert = AlertDialog.Builder(mContext)
      alert.setTitle(resources.getString(R.string.logout_confirm))
      alert.setMessage(resources.getString(R.string.do_you_wanna_logout))
      alert.setPositiveButton(resources.getString(R.string.confirm), DialogInterface.OnClickListener { _, _ ->
        Context.setToken(mContext, "")

        val myIntent = Intent(mContext, InitActivity::class.java)
        myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(myIntent)
      })
      alert.setNegativeButton(resources.getString(R.string.cancel), null)
      alert.show()
    }

    binding.txtEditInfo.setOnClickListener {
      val myIntent = Intent(mContext, ProfileActivity::class.java)
      startActivity(myIntent)
    }

    binding.txtHistory.setOnClickListener {
      val myIntent = Intent(mContext, SubscriptionActivity::class.java)
      startActivity(myIntent)
    }
  }

  override fun setValues() {
    getInfoFromServer()

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

  private fun getInfoFromServer() {
    apiService.getRequestInfo().enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          val br = response.body()!!
          binding.txtNickname.text = br.data.user.nickname
          Glide.with(mContext).load(br.data.user.profileImageURL).into(binding.imgProfile)
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
    })
  }

}