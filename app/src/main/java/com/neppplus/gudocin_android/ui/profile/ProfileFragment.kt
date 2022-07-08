package com.neppplus.gudocin_android.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : BaseFragment() {

    lateinit var binding: FragmentProfileBinding

    lateinit var retrofitService: RetrofitService

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
        profileInfoListener()
        loginUserProvider()
        getRequestInfo()
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