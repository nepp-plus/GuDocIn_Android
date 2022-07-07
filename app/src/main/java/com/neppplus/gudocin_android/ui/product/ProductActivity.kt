package com.neppplus.gudocin_android.ui.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityProductBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.ui.cart.CartActivity
import com.neppplus.gudocin_android.ui.content.ContentViewPagerAdapter
import com.neppplus.gudocin_android.ui.dummy.DummyActivity
import com.neppplus.gudocin_android.ui.review.shopping.ShoppingRecyclerViewAdapter
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : BaseActivity() {

    lateinit var binding: ActivityProductBinding

    lateinit var retrofitService: RetrofitService

    private lateinit var mContentViewPagerAdapter: ContentViewPagerAdapter

    lateinit var mShoppingRecyclerViewAdapter: ShoppingRecyclerViewAdapter

    lateinit var mProductData: ProductData

    val mReviewList = ArrayList<ReviewData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        binding.apply {
            activity = this@ProductActivity
            retrofitService = Retrofit.getRetrofit(this@ProductActivity).create(RetrofitService::class.java)
            initView()
        }
    }

    private fun ActivityProductBinding.initView() {
        mProductData = intent.getSerializableExtra("product_id") as ProductData
        actionBarVisibility()
        getRequestDetailProduct()
        setViewpager()
        recyclerviewAdapter()
    }

    private fun actionBarVisibility() {
        shopping.visibility = View.GONE
    }

    private fun ActivityProductBinding.getRequestDetailProduct() {
        retrofitService.getRequestDetailProduct(mProductData.id)
            .enqueue(object : Callback<BasicResponse> {
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!
                        Glide.with(this@ProductActivity).load(basicResponse.data.product.imageUrl).into(imgProduct)
                        txtProduct.text = basicResponse.data.product.name
                        txtStoreName.text = basicResponse.data.product.store.name
                        txtPrice.text = basicResponse.data.product.getFormattedPrice()

                        if (mProductData.reviews.isEmpty()) {
                            txtReviewList.text = resources.getString(R.string.review_empty)
                        } else {
                            for (review in response.body()!!.data.product.reviews) {
                                review.product = mProductData
                            }

                            mReviewList.apply {
                                clear()
                                addAll(response.body()!!.data.product.reviews)
                            }

                            mShoppingRecyclerViewAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    Log.d("onFailure", resources.getString(R.string.data_loading_failed))
                }
            })
    }

    private fun postRequestCart() {
        retrofitService.postRequestCart(mProductData.id).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val basicResponse = response.body()!!
                    Log.d(resources.getString(R.string.success), basicResponse.message)
                    alertDialog()
                } else {
                    val errorJson = JSONObject(response.errorBody()!!.string())
                    Log.d(resources.getString(R.string.error_case), errorJson.toString())

                    val message = errorJson.getString("message")
                    Toast.makeText(this@ProductActivity, message, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
        })
    }

    private fun ActivityProductBinding.setViewpager() {
        mContentViewPagerAdapter = ContentViewPagerAdapter(supportFragmentManager, this@ProductActivity)
        vpContent.adapter = mContentViewPagerAdapter
        tlContent.setupWithViewPager(binding.vpContent)
    }

    private fun ActivityProductBinding.recyclerviewAdapter() {
        mShoppingRecyclerViewAdapter = ShoppingRecyclerViewAdapter(mReviewList)
        rvReview.apply {
            adapter = mShoppingRecyclerViewAdapter
            layoutManager =
                LinearLayoutManager(this@ProductActivity, LinearLayoutManager.HORIZONTAL, false)
        }
    }

    fun alertDialog() {
        val alert = AlertDialog.Builder(this)
        alert.setTitle(resources.getString(R.string.cart_registration_success))
        alert.setMessage(resources.getString(R.string.move_cart_list))

        alert.setPositiveButton(
            resources.getString(R.string.confirm)
        ) { _, _ ->
            startActivity(Intent(this, CartActivity::class.java))
        }

        alert.setNegativeButton(resources.getString(R.string.cancel)) { _, _ -> }
        alert.show()
    }

    fun addCartItem() {
        postRequestCart()
    }

    fun startDummy() {
        startActivity(Intent(this, DummyActivity::class.java))
    }

}