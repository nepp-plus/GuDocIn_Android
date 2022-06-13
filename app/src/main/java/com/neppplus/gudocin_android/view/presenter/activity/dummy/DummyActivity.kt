package com.neppplus.gudocin_android.view.presenter.activity.dummy

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityDummyBinding
import com.neppplus.gudocin_android.view.presenter.activity.BaseActivity

class DummyActivity : BaseActivity() {

  lateinit var binding: ActivityDummyBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_dummy)
    setupEvents()
    setValues()
  }

  override fun setupEvents() {}

  override fun setValues() {}

}