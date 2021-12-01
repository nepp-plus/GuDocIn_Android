package com.neppplus.gudocin_android.terms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityTermsBinding

class TermsActivity : AppCompatActivity(){

    private val termsViewModel: TermsViewModel by lazy{ ViewModelProvider(this)[TermsViewModel::class.java]}

    private val termsAdapter by lazy { TermsAdapter(termsViewModel) }
    private lateinit var binding: ActivityTermsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setToolbar()

        binding= DataBindingUtil.setContentView(this, R.layout.activity_terms)

        binding.viewModel = termsViewModel
        binding.recyclerView.adapter = termsAdapter

        binding.lifecycleOwner = this
    }


//    private fun setToolbar(){
//        setSupportActionBar(toolbar)
//    }
}