package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.adapters.CartRecyclerAdapter
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

    val mBasketList = ArrayList<CartData>()

    lateinit var mCartRecyclerAdapter: CartRecyclerAdapter

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
    }

    override fun setValues() {
        getBasketListFromServer()

        btnCart.visibility = View.GONE

        mCartRecyclerAdapter = CartRecyclerAdapter(mContext, mBasketList)

        binding.basketListRecyclerView.adapter = mCartRecyclerAdapter
        binding.basketListRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getBasketListFromServer() {
        apiService.getRequestBasketList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mBasketList.clear()
                    mBasketList.addAll(br.data.carts)
                    mCartRecyclerAdapter.notifyDataSetChanged()

                    for (data in mBasketList) {
                        if (data.product.price != null) {
                            total += data.product.price!!
                        }
                    }
                    var KRW = "${NumberFormat.getInstance(Locale.KOREA).format(total)} 원"
                    binding.txtTotalPrice.text = KRW
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}