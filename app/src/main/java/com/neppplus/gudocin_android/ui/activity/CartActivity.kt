package com.neppplus.gudocin_android.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.ui.adapter.CartRecyclerViewAdapter
import com.neppplus.gudocin_android.databinding.ActivityCartBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.CartData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartActivity : BaseActivity() {

    lateinit var binding: ActivityCartBinding

    val mCartList = ArrayList<CartData>()

    lateinit var mCartRecyclerViewAdapter: CartRecyclerViewAdapter

    var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnSubscribe.setOnClickListener {
            val myIntent = Intent(mContext, DummyActivity::class.java)
            startActivity(myIntent)
        }

        binding.swipeRefresh.setOnRefreshListener {
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
        }

        /* binding.layoutScroll.viewTreeObserver.addOnScrollChangedListener {
            binding.swipeRefresh.isEnabled = (binding.layoutScroll.scrollY == 0)
        } */
    }

    override fun setValues() {
        getCartFromServer()

        mCartRecyclerViewAdapter = CartRecyclerViewAdapter(mContext, mCartList)
        binding.cartListRecyclerView.adapter = mCartRecyclerViewAdapter
        binding.cartListRecyclerView.layoutManager = LinearLayoutManager(mContext)

        btnShopping.visibility = View.GONE
        btnCart.visibility = View.GONE
    }

    fun getCartFromServer() {
        apiService.getRequestCart().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mCartList.clear()
                    mCartList.addAll(br.data.carts)
                    mCartRecyclerViewAdapter.notifyDataSetChanged()

                    for (data in mCartList) {
                        if (data.product.price != null) {
                            total += data.product.price!!
                        }
                    }
                    var KRW = "${NumberFormat.getInstance(Locale.KOREA).format(total)}원"
                    binding.txtTotalPrice.text = KRW
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}