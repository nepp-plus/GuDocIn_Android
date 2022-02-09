package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.EatCategoryListActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.SmallCategoriesData

class SmallCategoriesAdapter(
    val mContext: Context, val mList: List<SmallCategoriesData>
) : RecyclerView.Adapter<SmallCategoriesAdapter.SmallCategoryViewHolder>() {

    inner class SmallCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtSmallCategory = view.findViewById<TextView>(R.id.txtSmallCategory)
        val layoutSmallCategoryList =
            view.findViewById<LinearLayout>(R.id.layoutSmallCategoryList)

        fun bind(data: SmallCategoriesData) {
            txtSmallCategory.text = data.name
            layoutSmallCategoryList.setOnClickListener {
                val selectedSmallCategoryNum = data.id
                if (mContext is EatCategoryListActivity) {
                    mContext.mClickedSmallCategoryNum = selectedSmallCategoryNum
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallCategoryViewHolder {
        val row =
            LayoutInflater.from(mContext).inflate(R.layout.small_categories_list_item, parent, false)
        return SmallCategoryViewHolder(row)
    }

    override fun onBindViewHolder(holder: SmallCategoryViewHolder, position: Int) {
        val data = mList[position]
        holder.bind(data)
    }

    override fun getItemCount() = mList.size

}