package com.neppplus.gudocin_android.view.activity.cart

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.BaseActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityCartBinding
import com.neppplus.gudocin_android.view.activity.dummy.DummyActivity
import com.neppplus.gudocin_android.view.adapter.cart.CartRecyclerViewAdapter
import com.neppplus.gudocin_android.viewmodel.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding, CartViewModel>(R.layout.activity_cart) {

  private val cartViewModel: CartViewModel by viewModels()

  override val getViewModel: CartViewModel
    get() = cartViewModel

  private lateinit var mCartRecyclerViewAdapter: CartRecyclerViewAdapter

  private var totalPrice = 0

  override fun initView() {
    binding { view = this@CartActivity }
    initRecyclerView()
    observe()
    toolbar()
  }

  override fun observe() {
    super.observe()
    cartViewModel.liveDataList.observe(this) {
      if (it != null) {
        mCartRecyclerViewAdapter.setListData(it.data.carts)
        mCartRecyclerViewAdapter.notifyDataSetChanged()
        for (data in it.data.carts) {
          if (data.product.price != null) {
            totalPrice += data.product.price
          }
        }
        val koreanWon = "${NumberFormat.getInstance(Locale.KOREA).format(totalPrice)}원"
        binding.txtPrice.text = koreanWon
      } else {
        Toast.makeText(this, resources.getString(R.string.data_loading_failed), Toast.LENGTH_SHORT).show()
      }
    }
    cartViewModel.loadDataList()
  }

  private fun initRecyclerView() {
    mCartRecyclerViewAdapter = CartRecyclerViewAdapter()
    binding.rvCart.apply {
      adapter = mCartRecyclerViewAdapter
      layoutManager = LinearLayoutManager(this@CartActivity)
    }
  }

  private fun toolbar() {
    shopping.visibility = View.GONE
    cart.visibility = View.GONE
  }

  fun swipeRefresh() {
    try {
      val intent = intent
      finish() // 현재 액티비티 종료 실시
      overridePendingTransition(0, 0) // 인텐트 애니메이션 없애기
      startActivity(intent) // 현재 액티비티 재실행 실시
      overridePendingTransition(0, 0) // 인텐트 애니메이션 없애기
    } catch (e: Exception) {
      e.printStackTrace()
    }
    binding.swipeRefresh.isRefreshing = false

    /**
     * Scroll 시 SwipeRefreshLayout Refresh 동작 방지
     * binding.layoutScroll.viewTreeObserver.addOnScrollChangedListener {
     * binding.swipeRefresh.isEnabled = (binding.layoutScroll.scrollY == 0)
     * }
     */
  }

  fun dummyIntent() {
    val myIntent = Intent(this, DummyActivity::class.java)
    startActivity(myIntent)
  }

}