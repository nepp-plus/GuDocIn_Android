package com.neppplus.gudocin_android

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityReviewDetailBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewDetailActivity : BaseActivity() {

    lateinit var mReviewData: ReviewData

    lateinit var binding: ActivityReviewDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_review_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        mReviewData = intent.getSerializableExtra("review") as ReviewData

       setReviewDataToUI()


        getReviewDataFromSever()

    }
    fun setReviewDataToUI(){

        binding.txtReviewTitle.text = mReviewData.title
        binding.txtProductName.text = mReviewData.product.name
        binding.txtUserNickName.text = mReviewData.user.nickname
        binding.txtReviewContent.text = mReviewData.content


    }


    fun getReviewDataFromSever() {
        apiService.getRequestReviewDetail(mReviewData.id).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful){
                    Log.d("불러오기성공","불러오기 성공")
                }
                else{
                    val jsonobj = JSONObject(response.errorBody()!!.string())
                    Log.d("리뷰등록실패",jsonobj.toString())
                }


            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {


            }

        })

    }
}