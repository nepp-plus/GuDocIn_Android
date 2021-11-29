package com.neppplus.gudocin_android

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.neppplus.gudocin_android.databinding.ActivityNavigationBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.fragments.HomeFragment
import com.neppplus.gudocin_android.fragments.MyProfileFragment
import com.neppplus.gudocin_android.fragments.RankingFragment
import com.neppplus.gudocin_android.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest

class NavigationActivity : BaseActivity() {

//    메인화면 - 김준기가 작업합니다.

    lateinit var binding: ActivityNavigationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_navigation)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {


        binding.bottomNavi.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.naviHome -> binding.viewPager.currentItem = 0
                R.id.naviRanking -> binding.viewPager.currentItem = 1
                else -> binding.viewPager.currentItem = 2
            }
            true
        }


        getAndSendDeviceToken()
        getKeyHash()
    }

    override fun setValues() {

        binding.viewPager.apply {
            adapter = ViewPagerAdapter(this@NavigationActivity)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.bottomNavi.selectedItemId = when (position) {
                        0 -> R.id.naviHome
                        1 -> R.id.naviRanking
                        else -> R.id.naviMyProfile
                    }
                }

            })

        }

    }

    inner class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount() = 3


        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> HomeFragment()
                1 -> RankingFragment()
                else -> MyProfileFragment()
            }

        }

    }

    fun getAndSendDeviceToken() {

        if (ContextUtil.getDeviceToken(mContext) != "") {
            apiService.patchRequestUpdateUserInfo("android_device_token", ContextUtil.getDeviceToken(mContext)).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })
        }

    }

    fun getKeyHash() {
        val info = packageManager.getPackageInfo(
            "com.neppplus.gudocin_android",
            PackageManager.GET_SIGNATURES
        )
        for (signature in info.signatures) {
            val md: MessageDigest = MessageDigest.getInstance("SHA")
            md.update(signature.toByteArray())
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
        }
    }

}



