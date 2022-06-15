package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.neppplus.gudocin_android.view.activity.cart.CartActivity
import com.neppplus.gudocin_android.view.activity.shopping.ShoppingActivity

abstract class BaseActivity<T : ViewDataBinding, U : BaseViewModel>(@LayoutRes private val layoutRes: Int) : AppCompatActivity(layoutRes) {
  lateinit var binding: T
  abstract val getViewModel: U

  /**
   * ActionBar Contents
   */
  lateinit var back: ImageView
  lateinit var title: TextView
  lateinit var shopping: ImageView
  lateinit var cart: ImageView

  abstract fun initView()
  open fun observe() = Unit

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this@BaseActivity, layoutRes)
    binding.apply {
      lifecycleOwner = this@BaseActivity
      /**
       * 구체적인 Type 의존 없이 setVariable() 통해 variable set 가능
       * binding.xxx = "yyy" / binding.setVariable(BR.xxx, "yyy") 둘다 동일한 행위
       * BR = Binding Resource
       */
      setVariable(BR.viewModel, getViewModel)
      /**
       * 즉각적인 바인딩: 변수 또는 Observable 변경 시 바인딩은 다음 프레임 전에 변경되도록 예약
       * 바인딩을 즉시 실행해야 할 경우 executePendingBindings() 메소드 사용
       */
      executePendingBindings()
    }
    supportActionBar?.let {
      setCustomActionBar()
      actionBarListener()
    }
    initView()
    observe()
  }

  inline fun binding(block: T.() -> Unit) {
    binding.apply(block)
  }

  private fun setCustomActionBar() {
    val defActionBar = supportActionBar
    Log.d("ActionBar", "Into the Setting")

    defActionBar?.apply {
      displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
      setCustomView(R.layout.custom_action_bar)

      val toolbar = customView.parent as androidx.appcompat.widget.Toolbar
      toolbar.setContentInsetsAbsolute(0, 0)

      back = customView.findViewById(R.id.btnBack)
      title = customView.findViewById<TextView>(R.id.txtTitle).toString()
      shopping = customView.findViewById(R.id.btnShopping)
      cart = customView.findViewById(R.id.btnCart)
    }
  }

  private fun actionBarListener() {
    val onClickListener = View.OnClickListener { view ->
      when (view) {
        back -> finish()
        shopping -> startActivity(Intent(this, ShoppingActivity::class.java))
        cart -> startActivity(Intent(this, CartActivity::class.java))
      }
    }
    back.setOnClickListener(onClickListener)
    shopping.setOnClickListener(onClickListener)
    cart.setOnClickListener(onClickListener)
  }
}