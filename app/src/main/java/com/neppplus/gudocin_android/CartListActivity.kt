package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.adapters.CartListRecyclerViewAdapter
import com.neppplus.gudocin_android.databinding.ActivityCartListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.CartData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class CartListActivity : BaseActivity() {

    lateinit var binding: ActivityCartListBinding

    val mCartList = ArrayList<CartData>()

    lateinit var mCartListRecyclerViewAdapter: CartListRecyclerViewAdapter

    var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnSubscribe.setOnClickListener {
            val myIntent = Intent(mContext, PaymentActivity::class.java)
            startActivity(myIntent)
            finish()
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
        getCartListFromServer()

        mCartListRecyclerViewAdapter = CartListRecyclerViewAdapter(mContext, mCartList)
        binding.cartListRecyclerView.adapter = mCartListRecyclerViewAdapter
        binding.cartListRecyclerView.layoutManager = LinearLayoutManager(mContext)

        btnCart.visibility = View.GONE
    }

    fun getCartListFromServer() {
        apiService.getRequestCartList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mCartList.clear()
                    mCartList.addAll(br.data.carts)
                    mCartListRecyclerViewAdapter.notifyDataSetChanged()

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