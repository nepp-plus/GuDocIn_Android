package com.neppplus.gudocin_android

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityReviewBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.utils.ContextUtil
import org.json.JSONObject
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

                val alert = AlertDialog.Builder(mContext)

                alert.setTitle("리뷰 등록")

                val customView = LayoutInflater.from(mContext).inflate(R.layout.activity_review, null)

                alert.setView(customView)

                val edtReview = customView.findViewById<Button>(R.id.btnUploadReview)

                alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, i ->

                    val inputTitle = binding.edtReviewTitle.text.toString()
                    val inputContent = binding.edtReviewContent.text.toString()
                    val rating = binding.ratingBar.rating.toInt()

                    apiService.postRequestReviewContent(mProductData.id,inputTitle,inputContent,rating).enqueue(object :Callback<BasicResponse>{
                        override fun onResponse(
                            call: Call<BasicResponse>,
                            response: Response<BasicResponse>
                        ) {
                            if (response.isSuccessful){
                                val br = response.body()!!
                                br.data.reviews

                                Toast.makeText(mContext, "리뷰를 등록했습니다.", Toast.LENGTH_SHORT).show()
                            }
                            else{



                            }


                        }

                        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                        }

                    })

                })


            }

            binding.btnCancleReview.setOnClickListener {

                val alert = AlertDialog.Builder(mContext)
                alert.setTitle("리뷰 취소 알람")
                alert.setMessage("리뷰작성을 취소하시겠습니까?")
                alert.setPositiveButton("확인",DialogInterface.OnClickListener { dialog, i ->

                    finish()
                    Toast.makeText(mContext, "리뷰작성이 취소되었습니다.", Toast.LENGTH_SHORT).show()

                })
                alert.setNegativeButton("취소",null)

                alert.show()


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