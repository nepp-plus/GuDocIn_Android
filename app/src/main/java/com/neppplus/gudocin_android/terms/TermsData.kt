package com.neppplus.gudocin_android.terms

import androidx.databinding.ObservableBoolean

data class TermsData(
    var termsSequence: Int,
    var termsTitle: String,
    var required: Boolean,
    var content: String,
    val checked: ObservableBoolean = ObservableBoolean(false)
)