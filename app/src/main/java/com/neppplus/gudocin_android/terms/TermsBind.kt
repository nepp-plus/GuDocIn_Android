package com.neppplus.gudocin_android

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.terms.TermsAdapter
import com.neppplus.gudocin_android.terms.TermsData

@BindingAdapter("termsItems")
fun RecyclerView.bindTermsItem(list: List<TermsData>?) {
    list?.let {
        (adapter as TermsAdapter).submitList(list)
    }
}

@BindingAdapter(value = ["termsTitle", "termsRequired"], requireAll = true)
fun TextView.setTermsString(termsStr: String, required: Boolean) {
    val termsText = if (required) {
        String.format(this.context.getString(R.string.terms_title_required_true), termsStr)
    } else {
        String.format(this.context.getString(R.string.terms_title_required_false), termsStr)
    }
    this.text = termsText
}