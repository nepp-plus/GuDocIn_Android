package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.adapters.ProductContentViewPagerAdapter
import com.neppplus.gudocin_android.adapters.ReviewRecyclerViewAdapterForProductList
import com.neppplus.gudocin_android.databinding.ActivityProductItemDetailBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductItemDetailActivity : BaseActivity() {

    lateinit var binding: ActivityProductItemDetailBinding

    val mReviewList = ArrayList<ReviewData>()
    lateinit var mProductData :ProductData
    lateinit var mReviewRecyclerViewAdapterForProductList : ReviewRecyclerViewAdapterForProductList
    lateinit var mProductContentViewPagerAdapter : ProductContentViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_product_item_detail)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {



    }

    override fun setValues() {

        binding.btnBuyProduct.setOnClickListener {
           val myIntent = Intent(mContext,  ::class.java )
            myIntent.putExtra("product_id",mProductData.id)
            startActivity(myIntent)
        }

        binding.btnAddCart.setOnClickListener {
            val myIntent = Intent(mContext, MyPurchaseListActivity ::class.java)
            myIntent.putExtra("product_id",mProductData.id)
            startActivity(myIntent)

        }

        getProductItemDetailFromServer()

        mProductData = intent.getSerializableExtra("product_id") as ProductData


        //제품 상세 content 와 상점 상세 content 의 ViewPager 용 어댑터 연결
        mProductContentViewPagerAdapter = ProductContentViewPagerAdapter(supportFragmentManager)
        binding.ProductContentViewPager.adapter= mProductContentViewPagerAdapter
        binding.ProductContentTabLayout.setupWithViewPager( binding.ProductContentViewPager )

        //리뷰 리스트 Recycler View 용 어댑터 연결
        mReviewRecyclerViewAdapterForProductList = ReviewRecyclerViewAdapterForProductList(mContext,mReviewList)
        binding.reviewRecyclerViewForProduct.adapter = mReviewRecyclerViewAdapterForProductList
        binding.reviewRecyclerViewForProduct.layoutManager = LinearLayoutManager(mContext,
            LinearLayoutManager.HORIZONTAL,false)

    }

    fun getProductItemDetailFromServer(){
        apiService.getRequestProductDetail(mProductData.id).enqueue( object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
                    val br = response.body()!!
                    binding.txtProductName.text= br.data.product.name
                    binding.txtProductPrice.text = br.data.product.getFormatedPrice()
                    binding.txtProductCompanyName.text = br.data.product.store.name
                    Glide.with(mContext).load(br.data.product.imageUrl).into(binding.imgProduct)

                    if (mProductData.reviews.size == 0){
                        binding.txtViewReview.text = "아직 등록된 리뷰가 없습니다."

                    }
                    else{
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

}