package com.neppplus.gudocin_android.ui.review

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityReviewBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.ui.reply.ReplyActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : BaseActivity() {

    lateinit var binding: ActivityReviewBinding

    lateinit var retrofitService: RetrofitService

    private lateinit var mReviewData: ReviewData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review)
        binding.apply {
            activity = this@ReviewActivity
            retrofitService =
                Retrofit.getRetrofit(this@ReviewActivity).create(RetrofitService::class.java)
            initView()
        }
    }

    fun confirmReviewListener() {
        val intent = Intent(this@ReviewActivity, ReplyActivity::class.java)
        intent.putExtra("review", mReviewData)
        this@ReviewActivity.startActivity(intent)
    }

    private fun setRatingIndicator() {
        binding.ratingBar.setIsIndicator(true)
    }

    private fun ActivityReviewBinding.initView() {
        setReviewUI()
        actionBarVisibility()
        getRequestDetailReview()
        setRatingIndicator()
    }

    private fun actionBarVisibility() {
        cart.visibility = View.GONE
        shopping.visibility = View.GONE
    }

    private fun ActivityReviewBinding.setReviewUI() {
        mReviewData = intent.getSerializableExtra("review") as ReviewData

        txtProduct.text = mReviewData.product.name
        txtTitle.text = mReviewData.title
        txtNickName.text = mReviewData.user.nickname
        txtDate.text = mReviewData.createdAt
        txtContent.text = mReviewData.content

        Glide.with(this@ReviewActivity).load(mReviewData.thumbNailImg).into(imgThumbnail)
        Glide.with(this@ReviewActivity).load(mReviewData.user.profileImageURL).into(imgProfile)

        // 평점을 ratingBar 에 가져오는 바인딩 함수 (Int -> Float 변환)
        ratingBar.rating = mReviewData.score.toFloat()

        // txtTag.text = mReviewData.tags.toString()
    }

    private fun getRequestDetailReview() {
        retrofitService.getRequestDetailReview(mReviewData.id)
            .enqueue(object : Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("response", resources.getString(R.string.review_load_success))
                    } else {
                        val jsonObj = JSONObject(response.errorBody()!!.string())
                        Log.d(resources.getString(R.string.review_load_failed), jsonObj.toString())
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    Log.d("onFailure", resources.getString(R.string.data_loading_failed))
                }
            })
    }

}