package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.EatCategoryListActivity
import com.neppplus.gudocin_android.NavigationActivity
import com.neppplus.gudocin_android.ProductItemDetailActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.SmallCategoriesData
import com.neppplus.gudocin_android.fragments.HomeFragment


class SmallCategoriesListAdapter(val mContext: Context, val mList:  List<SmallCategoriesData>

) : RecyclerView.Adapter<SmallCategoriesListAdapter.SmallCategoryViewHolder>() {

    inner class SmallCategoryViewHolder(view: View): RecyclerView.ViewHolder(view){

        val txtSmallCategoryName = view.findViewById<TextView>(R.id.txtSmallCategoryName)
        val btnRefreshListOfSmallCategory = view.findViewById<LinearLayout>(R.id.btnRefreshListOfSmallCategory)

        fun bind(data: SmallCategoriesData){
            txtSmallCategoryName.text = data.name

            btnRefreshListOfSmallCategory.setOnClickListener{

                val selectedSmallCategoryNum = data.id

                if (mContext is EatCategoryListActivity){
                    mContext.mClickedSmallCategoryNum = selectedSmallCategoryNum
                }

            }

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SmallCategoryViewHolder {
        val row= LayoutInflater.from(mContext).inflate(R.layout.small_categories_item,parent,false)
        return SmallCategoryViewHolder(row)
    }

    override fun onBindViewHolder(holder: SmallCategoryViewHolder, position: Int) {
        val data = mList[position]

        holder.bind(data)
    }

    override fun getItemCount()= mList.size

}