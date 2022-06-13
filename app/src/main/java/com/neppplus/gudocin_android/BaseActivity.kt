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
import com.neppplus.gudocin_android.view.presenter.activity.cart.CartActivity
import com.neppplus.gudocin_android.view.presenter.activity.shopping.ShoppingActivity

abstract class BaseActivity<T : ViewDataBinding, U : BaseViewModel>(@LayoutRes private val layoutRes: Int) : AppCompatActivity(layoutRes) {
  lateinit var binding: T
  abstract val getViewModel: U

  /**
   * ActionBar Contents
   */
  lateinit var mContext: Context
  lateinit var back: ImageView
  lateinit var title: TextView
  lateinit var shopping: ImageView
  lateinit var cart: ImageView

  abstract fun initView()
  open fun observe() = Unit

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    // 초기화 된 layoutRes 로 dataBinding 객체 생성
    binding = DataBindingUtil.setContentView(this, layoutRes)
    binding.apply {
      lifecycleOwner = this@BaseActivity
      /**
       * 구체적인 Type 의존 없이 setVariable() 통해 variable set 가능
       * binding.xxx = "yyy" / binding.setVariable(BR.xxx, "yyy") 둘다 동일한 행위
       * BR = Binding Resource
       */
//      setVariable(BR.viewModel, getViewModel)
      /**
       * 즉각적인 바인딩: 변수 또는 Observable 변경 시 바인딩은 다음 프레임 전에 변경되도록 예약
       * 바인딩을 즉시 실행해야 할 경우 executePendingBindings() 메소드 사용
       */
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