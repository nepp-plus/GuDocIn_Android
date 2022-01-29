package com.neppplus.gudocin_android.terms

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

class TermsAdapter(private val termsViewModel: TermsViewModel) :
    ListAdapter<TermsData, TermsHolder>(TermsDataDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TermsHolder {
        return TermsHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TermsHolder, position: Int) {
        val item = getItem(position)
        holder.onBind(termsViewModel, item)
    }

    class TermsDataDiffCallback : DiffUtil.ItemCallback<TermsData>() {
        override fun areItemsTheSame(oldItem: TermsData, newItem: TermsData): Boolean {
            return oldItem.termsSequence == newItem.termsSequence
        }

        override fun areContentsTheSame(oldItem: TermsData, newItem: TermsData): Boolean {
            return oldItem == newItem
        }
    }

}