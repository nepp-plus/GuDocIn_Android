package com.neppplus.gudocin_android.view.presenter.activity.cart

import android.content.Intent
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.BaseActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityCartBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.cart.CartData
import com.neppplus.gudocin_android.view.adapter.cart.CartRecyclerViewAdapter
import com.neppplus.gudocin_android.view.presenter.activity.dummy.DummyActivity
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

@AndroidEntryPoint
class CartActivity : BaseActivity<ActivityCartBinding, CartViewModel>(R.layout.activity_cart) {

  private val cartViewModel: CartViewModel by viewModels()

  override val getViewModel: CartViewModel
    get() = cartViewModel

  lateinit var mCartRecyclerViewAdapter: CartRecyclerViewAdapter

  private val mCartList = ArrayList<CartData>()

  private var total = 0

  override fun initView() {
    binding { view = this@CartActivity }
    initRecyclerView()
    getCartFromServer()
    toolbar()
  }

  private fun getCartFromServer() {
    retrofitService.getRequestCart().enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          val basicResponse = response.body()!!
          mCartList.apply {
            clear()
            addAll(basicResponse.data.carts)
          }
          mCartRecyclerViewAdapter.notifyDataSetChanged()
          calculator()
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
    })
  }

  private fun initRecyclerView() {
    mCartRecyclerViewAdapter = CartRecyclerViewAdapter(mCartList)

    binding.rvCart.apply {
      adapter = mCartRecyclerViewAdapter
      layoutManager = LinearLayoutManager(this@CartActivity)
    }
  }

  private fun calculator() {
    for (data in mCartList) {
      if (data.product.price != null) {
        total += data.product.price!!
      }
    }
    var won = "${NumberFormat.getInstance(Locale.KOREA).format(total)}원"
    binding.txtPrice.text = won
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

    // Scroll 시 SwipeRefreshLayout Refresh 동작 방지
    /* binding.layoutScroll.viewTreeObserver.addOnScrollChangedListener {
      binding.swipeRefresh.isEnabled = (binding.layoutScroll.scrollY == 0)
    } */
  }

  fun startDummy(view: View) {
    val myIntent = Intent(this, DummyActivity::class.java)
    startActivity(myIntent)
  }

}