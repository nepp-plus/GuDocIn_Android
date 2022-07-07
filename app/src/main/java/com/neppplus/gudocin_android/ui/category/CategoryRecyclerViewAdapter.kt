package com.neppplus.gudocin_android.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.databinding.AdapterCategoryBinding
import com.neppplus.gudocin_android.model.category.SmallCategoryData

class CategoryRecyclerViewAdapter(
  private val mSmallCategoryList: List<SmallCategoryData>
) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.SmallCategoryViewHolder>() {

  inner class SmallCategoryViewHolder(private val binding: AdapterCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: SmallCategoryData) {
      binding.txtCategory.text = data.name
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallCategoryViewHolder {
    val binding = AdapterCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return SmallCategoryViewHolder(binding)
  }

  override fun onBindViewHolder(holder: SmallCategoryViewHolder, position: Int) {
    holder.bind(mSmallCategoryList[position])
  }

  override fun getItemCount() = mSmallCategoryList.size

}