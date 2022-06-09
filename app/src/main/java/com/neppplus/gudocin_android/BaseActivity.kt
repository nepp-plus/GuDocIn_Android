package com.neppplus.gudocin_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.neppplus.gudocin_android.presentation.activity.CartActivity
import com.neppplus.gudocin_android.presentation.activity.ShoppingActivity

abstract class BaseActivity<T : ViewDataBinding, U : BaseViewModel>(@LayoutRes private val layoutRes: Int) : AppCompatActivity(layoutRes) {
  lateinit var mContext: Context
  lateinit var binding: T
  abstract val getViewModel: U

  /**
   * ActionBar Contents
   */
  private lateinit var back: ImageView
  private lateinit var title: TextView
  private lateinit var shopping: ImageView
  private lateinit var cart: ImageView

  abstract fun initView()
  open fun observe() = Unit

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // 초기화 된 layoutRes 로 dataBinding 객체 생성
    binding = DataBindingUtil.setContentView(this, layoutRes)
    binding.apply {
      lifecycleOwner = this@BaseActivity
      setVariable(BR.viewModel, getViewModel)
      executePendingBindings()
    }

    supportActionBar?.let {
      setCustomActionBar()
    }

    initView()
    observe()
  }

  private fun setCustomActionBar() {
    val defActionBar = supportActionBar!!
    Log.d("액션바", "설정으로 들어옴")

    defActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
    defActionBar.setCustomView(R.layout.custom_action_bar)

    val toolbar = defActionBar.customView.parent as Toolbar
    toolbar.setContentInsetsAbsolute(0, 0)

    back = defActionBar.customView.findViewById(R.id.btnBack)
    title = defActionBar.customView.findViewById(R.id.txtTitleInActionBar)
    shopping = defActionBar.customView.findViewById(R.id.btnShopping)
    cart = defActionBar.customView.findViewById(R.id.btnCart)

    startActivity()
  }

  private fun startActivity() {
    back.setOnClickListener {
      finish()
    }

    shopping.setOnClickListener {
      startActivity(Intent(mContext, ShoppingActivity::class.java))
    }

    cart.setOnClickListener {
      startActivity(Intent(mContext, CartActivity::class.java))
    }
  }

  inline fun binding(block: T.() -> Unit) {
    binding.apply(block)
  }
}