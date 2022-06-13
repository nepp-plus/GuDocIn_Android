package com.neppplus.gudocin_android.view.presenter.activity.cart

import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.BaseActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityCartBinding
import com.neppplus.gudocin_android.view.adapter.cart.CartRecyclerViewAdapter
import com.neppplus.gudocin_android.view.presenter.activity.dummy.DummyActivity
import java.text.NumberFormat
import java.util.*

class CartActivity : BaseActivity<ActivityCartBinding, CartViewModel>(R.layout.activity_cart) {

  private val cartViewModel: CartViewModel by viewModels()

  override val getViewModel: CartViewModel
    get() = cartViewModel

  lateinit var mCartRecyclerViewAdapter: CartRecyclerViewAdapter

  private var total = 0

  override fun initView() {
    binding { view = this@CartActivity }
    initRecyclerView()
    observe()
    toolbar()
  }

  override fun observe() {
    super.observe()
    cartViewModel.getLiveDataObserver().observe(this, androidx.lifecycle.Observer {
      if (it != null) {
        mCartRecyclerViewAdapter.setDataList(it)
        mCartRecyclerViewAdapter.notifyDataSetChanged()

        for (data in it) {
          if (data.product.price != null) {
            total += data.product.price!!
          }
        }

        val won = "${NumberFormat.getInstance(Locale.KOREA).format(total)}원"
        binding.txtPrice.text = won
      } else {
        Toast.makeText(this, resources.getString(R.string.data_loading_failed), Toast.LENGTH_SHORT).show()
      }
    })
    cartViewModel.loadDataList()
  }

  private fun initRecyclerView() {
    mCartRecyclerViewAdapter = CartRecyclerViewAdapter()
    binding.rvCart.adapter = mCartRecyclerViewAdapter
    binding.rvCart.layoutManager = LinearLayoutManager(this)
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

    /* binding.layoutScroll.viewTreeObserver.addOnScrollChangedListener {
        binding.swipeRefresh.isEnabled = (binding.layoutScroll.scrollY == 0)
    } */
  }

  fun startDummy(view: View) {
    val myIntent = Intent(this, DummyActivity::class.java)
    startActivity(myIntent)
  }

}