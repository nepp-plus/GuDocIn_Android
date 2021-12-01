package com.neppplus.gudocin_android.terms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.databinding.HolderTermsBinding

class TermsHolder(private val binding: HolderTermsBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun onBind(viewModel: TermsViewModel, item: TermsData) {
        binding.viewModel = viewModel
        binding.termsData = item
        binding.executePendingBindings()
    }

    companion object {
        fun from(parent: ViewGroup): TermsHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = HolderTermsBinding.inflate(layoutInflater, parent, false)
            return TermsHolder(
                binding
            )
        }
    }
}