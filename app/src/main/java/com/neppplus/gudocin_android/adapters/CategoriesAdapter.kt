package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.CategoriesData

class CategoriesAdapter(

    val mContext: Context,
    resId: Int,
    val mList:  List<CategoriesData>

) : ArrayAdapter<CategoriesData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null) {
            tempRow =  mInflater.inflate(R.layout.small_categories_item, null)
        }

        val row = tempRow!!

        val data =  mList[position]

        val imgSmallCategory = row.findViewById<TextView>(R.id.imgSmallCategory)

        val txtSmallCategoryName = row.findViewById<ImageView>(R.id.txtSmallCategoryName)



//        txtSmallCategoryName.text = "(${data.largeCategory.toString()})"

        return row

    }

}