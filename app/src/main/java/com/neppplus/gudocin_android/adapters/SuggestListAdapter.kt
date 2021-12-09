package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filter.FilterResults
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import com.neppplus.gudocin_android.ProductItemDetailActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.ProductData


class SuggestListAdapter(
    val mContext: Context,
    val mInflater: LayoutInflater,
    var mList: List<ProductData>
) : SuggestionsAdapter<ProductData, SuggestListAdapter.SuggestionHolder>(mInflater) , Filterable{


    inner class SuggestionHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtProductName = itemView.findViewById<TextView>(R.id.txtProductName)
        fun bind(data: ProductData) {
            txtProductName.text = data.name
            itemView.setOnClickListener { 
//                val myIntent = Intent(mContext, ProductItemDetailActivity::class.java)
//                startActivity(myIntent)
                Toast.makeText(mContext, "클릭됨", Toast.LENGTH_SHORT).show()
            }
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
        if (suggestion != null) {
            if (holder != null) {
                holder.txtProductName.setText(suggestion.name.toString())
            }
        }

    }

    override fun getSingleViewHeight() = 60





}