package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filter.FilterResults
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
        if (suggestion != null) {
            if (holder != null) {
                holder.txtProductName.setText(suggestion.name.toString())
            }
        }

    }

    override fun getSingleViewHeight() = 60


    override fun getFilter(): Filter? {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val results = FilterResults()
                val term = constraint.toString()
                if (term.isEmpty()) {
                  }
                else {
                    for (item in mList) {
                        if (item.name.toLowerCase()
                                .contains(term.toLowerCase())
                        )
                         break
                    }
                }
                results.values = suggestions
                return results
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                suggestions = results.values as ArrayList<ProductData>
                notifyDataSetChanged()
            }
        }
    }


}