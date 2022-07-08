package com.neppplus.gudocin_android.ui.compose

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityComposeBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.product.ProductData
import com.neppplus.gudocin_android.model.user.GlobalData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.util.URIPathHelper
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

class ComposeActivity : BaseActivity() {

    lateinit var binding: ActivityComposeBinding

    lateinit var retrofitService: RetrofitService

    private lateinit var mProductData: ProductData

    private val mInputTagList = ArrayList<String>()

    private var mThumbnailUri: Uri? = null

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_compose)
        binding.apply {
            activity = this@ComposeActivity
            retrofitService = Retrofit.getRetrofit(this@ComposeActivity).create(RetrofitService::class.java)
            initView()
            permissionListener()
            inputTagListener()
            reviewUploadListener()
        }
    }

    private fun ActivityComposeBinding.permissionListener() {
        // 실제 파일 경로 읽는 권한 필요 (업로드 가능)
        val ocl = View.OnClickListener {
            val permissionListener: PermissionListener = object : PermissionListener {
                override fun onPermissionGranted() {
                    // 갤러리 왕복 이동
                    val intent = Intent()
                    intent.action = Intent.ACTION_PICK
                    intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
                    resultLauncher.launch(intent)
                }

                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(this@ComposeActivity, resources.getString(R.string.gallery_permission_nothing),
                        Toast.LENGTH_SHORT).show()
                }
            }

            TedPermission.create()
                .setPermissionListener(permissionListener)
                .setRationaleMessage(resources.getString(R.string.gallery_permission_need))
                .setDeniedMessage(resources.getString(R.string.gallery_permission_process))
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check()
        }

        llSelectImage.setOnClickListener(ocl)
        imgThumbnail.setOnClickListener(ocl)
    }

    private fun ActivityComposeBinding.inputTagListener() {
        edtKeyword.addTextChangedListener {
            val nowText = it.toString()

            if (nowText == "") {
                return@addTextChangedListener
            }

            Log.d(resources.getString(R.string.input_data), nowText)

            // 스페이스바 적용 시 태그 입력
            if (nowText.last() == ' ') {
                Log.d(resources.getString(R.string.input_data), resources.getString(R.string.input_space_bar))

                val tag = nowText.replace(" ", "")
                mInputTagList.add(tag)

                val tagBox = LayoutInflater.from(this@ComposeActivity)
                    .inflate(R.layout.adapter_compose, null)

                val txtTag = tagBox.findViewById<TextView>(R.id.txtTag)
                txtTag.text = "#${tag}"

                flTag.addView(tagBox)
                edtKeyword.setText("")
            }
        }
    }

    private fun ActivityComposeBinding.reviewUploadListener() {
        btnUpload.setOnClickListener {
            val inputTitle = edtTitle.text.toString()
            val inputContent = edtContent.text.toString()

            // 제목 미입력 시 진행 불가
            if (inputTitle.isEmpty()) {
                Toast.makeText(this@ComposeActivity, resources.getString(R.string.input_title),
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 대표 사진 미첨부 시 진행 불가
            if (mThumbnailUri == null) {
                Toast.makeText(this@ComposeActivity, resources.getString(R.string.input_represent_photo),
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 리뷰 내용 미입력 시 진행 불가
            if (inputContent.isEmpty()) {
                Toast.makeText(this@ComposeActivity, resources.getString(R.string.input_review),
                    Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val alert = AlertDialog.Builder(this@ComposeActivity)
            alert.setTitle(resources.getString(R.string.notice_review))
            alert.setMessage(resources.getString(R.string.review_registration))
            alert.setPositiveButton(
                resources.getString(R.string.confirm)
            ) { _, _ ->
                val rating = ratingBar.rating.toDouble()
                var tagStr = ""

                for (tag in mInputTagList) {
                    Log.d(resources.getString(R.string.attach_tag), tag)
                    tagStr += tag
                    tagStr += ","
                }

                tagStr = tagStr.substring(0, tagStr.length - 1)
                Log.d(resources.getString(R.string.completed_tag), tagStr)

                val productIdBody =
                    RequestBody.create(
                        MediaType.parse(resources.getString(R.string.text_plain)),
                        mProductData.id.toString()
                    )

                val inputContentBody =
                    RequestBody.create(
                        MediaType.parse(resources.getString(R.string.text_plain)),
                        inputContent
                    )

                val inputTitleBody =
                    RequestBody.create(
                        MediaType.parse(resources.getString(R.string.text_plain)),
                        inputTitle
                    )

                val ratingBody =
                    RequestBody.create(
                        MediaType.parse(resources.getString(R.string.text_plain)),
                        rating.toString()
                    )

                val tagStrBody = RequestBody.create(
                    MediaType.parse(resources.getString(R.string.text_plain)),
                    tagStr
                )

                val file = File(URIPathHelper().getPath(this@ComposeActivity, mThumbnailUri!!)!!)

                val fileReqBody = RequestBody.create(MediaType.get("image/*"), file)

                val thumbNailImageBody =
                    MultipartBody.Part.createFormData("thumbnail_img", "thumbnail.jpg", fileReqBody)

                val param = HashMap<String, RequestBody>()
                param["product_id"] = productIdBody
                param["content"] = inputContentBody
                param["title"] = inputTitleBody
                param["score"] = ratingBody
                param["tag_list"] = tagStrBody
                param["thumbnail_img"] = fileReqBody

                retrofitService.postRequestReview(param, thumbNailImageBody).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@ComposeActivity, resources.getString(R.string.review_upload), Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            val jsonObj = JSONObject(response.errorBody()!!.string())
                            Log.d(resources.getString(R.string.review_upload_failed), jsonObj.toString())
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                        Log.d("onFailure", resources.getString(R.string.data_loading_failed))
                    }
                })
            }
            alert.setNegativeButton(resources.getString(R.string.cancel), null)
            alert.show()
        }
    }

    private fun ActivityComposeBinding.initView() {
        viewData()
        uploadImage()
        customDate()
        actionBarVisibility()
    }

    private fun ActivityComposeBinding.viewData() {
        mProductData = intent.getSerializableExtra("product") as ProductData
        txtProduct.text = mProductData.name
        txtNickName.text = GlobalData.loginUser!!.nickname
        Glide.with(this@ComposeActivity).load(GlobalData.loginUser!!.profileImageURL).into(imgProfile)
    }

    private fun ActivityComposeBinding.uploadImage() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    mThumbnailUri = it.data!!.data
                    llSelectImage.visibility = View.GONE
                    imgThumbnail.visibility = View.VISIBLE
                    Glide.with(this@ComposeActivity).load(mThumbnailUri).into(imgThumbnail)
                }
            }
    }

    private fun ActivityComposeBinding.customDate() {
        val now = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val date = sdf.format(now.time)
        txtDate.text = date
    }

    private fun actionBarVisibility() {
        cart.visibility = View.GONE
        shopping.visibility = View.GONE
    }

}