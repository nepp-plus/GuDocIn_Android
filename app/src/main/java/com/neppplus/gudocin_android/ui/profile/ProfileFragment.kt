package com.neppplus.gudocin_android.ui.profile

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.FragmentProfileBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.user.GlobalData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseFragment
import com.neppplus.gudocin_android.ui.init.InitActivity
import com.neppplus.gudocin_android.ui.subscription.SubscriptionActivity
import com.neppplus.gudocin_android.util.ContextUtil
import com.neppplus.gudocin_android.util.URIPathHelper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class ProfileFragment : BaseFragment() {

    lateinit var binding: FragmentProfileBinding

    lateinit var retrofitService: RetrofitService

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding<FragmentProfileBinding>(inflater, R.layout.fragment_profile, container).apply {
        retrofitService =
            Retrofit.getRetrofit(requireContext()).create(RetrofitService::class.java)
        fragment = this@ProfileFragment
        initView()
    }.root

    private fun FragmentProfileBinding.initView() {
        profileChange()
        profileInfoListener()
        loginUserProvider()
        getRequestInfo()
    }

    private fun FragmentProfileBinding.profileChange() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    val selectedImageUri = it.data?.data!!
                    Log.d("Selected Image Uri", selectedImageUri.toString())

                    // Uri -> 실제 첨부 가능 파일 변환 -> 실제 경로 추출 통해 Retrofit 첨부 가능
                    val file = File(
                        URIPathHelper().getPath(requireContext(), selectedImageUri).toString()
                    )

                    // Retrofit 첨부 가능한 RequestBody 형태 가공
                    val fileReqBody = RequestBody.create(MediaType.get("image/*"), file)

                    // 실제 첨부 데이터 변경
                    val body = MultipartBody.Part.createFormData(
                        "profile_image",
                        "myFile.jpg",
                        fileReqBody
                    )

                    retrofitService.putRequestProfile(body)
                        .enqueue(object : Callback<BasicResponse> {
                            override fun onResponse(
                                call: Call<BasicResponse>,
                                response: Response<BasicResponse>
                            ) {
                                if (response.isSuccessful) {
                                    Toast.makeText(
                                        requireContext(),
                                        resources.getString(R.string.profile_change_success),
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // 사용자 선택 사진 (selectedImageUri) 프로필 반영
                                    Glide.with(requireContext()).load(selectedImageUri)
                                        .into(imgProfile)
                                } else {
                                    Toast.makeText(
                                        requireContext(),
                                        resources.getString(R.string.profile_change_failed),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                                Log.d(
                                    "onFailure",
                                    resources.getString(R.string.data_loading_failed)
                                )
                            }
                        })
                }
            }
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
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.gallery_permission_nothing),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        TedPermission.create()
            .setPermissionListener(permissionListener)
            .setRationaleMessage(resources.getString(R.string.gallery_permission_need))
            .setDeniedMessage(resources.getString(R.string.gallery_permission_process))
            .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
            .check()
    }

    private fun FragmentProfileBinding.profileInfoListener() {
        val onClickListener = View.OnClickListener { view ->
            when (view) {
                txtLogOut -> {
                    val alert = AlertDialog.Builder(requireContext())
                    alert.setTitle(resources.getString(R.string.logout_confirm))
                    alert.setMessage(resources.getString(R.string.do_you_wanna_logout))
                    alert.setPositiveButton(
                        resources.getString(R.string.confirm)
                    ) { _, _ ->
                        ContextUtil.setToken(requireContext(), "")

                        val intent = Intent(requireContext(), InitActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    alert.setNegativeButton(resources.getString(R.string.cancel), null)
                    alert.show()
                }

                txtEditInfo -> startActivity(Intent(requireContext(), ProfileActivity::class.java))

                txtHistory -> startActivity(
                    Intent(requireContext(), SubscriptionActivity::class.java)
                )
            }
        }
        txtLogOut.setOnClickListener(onClickListener)
        txtEditInfo.setOnClickListener(onClickListener)
        txtHistory.setOnClickListener(onClickListener)
    }

    private fun FragmentProfileBinding.loginUserProvider() {
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

    private fun FragmentProfileBinding.getRequestInfo() {
        retrofitService.getRequestInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val basicResponse = response.body()!!
                    txtNickname.text = basicResponse.data.user.nickname
                    Glide.with(requireContext()).load(basicResponse.data.user.profileImageURL)
                        .into(imgProfile)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

}