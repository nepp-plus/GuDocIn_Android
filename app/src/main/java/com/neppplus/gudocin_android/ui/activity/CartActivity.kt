package com.neppplus.gudocin_android.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityCartBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.CartData
import com.neppplus.gudocin_android.ui.adapter.CartRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartActivity : BaseActivity() {

  lateinit var binding: ActivityCartBinding

  lateinit var mCartRecyclerViewAdapter: CartRecyclerViewAdapter

  val mCartList = ArrayList<CartData>()

  var total = 0

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
    binding.cart = this
    setupEvents()
    setValues()
  }

  override fun setupEvents() {}

  override fun setValues() {
    getCartFromServer()

    mCartRecyclerViewAdapter = CartRecyclerViewAdapter(mContext, mCartList)
    binding.rvCart.adapter = mCartRecyclerViewAdapter
    binding.rvCart.layoutManager = LinearLayoutManager(mContext)

    btnShopping.visibility = View.GONE
    btnCart.visibility = View.GONE
  }

  private fun getCartFromServer() {
    apiService.getRequestCart().enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          val br = response.body()!!
          mCartList.clear()
          mCartList.addAll(br.data.carts)
          mCartRecyclerViewAdapter.notifyDataSetChanged()
          calculator()
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

      }
    })
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
    val myIntent = Intent(mContext, DummyActivity::class.java)
    startActivity(myIntent)
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

}