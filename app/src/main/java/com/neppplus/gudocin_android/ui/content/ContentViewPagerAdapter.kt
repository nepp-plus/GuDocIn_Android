package com.neppplus.gudocin_android.ui.content

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neppplus.gudocin_android.ui.content.product.ProductContentFragment
import com.neppplus.gudocin_android.ui.content.store.StoreContentFragment

class ContentViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val itemCount: Int = 2

    override fun getItemCount() = itemCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductContentFragment()
            else -> StoreContentFragment()
        }
    }

}