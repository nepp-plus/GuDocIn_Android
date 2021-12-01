package com.neppplus.gudocin_android

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityReviewBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.datas.ProductData
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


        }
        binding.btnUploadReview.setOnClickListener {


            val inputTag = binding.edtKeyword.text.toString()
            val inputTile = binding.edtReviewTitle.text.toString()

            if (inputTile.length < 1){
                Toast.makeText(mContext, "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputContent = binding.edtReviewContent.text.toString()
            if (inputContent.length < 1){
                Toast.makeText(mContext, "리뷰 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("리뷰 등록")
            alert.setMessage("리뷰작성을 등록하시겠습니까?")
            alert.setPositiveButton("확인",DialogInterface.OnClickListener { dialog, i ->

                val rating = binding.ratingBar.rating.toDouble()

                apiService.postRequestReviewContent(mProductData.id,inputContent,inputTile,rating,inputTag).enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {

                        if (response.isSuccessful){

                            finish()
                            Toast.makeText(mContext, "리뷰가 등록되었습니다..", Toast.LENGTH_SHORT).show()

                        }
                        else {
                            val jsonobj = JSONObject(response.errorBody()!!.string())
                            Log.d("리뷰등록실패",jsonobj.toString())
                        }

                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }


                })




            })
            alert.setNegativeButton("취소",null)

            alert.show()



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