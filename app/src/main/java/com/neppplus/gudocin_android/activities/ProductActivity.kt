package com.neppplus.gudocin_android.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.ContentViewPagerAdapter
import com.neppplus.gudocin_android.adapters.reviews.ProductReviewListRecyclerViewAdapter
import com.neppplus.gudocin_android.databinding.ActivityProductBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.datas.ReviewData
import com.neppplus.gudocin_android.dummy.DummyActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductActivity : BaseActivity() {

    lateinit var binding: ActivityProductBinding

    lateinit var mProductData: ProductData

    lateinit var mContentViewPagerAdapter: ContentViewPagerAdapter

    val mReviewList = ArrayList<ReviewData>()

    lateinit var mProductReviewListRecyclerViewAdapter: ProductReviewListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.layoutCart.setOnClickListener {
            postAddItemToCartViaServer()
        }

        binding.layoutCredit.setOnClickListener {
            val myIntent = Intent(mContext, DummyActivity::class.java)
            startActivity(myIntent)
            finish()
        }
    }

    override fun setValues() {
        mProductData = intent.getSerializableExtra("product_id") as ProductData

        getProductItemDetailFromServer()

        mContentViewPagerAdapter =
            ContentViewPagerAdapter(supportFragmentManager, mProductData)
        binding.ProductContentViewPager.adapter = mContentViewPagerAdapter
        binding.ProductContentTabLayout.setupWithViewPager(binding.ProductContentViewPager)

        mProductReviewListRecyclerViewAdapter =
            ProductReviewListRecyclerViewAdapter(mContext, mReviewList)
        binding.reviewRecyclerView.adapter = mProductReviewListRecyclerViewAdapter
        binding.reviewRecyclerView.layoutManager = LinearLayoutManager(
            mContext,
            LinearLayoutManager.HORIZONTAL, false
        )
    }

    fun getProductItemDetailFromServer() {
        apiService.getRequestProductDetail(mProductData.id)
            .enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        binding.txtProductName.text = br.data.product.name
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
                            mProductReviewListRecyclerViewAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
    }

    fun postAddItemToCartViaServer() {
        apiService.postRequestAddItemToCart(mProductData.id)
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
                                val myIntent = Intent(mContext, CartListActivity::class.java)
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