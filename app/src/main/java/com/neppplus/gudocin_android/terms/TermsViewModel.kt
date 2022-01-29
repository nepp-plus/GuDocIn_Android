package com.neppplus.gudocin_android.terms

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel

class TermsViewModel : ViewModel() {

    val termsList = arrayListOf(
        TermsData(1, "GuDocIn 이용약관", true, "이용약관1"),
        TermsData(2, "개인정보 수집 및 이용 동의", true, "이용약관2"),
        TermsData(3, "개인정보 제3자 제공 동의(인증 이용기관 제공)", true, "이용약관3"),
        TermsData(4, "개인정보 제3자 제공 동의(GuDocIn)", true, "이용약관4"),
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