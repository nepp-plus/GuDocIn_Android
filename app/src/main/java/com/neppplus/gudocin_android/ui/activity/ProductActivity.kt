package com.neppplus.gudocin_android.ui.activity

import android.content.DialogInterface
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
import com.neppplus.gudocin_android.ui.adapter.ContentViewPagerAdapter
import com.neppplus.gudocin_android.ui.adapter.reviews.ShoppingReviewRecyclerViewAdapter
import com.neppplus.gudocin_android.databinding.ActivityProductBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.ProductData
import com.neppplus.gudocin_android.model.ReviewData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : BaseActivity() {

    lateinit var binding: ActivityProductBinding

    lateinit var mContentViewPagerAdapter: ContentViewPagerAdapter

    lateinit var mShoppingReviewRecyclerViewAdapter: ShoppingReviewRecyclerViewAdapter

    val mReviewList = ArrayList<ReviewData>()

    lateinit var mProductData: ProductData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.llCart.setOnClickListener {
            postAddItemToCartViaServer()
        }

        binding.llCredit.setOnClickListener {
            val myIntent = Intent(mContext, DummyActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        mProductData = intent.getSerializableExtra("product_id") as ProductData

        getDetailProductFromServer()

        mContentViewPagerAdapter =
            ContentViewPagerAdapter(supportFragmentManager, mProductData)
        binding.vpContent.adapter = mContentViewPagerAdapter
        binding.tlContent.setupWithViewPager(binding.vpContent)

        mShoppingReviewRecyclerViewAdapter =
            ShoppingReviewRecyclerViewAdapter(mContext, mReviewList)
        binding.rvReview.adapter = mShoppingReviewRecyclerViewAdapter
        binding.rvReview.layoutManager = LinearLayoutManager(
            mContext,
            LinearLayoutManager.HORIZONTAL, false
        )
        btnShopping.visibility = View.GONE
    }

    fun getDetailProductFromServer() {
        apiService.getRequestDetailProduct(mProductData.id)
            .enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        binding.txtProduct.text = br.data.product.name
                        binding.txtPrice.text = br.data.product.getFormattedPrice()
                        binding.txtStoreName.text = br.data.product.store.name
                        Glide.with(mContext).load(br.data.product.imageUrl).into(binding.imgProduct)

                        if (mProductData.reviews.size == 0) {
                            binding.txtReviewList.text = "아직 등록된 리뷰가 없습니다"
                        } else {
                            for (review in response.body()!!.data.product.reviews) {
                                review.product = mProductData
                            }
                            mReviewList.clear()
                            mReviewList.addAll(response.body()!!.data.product.reviews)
                            mShoppingReviewRecyclerViewAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
    }

    fun postAddItemToCartViaServer() {
        apiService.postRequestCart(mProductData.id)
            .enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        Log.d("성공", br.message)

                        val alert = AlertDialog.Builder(mContext)
                        alert.setTitle("장바구니 등록 완료")
                        alert.setMessage("장바구니로 이동하시겠습니까?")
                        alert.setPositiveButton("확인",
                            DialogInterface.OnClickListener { dialog, which ->
                                val myIntent = Intent(mContext, CartActivity::class.java)
                                startActivity(myIntent)
                            })
                        alert.setNegativeButton(
                            "취소",
                            DialogInterface.OnClickListener { dialog, which ->
                            })
                        alert.show()
                    } else {
                        val errorJson = JSONObject(response.errorBody()!!.string())
                        Log.d("에러경우", errorJson.toString())

                        val message = errorJson.getString("message")
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
    }

}