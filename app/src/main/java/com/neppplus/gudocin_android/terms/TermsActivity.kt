package com.neppplus.gudocin_android.terms

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.neppplus.gudocin_android.NavigationActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.SignUpActivity
import com.neppplus.gudocin_android.databinding.ActivityTermsBinding

class TermsActivity : AppCompatActivity(){

    private val termsViewModel: TermsViewModel by lazy{ ViewModelProvider(this)[TermsViewModel::class.java]}

    private val termsAdapter by lazy { TermsAdapter(termsViewModel) }
    private lateinit var binding: ActivityTermsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_terms)

        binding.viewModel = termsViewModel
        binding.recyclerView.adapter = termsAdapter

        binding.lifecycleOwner = this

        binding.btnComplete.setOnClickListener {
            finish()
        }

    }

}