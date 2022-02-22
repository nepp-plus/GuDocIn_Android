package com.neppplus.gudocin_android.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityReviewBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import com.neppplus.gudocin_android.dummy.DummyActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : BaseActivity() {

    lateinit var binding: ActivityReviewBinding

    lateinit var mReviewData: ReviewData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        /* binding.btnSubscribe.setOnClickListener {
            val myIntent = Intent(mContext, PaymentActivity::class.java)
            myIntent.putExtra("product_id", mReviewData.product)
            myIntent.putExtra("review", mReviewData)
            startActivity(myIntent)
        } */

        binding.btnSubscribe.setOnClickListener {
            val myIntent = Intent(mContext, DummyActivity::class.java)
            startActivity(myIntent)
            finish()
        }

        /* binding.btnProductDetail.setOnClickListener {
          val myIntent = Intent(mContext, ProductActivity::class.java)
          myIntent.putExtra("product_id", mReviewData.product)
          mContext.startActivity(myIntent)
      } */

        /* binding.layoutWatchReply.setOnClickListener {
            val myIntent = Intent(mContext, ReplyListActivity::class.java)
            myIntent.putExtra("review", mReviewData)
            mContext.startActivity(myIntent)
        } */

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
        binding.txtNickName.text = mReviewData.user.nickname
        binding.txtReviewDate.text = mReviewData.createdAt
        binding.txtProductName.text = mReviewData.product.name
        Glide.with(mContext).load(mReviewData.thumbNailImg).into(binding.imgThumbnail)
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