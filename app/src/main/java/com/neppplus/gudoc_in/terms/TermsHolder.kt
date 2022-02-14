package com.neppplus.gudoc_in.terms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudoc_in.databinding.TermsHolderBinding

class TermsHolder(private val binding: TermsHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(viewModel: TermsViewModel, item: TermsData) {
        binding.viewModel = viewModel
        binding.termsData = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TermsHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = TermsHolderBinding.inflate(layoutInflater, parent, false)
            return TermsHolder(
                binding
            )
        }
    }
}