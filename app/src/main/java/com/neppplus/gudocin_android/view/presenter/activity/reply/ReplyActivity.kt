package com.neppplus.gudocin_android.view.presenter.activity.reply

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityReplyBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.GlobalData
import com.neppplus.gudocin_android.model.reply.ReplyData
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.view.adapter.reply.ReplyAdapter
import com.neppplus.gudocin_android.view.presenter.activity.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReplyActivity : BaseActivity() {

  lateinit var binding: ActivityReplyBinding

  lateinit var mReplyAdapter: ReplyAdapter

  val mReplyList = ArrayList<ReplyData>()

  lateinit var mReviewData: ReviewData

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_reply)
    setupEvents()
    setValues()
  }

  override fun setupEvents() {
    binding.btnSend.setOnClickListener {
      val inputContent = binding.edtReply.text.toString()
      apiService.postRequestReply(mReviewData.id, inputContent).enqueue(object : Callback<BasicResponse> {
          override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
            if (response.isSuccessful) {
              Toast.makeText(mContext, resources.getString(R.string.reply_success), Toast.LENGTH_SHORT).show()
              getReplyFromServer()
              binding.edtReply.setText("")
            } else {
              Toast.makeText(mContext, resources.getString(R.string.reply_failed), Toast.LENGTH_SHORT)
                .show()
            }
          }

          override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
        })
    }
  }

  override fun setValues() {
    Glide.with(mContext).load(GlobalData.loginUser!!.profileImageURL).into(binding.imgProfile)

    mReviewData = intent.getSerializableExtra("review") as ReviewData
    mReplyAdapter = ReplyAdapter(mContext, R.layout.adapter_reply, mReplyList)
    binding.lvReply.adapter = mReplyAdapter
    getReplyFromServer()

    btnShopping.visibility = View.GONE
    btnCart.visibility = View.GONE
  }

  fun getReplyFromServer() {
    apiService.getRequestReply(mReviewData.id).enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          val br = response.body()!!
          mReplyList.clear()
          mReplyList.addAll(br.data.replies)
          mReplyAdapter.notifyDataSetChanged()
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
    })
  }

}