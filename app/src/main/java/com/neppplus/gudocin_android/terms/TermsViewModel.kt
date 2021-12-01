package com.neppplus.gudocin_android.terms

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

class TermsViewModel : ViewModel() {

    val termsList = arrayListOf(
        TermsData(1, "이용약관1", true, "이용약관1"),
        TermsData(2, "이용약관2", true, "이용약관2"),
        TermsData(3, "이용약관3", true, "이용약관3"),
        TermsData(4, "이용약관4", true, "이용약관4"),
        TermsData(5, "이용약관5", false, "이용약관5"),
        TermsData(6, "이용약관6", false, "이용약관6")
    )


    private val requiredTermsCount = termsList.filter { it.required }.size


    val allAgreeChecked = ObservableBoolean(false)
    val btnEnable = ObservableBoolean(false)

    fun onTermsItemCheckBoxClickListener(view: View) {
        termsList.let { list ->
            allAgreeChecked.set(list.filter { it.checked.get() }.size == list.size)
            btnEnable.set(list.filter { it.checked.get() && it.required }.size == requiredTermsCount)
        }
    }

    fun onAllAgreeCheckBoxListener(view: View) {
        termsList.let {
            it.forEach { termsData ->
                termsData.checked.set(allAgreeChecked.get())
            }
            btnEnable.set(allAgreeChecked.get())
        }
    }
}