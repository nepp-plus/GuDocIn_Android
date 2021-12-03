package com.neppplus.gudocin_android.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.neppplus.gudocin_android.fragments.MySaveMoneyPaymentFragment
import com.neppplus.gudocin_android.fragments.MySaveMoneyUsageFragment

class SaveMoneyViewPagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {



    override fun getCount()= 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "적립금 지급 내역"
            else -> "적립금 사용 내역"
        }
    }

    override fun getItem(position: Int): Fragment {

        return when(position) {

            0-> MySaveMoneyPaymentFragment()
            else -> MySaveMoneyUsageFragment()
        }

    }
}