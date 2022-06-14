package com.neppplus.gudocin_android.view.presenter.activity.dummy

import androidx.activity.viewModels
import com.neppplus.gudocin_android.BaseActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityDummyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DummyActivity() : BaseActivity<ActivityDummyBinding, DummyViewModel>(R.layout.activity_dummy) {

  private val dummyViewModel: DummyViewModel by viewModels()

  override val getViewModel: DummyViewModel
    get() = dummyViewModel

  override fun initView() {}

}