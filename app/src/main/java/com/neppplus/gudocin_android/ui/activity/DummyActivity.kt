package com.neppplus.gudocin_android.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityDummyBinding

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