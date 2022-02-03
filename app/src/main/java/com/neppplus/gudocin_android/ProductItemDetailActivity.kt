package com.neppplus.gudocin_android

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.adapters.ProductContentViewPagerAdapter
import com.neppplus.gudocin_android.adapters.ReviewRecyclerViewAdapterForProductList
import com.neppplus.gudocin_android.databinding.ActivityProductItemDetailBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.datas.ReviewData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductItemDetailActivity : BaseActivity() {

    lateinit var binding: ActivityProductItemDetailBinding

    val mReviewList = ArrayList<ReviewData>()
    lateinit var mProductData: ProductData
    lateinit var mReviewRecyclerViewAdapterForProductList: ReviewRecyclerViewAdapterForProductList
    lateinit var mProductContentViewPagerAdapter: ProductContentViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_item_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnBuyProduct.setOnClickListener {
            val myIntent = Intent(mContext, PaymentActivity::class.java)
            myIntent.putExtra("product_id", mProductData)
            startActivity(myIntent)
        }
        binding.btnAddCart.setOnClickListener {
            postAddItemToCartViaServer()
        }
    }

    override fun setValues() {
        getProductItemDetailFromServer()

        mProductData = intent.getSerializableExtra("product_id") as ProductData

        // 제품 상세 & 상점 상세의 ViewPager 용 어댑터 연결
        mProductContentViewPagerAdapter =
            ProductContentViewPagerAdapter(supportFragmentManager, mProductData)
        binding.ProductContentViewPager.adapter = mProductContentViewPagerAdapter
        binding.ProductContentTabLayout.setupWithViewPager(binding.ProductContentViewPager)

        // 리뷰 리스트 Horizontal Recycler View 용 어댑터 연결
        mReviewRecyclerViewAdapterForProductList =
            ReviewRecyclerViewAdapterForProductList(mContext, mReviewList)
        binding.reviewRecyclerViewForProduct.adapter = mReviewRecyclerViewAdapterForProductList
        binding.reviewRecyclerViewForProduct.layoutManager = LinearLayoutManager(
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
                        binding.txtProductPrice.text = br.data.product.getFormattedPrice()
                        binding.txtProductCompanyName.text = br.data.product.store.name
                        Glide.with(mContext).load(br.data.product.imageUrl).into(binding.imgProduct)

                        if (mProductData.reviews.size == 0) {
                            binding.txtViewReview.text = "아직 등록된 리뷰가 없습니다"
                        } else {
                            for (review in response.body()!!.data.product.reviews) {
                                review.product = mProductData
                            }
                            mReviewList.clear()
                            mReviewList.addAll(response.body()!!.data.product.reviews)
                            mReviewRecyclerViewAdapterForProductList.notifyDataSetChanged()
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
                        alert.setTitle("장바구니 상품 등록 완료")
                        alert.setMessage("장바구니로 이동 하시겠습니까?")
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