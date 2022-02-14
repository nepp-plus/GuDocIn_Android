package com.neppplus.gudoc_in.terms

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.neppplus.gudoc_in.R
import com.neppplus.gudoc_in.activities.BaseActivity
import com.neppplus.gudoc_in.databinding.ActivityMembershipTermsBinding

class TermsActivity : BaseActivity() {

    private lateinit var binding: ActivityMembershipTermsBinding

    private val termsViewModel: TermsViewModel by lazy { ViewModelProvider(this)[TermsViewModel::class.java] }
    private val termsAdapter by lazy { TermsAdapter(termsViewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_membership_terms)
        binding.viewModel = termsViewModel
        binding.recyclerView.adapter = termsAdapter
        binding.lifecycleOwner = this
    }

    override fun setupEvents() {
        binding.btnComplete.setOnClickListener {
            finish()
        }
    }

    override fun setValues() {

    }

}