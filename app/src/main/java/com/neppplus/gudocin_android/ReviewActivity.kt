package com.neppplus.gudocin_android

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.gudocin_android.databinding.ActivityReviewBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.utils.URIPathHelper
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ReviewActivity : BaseActivity() {

    val REQ_FOR_GALLERY = 1004

    var mSelectedThumbnailUri: Uri? = null

    lateinit var binding: ActivityReviewBinding

    lateinit var mProductData: ProductData

    val mRaidoList = ArrayList<String>()

    val mInputTagList = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_review)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        val ocl = View.OnClickListener {
            val pl = object : PermissionListener {
                override fun onPermissionGranted() {

                    val myIntent = Intent()
                    myIntent.action = Intent.ACTION_PICK
                    myIntent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
                    startActivityForResult(myIntent, REQ_FOR_GALLERY)

                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {

                    Toast.makeText(mContext, "갤러리 조회 권한이 없습니다.", Toast.LENGTH_SHORT).show()

                }

            }

            TedPermission.create()
                .setPermissionListener(pl)
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()
        }

        binding.selectImgLayout.setOnClickListener(ocl)
        binding.imgThumPicture.setOnClickListener(ocl)


        binding.edtKeyword.addTextChangedListener {

            val nowText = it.toString()

            if (nowText == "") {

                return@addTextChangedListener
            }

            Log.d("입력값", nowText)

            if (nowText.last() == ' ') {
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

            val inputTile = binding.edtReviewTitle.text.toString()


            if (inputTile.length < 1) {
                Toast.makeText(mContext, "제목을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputContent = binding.edtReviewContent.text.toString()
            if (inputContent.length < 1) {
                Toast.makeText(mContext, "리뷰 내용을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (mSelectedThumbnailUri == null) {
                Toast.makeText(mContext, "대표사진을 첨부해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("리뷰 등록")
            alert.setMessage("리뷰작성을 등록하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, i ->

                val rating = binding.ratingBar.rating.toDouble()

                var tagStr = ""

                for (tag in mInputTagList) {
                    Log.d("첨부할 태그", tag)
                    tagStr += tag
                    tagStr += ","
                }
                tagStr = tagStr.substring(0, tagStr.length - 1)
                Log.d("완성된String", tagStr)

                val productIdBody = RequestBody.create(MediaType.parse("text/plain"), mProductData.id.toString())
                val inputContentBody = RequestBody.create(MediaType.parse("text/plain"), inputContent)
                val inputTitleBody = RequestBody.create(MediaType.parse("text/plain"), inputTile)
                val ratingBody = RequestBody.create(MediaType.parse("text/plain"), rating.toString())
                val tagStrBody = RequestBody.create(MediaType.parse("text/plain"), tagStr)


                val file = File( URIPathHelper().getPath(mContext, mSelectedThumbnailUri!!) )
                val fileReqBody =  RequestBody.create(MediaType.get("image/*"), file)
                val thumbNailImagebody = MultipartBody.Part.createFormData("profile_image", "myFile.jpg", fileReqBody)

                val param = HashMap<String, RequestBody>()

                param.put("product_id", productIdBody)
                param.put("content", inputContentBody)
                param.put("title", inputTitleBody)
                param.put("score", ratingBody)
                param.put("tag_list", tagStrBody)
                param.put("thumbnail_img", fileReqBody)

                apiService.postRequestReviewContent(
                    param
                ).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {

                        if (response.isSuccessful) {

                            finish()
                            Toast.makeText(mContext, "리뷰가 등록되었습니다..", Toast.LENGTH_SHORT).show()

                        } else {
                            val jsonobj = JSONObject(response.errorBody()!!.string())
                            Log.d("리뷰등록실패", jsonobj.toString())
                        }

                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }


                })


            })
            alert.setNegativeButton("취소", null)

            alert.show()


        }
        binding.btnCancleReview.setOnClickListener {

            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("리뷰 취소 알람")
            alert.setMessage("리뷰작성을 취소하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialog, i ->

                finish()
                Toast.makeText(mContext, "리뷰작성이 취소되었습니다.", Toast.LENGTH_SHORT).show()

            })
            alert.setNegativeButton("취소", null)

            alert.show()


        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_FOR_GALLERY) {
            if (resultCode == RESULT_OK) {

                mSelectedThumbnailUri = data!!.data

                binding.selectImgLayout.visibility = View.GONE
                binding.imgThumPicture.visibility = View.VISIBLE

                Glide.with(mContext).load(mSelectedThumbnailUri).into(binding.imgThumPicture)

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