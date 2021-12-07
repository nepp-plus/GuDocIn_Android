package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.utils.SuggestionHolder


class SuggestListAdapter(val mInflater : LayoutInflater) : SuggestionsAdapter<ProductData,SuggestionHolder>(mInflater) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionHolder {

        return

    }

    override fun onBindSuggestionHolder(
        suggestion: ProductData?,
        holder: SuggestionHolder?,
        position: Int,
        view: View
    ) {

//        val row = LayoutInflater.getLay.inflate(R.layout.product_item, parent, false)
//        return SuggestionHolder(row)


    }

    override fun getSingleViewHeight(): Int {

        return 0
    }




}