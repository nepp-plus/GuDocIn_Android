package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.databinding.ActivityReviewDetailBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewDetailActivity : BaseActivity() {

    lateinit var binding: ActivityReviewDetailBinding

    lateinit var mReviewData: ReviewData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        /* binding.btnProductDetail.setOnClickListener {
            val myIntent = Intent(mContext, ProductDetailActivity::class.java)
            myIntent.putExtra("product_id", mReviewData.product)
            mContext.startActivity(myIntent)
        } */

        binding.btnProductReply.setOnClickListener {
            val myIntent = Intent(mContext, ReplyListActivity::class.java)
            myIntent.putExtra("review", mReviewData)
            mContext.startActivity(myIntent)
        }

        binding.btnBuyProduct.setOnClickListener {
            val myIntent = Intent(mContext, PaymentActivity::class.java)
            myIntent.putExtra("product_id", mReviewData.product)
            myIntent.putExtra("review", mReviewData)
            startActivity(myIntent)
        }

        binding.ratingBar.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            ratingBar.setIsIndicator(true)
        }
    }

    override fun setValues() {
        mReviewData = intent.getSerializableExtra("review") as ReviewData
        setReviewDataToUI()
        getReviewDataFromSever()
        Glide.with(mContext).load(mReviewData.user.profileImageURL).into(binding.imgProfile)
    }

    fun setReviewDataToUI() {
        binding.txtReviewTitle.text = mReviewData.title
        binding.txtUserNickName.text = mReviewData.user.nickname
        binding.txtReviewDate.text = mReviewData.createdAt
        binding.txtProductName.text = mReviewData.product.name
        Glide.with(mContext).load(mReviewData.thumbNailImg).into(binding.thumbNailImg)
        binding.txtReviewContent.text = mReviewData.content
//      binding.txtTag.text = mReviewData.tags.toString()

//        평점을 ratingBar 에 가져오는 바인딩 함수(Int -> Float 으로 변환)
        binding.ratingBar.rating = mReviewData.score.toFloat()
    }

    fun getReviewDataFromSever() {
        apiService.getRequestReviewDetail(mReviewData.id).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    Log.d("리뷰 불러오기 성공", "리뷰 불러오기 성공")
                } else {
                    val jsonObj = JSONObject(response.errorBody()!!.string())
                    Log.d("리뷰 불러오기 실패", jsonObj.toString())
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}