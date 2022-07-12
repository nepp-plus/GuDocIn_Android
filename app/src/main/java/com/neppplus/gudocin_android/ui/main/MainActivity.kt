package com.neppplus.gudocin_android.ui.main

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityMainBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.ui.home.HomeFragment
import com.neppplus.gudocin_android.ui.product.ProductFragment
import com.neppplus.gudocin_android.ui.profile.ProfileFragment
import com.neppplus.gudocin_android.util.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.system.exitProcess

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var retrofitService: RetrofitService

    private var backKeyPressedTime: Long = 0

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            activity = this@MainActivity
            retrofitService = Retrofit.getRetrofit(this@MainActivity).create(RetrofitService::class.java)
            initView()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun ActivityMainBinding.initView() {
        getDeviceToken()
        getKeyHash()
        actionBarVisibility()
        viewpagerAdapter()
    }

    private fun ActivityMainBinding.viewpagerAdapter() {
        viewPager.apply {
            adapter = ViewPagerAdapter(this@MainActivity)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    bottomNav.selectedItemId = when (position) {
                        0 -> R.id.navHome
                        1 -> R.id.navReview
                        else -> R.id.navProfile
                    }
                }
            })
            offscreenPageLimit = 3 // 3장 화면 계속 유지
        }
    }

    private fun actionBarVisibility() {
        back.visibility = View.GONE
    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - backKeyPressedTime >= 1500) {
            backKeyPressedTime = System.currentTimeMillis()
            Toast.makeText(
                this, resources.getString(R.string.back_button_pressed),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            ActivityCompat.finishAffinity(this)
            System.runFinalization()
            exitProcess(0)
        }
    }

    inner class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        override fun getItemCount() = 3

        private val fragmentList = arrayListOf(HomeFragment(), ProductFragment(), ProfileFragment())

        override fun createFragment(position: Int): Fragment {
            return fragmentList[position]
        }

        fun getFragment(position: Int) = fragmentList[position]
    }

    fun viewPager(view: View) {
        view.parent.requestDisallowInterceptTouchEvent(true)
    }

    fun navigation(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navHome -> binding.viewPager.currentItem = 0
            R.id.navReview -> binding.viewPager.currentItem = 1
            else -> binding.viewPager.currentItem = 2
        }
        return true
    }

    private fun getDeviceToken() {
        if (ContextUtil.getDeviceToken(this) != "") {
            retrofitService.patchRequestUpdateUser("android_device_token", ContextUtil.getDeviceToken(this))
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                        Log.d("onResponse", resources.getString(R.string.data_loading_succeed))
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.d("onFailure", resources.getString(R.string.data_loading_failed))
                    }
                })
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun getKeyHash() {
        try {
            val info =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES)
            val signature = info.signingInfo.apkContentsSigners

            for (sig in signature) {
                val msgDig = MessageDigest.getInstance("SHA")
                msgDig.update(sig.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(msgDig.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
    }

}