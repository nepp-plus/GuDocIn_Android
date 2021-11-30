package com.neppplus.gudocin_android

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityReviewBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.utils.ContextUtil
import com.neppplus.gudocin_android.utils.GlobalData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReviewActivity : BaseActivity() {

    lateinit var binding : ActivityReviewBinding

    lateinit var mProductData : ProductData


    val mInputTagList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_review)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {


        binding.edtKeyword.addTextChangedListener {

            val nowText = it.toString()

            if (nowText == ""){

                return@addTextChangedListener
            }

            Log.d("입력값", nowText)

            if (nowText.last() == ' '){
                Log.d("입력값", "스페이스바가 들어옴")

                val tag = nowText.replace(" ", "")

                mInputTagList.add(tag)

                val tagBox = LayoutInflater.from(mContext).inflate(R.layout.tag_list_item, null)
                val txtTag = tagBox.findViewById<TextView>(R.id.txtTag)
                txtTag.text = "#${tag}"

                binding.tagListLayout.addView(tagBox)

                binding.edtKeyword.setText("")

            }

            binding.btnUploadReview.setOnClickListener {

                val inputTitle = binding.edtReviewTitle.text.toString()
                val inputContent = binding.edtReviewContent.text.toString()

                val rating = binding.ratingBar.rating.toInt()
                Log.d("평점 점수", rating.toString())

                apiService.postRequestReviewContent(mProductData.id,inputTitle,inputContent, rating).enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {

                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }


                } )


            }

        }

    }

    override fun setValues() {

        mProductData = intent.getSerializableExtra("product") as ProductData

        binding.txtPrductName.text = mProductData.name
        binding.txtUserNickName.text = GlobalData.loginUser!!.nickname

        val now = Calendar.getInstance()

        val sdf = SimpleDateFormat("yyyy.MM.dd")
        val nowString = sdf.format(now.time)

        binding.txtReviewTime.text = nowString

    }
}