package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.ProductData


class SuggestListAdapter(
    val mContext: Context,
    val mInflater: LayoutInflater,
    val mList: List<ProductData>
) : SuggestionsAdapter<ProductData, SuggestListAdapter.SuggestionHolder>(mInflater) {


    inner class SuggestionHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtProductName = itemView.findViewById<TextView>(R.id.txtProductName)
        fun bind(data: ProductData) {

            txtProductName.text = data.name

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.simple_list_item_1, parent, false)
        return SuggestionHolder(row)
    }

    override fun onBindSuggestionHolder(
        suggestion: ProductData?,
        holder: SuggestionHolder?,
        position: Int
    ) {

//        holder.bind(mList.[position])
//        holder.bind(ProductData.[position])
//
//        holder.txtProductName.setText(suggestion.name)
    }

    override fun getSingleViewHeight() = 60


}