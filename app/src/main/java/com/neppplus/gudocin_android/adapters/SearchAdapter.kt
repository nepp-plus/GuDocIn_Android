package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mancj.materialsearchbar.adapter.SuggestionsAdapter
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.ProductData

class SearchAdapter(
    val mContext: Context,
    val mInflater: LayoutInflater,
    var mList: List<ProductData>
) : SuggestionsAdapter<ProductData, SearchAdapter.SuggestionHolder>(mInflater), Filterable {

    inner class SuggestionHolder(view: View) : RecyclerView.ViewHolder(view) {

        val txtProductName = itemView.findViewById<TextView>(R.id.txtProductName)

        fun bind(data: ProductData) {
            txtProductName.text = data.name
            itemView.setOnClickListener {
                /* val myIntent = Intent(mContext, ProductItemDetailActivity::class.java)
                   startActivity(myIntent) */
                Toast.makeText(mContext, "클릭됨", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestionHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.search_list_item, parent, false)
        return SuggestionHolder(row)
    }

    override fun onBindSuggestionHolder(
        suggestion: ProductData?,
        holder: SuggestionHolder?,
        position: Int
    ) {
        if (suggestion != null) {
            if (holder != null) {
                holder.txtProductName.setText(suggestion.name)
            }
        }
    }

    override fun getSingleViewHeight() = 60
}