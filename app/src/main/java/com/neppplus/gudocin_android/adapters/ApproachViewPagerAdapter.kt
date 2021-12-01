package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.neppplus.gudocin_android.R

class ApproachViewPagerAdapter(private val context: Context) : PagerAdapter() {

    private var layoutInflater: LayoutInflater? = null

    val Image = arrayOf(
        R.drawable.banner_special_day,
        R.drawable.banner_best_discount,
        R.drawable.banner_super_sale
    )

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as View
    }

    override fun getCount(): Int {
        return Image.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.approach_imageview, null)
        val image = view.findViewById<View>(R.id.imageView) as ImageView

        image.setImageResource(Image[position])
        val viewPager = container as ViewPager
        viewPager.addView(view, 0)

        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val viewPager = container as ViewPager
        val view = `object` as View
        viewPager.removeView(view)
    }

}