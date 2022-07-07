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
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.ui.reply.ReplyActivity
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
    binding.btnConfirm.setOnClickListener {
      val myIntent = Intent(mContext, ReplyActivity::class.java)
      myIntent.putExtra("review", mReviewData)
      mContext.startActivity(myIntent)
    }
    binding.ratingBar.setOnRatingChangeListener { ratingBar, _, _ ->
      ratingBar.setIsIndicator(true)
    }
  }

  override fun setValues() {
    mReviewData = intent.getSerializableExtra("review") as ReviewData
    setReviewUI()
    getReviewFromSever()
    Glide.with(mContext).load(mReviewData.user.profileImageURL).into(binding.imgProfile)
    cart.visibility = View.GONE
    shopping.visibility = View.GONE
  }

  private fun setReviewUI() {
    binding.txtProduct.text = mReviewData.product.name
    binding.txtTitle.text = mReviewData.title
    binding.txtNickName.text = mReviewData.user.nickname
    binding.txtDate.text = mReviewData.createdAt
    Glide.with(mContext).load(mReviewData.thumbNailImg).into(binding.imgThumbnail)
    binding.txtContent.text = mReviewData.content
//        평점을 ratingBar 에 가져오는 바인딩 함수(Int -> Float 으로 변환)
    binding.ratingBar.rating = mReviewData.score.toFloat()
//      binding.txtTag.text = mReviewData.tags.toString()
  }

  private fun getReviewFromSever() {
    apiService.getRequestDetailReview(mReviewData.id).enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          Log.d("response", resources.getString(R.string.review_load_success))
        } else {
          val jsonObj = JSONObject(response.errorBody()!!.string())
          Log.d(resources.getString(R.string.review_load_failed), jsonObj.toString())
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
    })
  }

}