package com.neppplus.gudocin_android.ui.cart

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityCartBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.cart.CartData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.ui.dummy.DummyActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

class CartActivity : BaseActivity() {

    lateinit var binding: ActivityCartBinding

    lateinit var retrofitService: RetrofitService

    private lateinit var mCartRecyclerViewAdapter: CartRecyclerViewAdapter

    private val mCartList = ArrayList<CartData>()

    private var totalPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.apply {
            activity = this@CartActivity
            retrofitService =
                Retrofit.getRetrofit(this@CartActivity).create(RetrofitService::class.java)
            initView()
        }
    }

    private fun ActivityCartBinding.initView() {
        initRecyclerView()
        getRequestCart()
        actionBarVisibility()
    }

    private fun ActivityCartBinding.getRequestCart() {
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

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

    private fun ActivityCartBinding.initRecyclerView() {
        mCartRecyclerViewAdapter = CartRecyclerViewAdapter(mCartList)
        rvCart.apply {
            adapter = mCartRecyclerViewAdapter
            layoutManager = LinearLayoutManager(this@CartActivity)
        }
    }

    private fun actionBarVisibility() {
        shopping.visibility = View.GONE
        cart.visibility = View.GONE
    }

    private fun ActivityCartBinding.calculator() {
        for (data in mCartList)
            totalPrice += data.product.price

        val koreanWon = "${NumberFormat.getInstance(Locale.KOREA).format(totalPrice)}원"
        txtPrice.text = koreanWon
    }

    fun swipeRefresh() {
        try {
            val intent = intent
            finish() // 현재 액티비티 종료
            overridePendingTransition(0, 0) // 인텐트 애니메이션 제거
            startActivity(intent) // 현재 액티비티 재실행
            overridePendingTransition(0, 0) // 인텐트 애니메이션 제거
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.swipeRefresh.isRefreshing = false
    }

    fun dummyIntent() {
        startActivity(Intent(this, DummyActivity::class.java))
    }

}