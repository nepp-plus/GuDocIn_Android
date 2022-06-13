package com.neppplus.gudocin_android.view.adapter.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.model.category.SmallCategoryData

class CategoryRecyclerViewAdapter(
  val mContext: Context, private val mList: List<SmallCategoryData>
) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.SmallCategoryViewHolder>() {

  inner class SmallCategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val smallCategoryList: TextView = view.findViewById(R.id.txtCategory)

    fun bind(data: SmallCategoryData) {
      smallCategoryList.text = data.name
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallCategoryViewHolder {
    val row = LayoutInflater.from(mContext).inflate(R.layout.adapter_category, parent, false)
    return SmallCategoryViewHolder(row)
  }

  override fun onBindViewHolder(holder: SmallCategoryViewHolder, position: Int) {
    val data = mList[position]
    holder.bind(data)
  }

  override fun getItemCount() = mList.size

}