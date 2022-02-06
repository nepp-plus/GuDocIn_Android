package com.neppplus.gudocin_android

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.adapters.ReplyAdapter
import com.neppplus.gudocin_android.databinding.ActivityReplyBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.datas.ReplyData
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReplyActivity : BaseActivity() {

    lateinit var binding: ActivityReplyBinding

    lateinit var mReviewData: ReviewData

    lateinit var mReplyAdapter: ReplyAdapter

    val mReplyList = ArrayList<ReplyData>()

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
                        if (response.isSuccessful) {
                            Toast.makeText(mContext, "댓글이 작성되었습니다", Toast.LENGTH_SHORT).show()
                            getReplyListFromServer()
                        } else {
                            Toast.makeText(mContext, "댓글 작성 실패: 관리자에게 문의해주세요", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
        }
    }

    override fun setValues() {
        getMyInfoFromServer()
        Glide.with(mContext).load(GlobalData.loginUser!!.profileImageURL).into(binding.imgProfile)

        mReviewData = intent.getSerializableExtra("review") as ReviewData
        mReplyAdapter = ReplyAdapter(mContext, R.layout.reply_list_item, mReplyList)
        binding.reviewReplyListview.adapter = mReplyAdapter
        getReplyListFromServer()
    }

    fun getMyInfoFromServer() {
        apiService.getRequestMyInfo().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    Glide.with(mContext).load(br.data.user.profileImageURL).into(binding.imgProfile)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

    fun getReplyListFromServer() {
        apiService.getRequestReviewReply(mReviewData.id).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mReplyList.clear()
                    mReplyList.addAll(br.data.replies)
                    mReplyAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}