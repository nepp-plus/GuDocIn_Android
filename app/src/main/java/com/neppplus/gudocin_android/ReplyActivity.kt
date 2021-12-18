package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.adapters.ReplyAdapter
import com.neppplus.gudocin_android.databinding.ActivityReplyBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ReplyData
import com.neppplus.gudocin_android.datas.ReviewData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReplyActivity : BaseActivity() {

    lateinit var binding: ActivityReplyBinding

    lateinit var mReviewData : ReviewData

    lateinit var mReplyAdapter : ReplyAdapter

    val mReplyList = ArrayList<ReplyData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_reply)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnEditReply.setOnClickListener {
            val inputContent = binding.edtReply.text.toString()

            apiService.postRequestReviewReply(mReviewData.id,inputContent).enqueue(object :Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if (response.isSuccessful) {
                        Toast.makeText(mContext, "댓글 작성에 성공했습니다.", Toast.LENGTH_SHORT).show()
                        getReplyListFromServer()
                    }
                    else {
                        Toast.makeText(mContext, "서버통신에 문제가 있습니다. 관리자에게 문의해주세요.", Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }


            })

        }


    }

    override fun setValues() {

        mReviewData = intent.getSerializableExtra("review") as ReviewData

        mReplyAdapter = ReplyAdapter(mContext,R.layout.reply_list_item,mReplyList)
        binding.reviewReplyListview.adapter = mReplyAdapter

        getReplyListFromServer()

    }

    fun getReplyListFromServer() {

        apiService.getRequestReviewReply(mReviewData.id).enqueue(object : Callback<BasicResponse>{
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