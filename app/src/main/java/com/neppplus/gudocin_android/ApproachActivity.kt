package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.neppplus.gudocin_android.adapters.ApproachViewPagerAdapter
import com.neppplus.gudocin_android.databinding.ActivityApproachBinding

class ApproachActivity : BaseActivity() {

    lateinit var binding: ActivityApproachBinding

    internal lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_approach)
        setupEvents()
        setValues()

        viewPager = findViewById(R.id.viewPager) as ViewPager

        val adapter = ApproachViewPagerAdapter(this)
        viewPager.adapter = adapter

    }

    override fun setupEvents() {

        binding.btnLogin.setOnClickListener {

            val myIntent = Intent(mContext, LoginActivity::class.java)
            startActivity(myIntent)

            binding.btnSignUp.setOnClickListener {

                val myIntent = Intent(mContext, SignUpActivity::class.java)
                startActivity(myIntent)

            }

        }

    }

    override fun setValues() {

    }

}