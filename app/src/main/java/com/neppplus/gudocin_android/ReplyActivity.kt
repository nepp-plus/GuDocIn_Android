package com.neppplus.gudocin_android

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.adapters.ReplyAdapter
import com.neppplus.gudocin_android.databinding.ActivityReplyBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReplyActivity : BaseActivity() {

    lateinit var binding: ActivityReplyBinding

    lateinit var mReviewData: ReviewData

    lateinit var mReplyAdapter: ReplyAdapter


    val mReplyList = ArrayList<ReviewData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reply)
        setupEvents()
        setValues()
    }



    override fun setupEvents() {

        binding.btnEditReply.setOnClickListener {
            val inputContent = binding.edtReply.text.toString()

            apiService.postRequestReviewReply(mReviewData.id, inputContent)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful){
                            val br = response.body()!!


                        }

                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }


                })

        }


    }

    override fun setValues() {

        getRequestReviewFromServer()



    }
    fun getRequestReviewFromServer() {
        apiService.getRequestReviewReply(mReviewData.id).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful){
                    val br = response.body()!!

                    mReplyList.clear()
                    mReplyList.addAll(br.data.replies.user)


                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }


        })
        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        binding.reviewReplyListview.adapter = mReplyAdapter


    }


}