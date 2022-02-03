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
import java.text.SimpleDateFormat
import java.util.*

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
        binding.btnProductDetail.setOnClickListener {
//            제품 상세페이지 인텐트
            val myIntent = Intent(mContext, ProductItemDetailActivity::class.java)
            myIntent.putExtra("product_id", mReviewData.product)
            mContext.startActivity(myIntent)
        }
        binding.btnGoReply.setOnClickListener {
//            댓글 페이지로 인텐트
            val myIntent = Intent(mContext, ReplyActivity::class.java)
            myIntent.putExtra("review", mReviewData)
            mContext.startActivity(myIntent)
        }
        binding.btnBuyProduct.setOnClickListener {
//            결제 페이지로 인텐트
            val myIntent = Intent(mContext, PaymentActivity::class.java)
            myIntent.putExtra("product_id", mReviewData.product)
            myIntent.putExtra("review", mReviewData)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        mReviewData = intent.getSerializableExtra("review") as ReviewData
        setReviewDataToUI()
        getReviewDataFromSever()
    }

    fun setReviewDataToUI() {
        binding.txtReviewTitle.text = mReviewData.title
        binding.txtProductName.text = mReviewData.product.name
        binding.txtUserNickName.text = mReviewData.user.nickname
        binding.txtReviewContent.text = mReviewData.content
        Glide.with(mContext).load(mReviewData.thumbNailImg).into(binding.thumbNailImg)

//        평점을 ratingBar 에 가져오는 바인딩 함수(Int -> Float 으로 변환)
        binding.ratingBar.rating = mReviewData.score.toFloat()

//        이 화면에서 쓸 날짜를 알맞은 양식으로 변환
        val now = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy.MM.dd")
        val date = sdf.format(now.time)
        binding.txtReviewTime.text = date
    }

    fun getReviewDataFromSever() {
//        리뷰 데이터 API 서버에서 파싱
        apiService.getRequestReviewDetail(mReviewData.id).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    Log.d("불러오기 성공", "불러오기 성공")
                } else {
                    val jsonObj = JSONObject(response.errorBody()!!.string())
                    Log.d("리뷰 등록 실패", jsonObj.toString())
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}