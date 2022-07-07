package com.neppplus.gudocin_android.ui.reply

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityReplyBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.user.GlobalData
import com.neppplus.gudocin_android.model.reply.ReplyData
import com.neppplus.gudocin_android.model.review.ReviewData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReplyActivity : BaseActivity() {

  lateinit var binding: ActivityReplyBinding

  lateinit var retrofitService: RetrofitService

  lateinit var mReplyRecyclerViewAdapter: ReplyRecyclerViewAdapter

  private lateinit var mReviewData: ReviewData

  val mReplyList = ArrayList<ReplyData>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_reply)
    binding.apply {
      activity = this@ReplyActivity
      retrofitService = Retrofit.getRetrofit(this@ReplyActivity).create(RetrofitService::class.java)
      initView()
    }
  }

  private fun ActivityReplyBinding.initView() {
    viewData()
    setRecyclerView()
    getRequestReply()
    actionBarVisibility()
  }

  fun postRequestReplyContent() {
      val inputContent = binding.edtReply.text.toString()
      retrofitService.postRequestReply(mReviewData.id, inputContent).enqueue(object : Callback<BasicResponse> {
        override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
          if (response.isSuccessful) {
            Toast.makeText(this@ReplyActivity, resources.getString(R.string.reply_success), Toast.LENGTH_SHORT).show()
            getRequestReply()
            binding.edtReply.setText("")
          } else {
            Toast.makeText(this@ReplyActivity, resources.getString(R.string.reply_failed), Toast.LENGTH_SHORT).show()
          }
        }

        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
          Log.d("onFailure", resources.getString(R.string.data_loading_failed))
        }
      })
  }

  private fun ActivityReplyBinding.viewData() {
    mReviewData = intent.getSerializableExtra("review") as ReviewData
    Glide.with(this@ReplyActivity).load(GlobalData.loginUser!!.profileImageURL).into(imgProfile)
  }

  private fun ActivityReplyBinding.setRecyclerView() {
    mReplyRecyclerViewAdapter = ReplyRecyclerViewAdapter(mReplyList)

    rvReply.apply {
      adapter = mReplyRecyclerViewAdapter
      layoutManager = LinearLayoutManager(this@ReplyActivity, LinearLayoutManager.VERTICAL, false)
    }
  }

  private fun actionBarVisibility() {
    shopping.visibility = View.GONE
    cart.visibility = View.GONE
  }

  fun getRequestReply() {
    retrofitService.getRequestReply(mReviewData.id).enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          val basicResponse = response.body()!!
          mReplyList.apply {
            clear()
            addAll(basicResponse.data.replies)
          }
          mReplyRecyclerViewAdapter.notifyDataSetChanged()
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
        Log.d("onFailure", resources.getString(R.string.data_loading_failed))
      }
    })
  }

}