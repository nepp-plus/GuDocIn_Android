package com.neppplus.gudocin_android.ui.activity

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
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityWriteBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.GlobalData
import com.neppplus.gudocin_android.model.ProductData
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

class WriteActivity : BaseActivity() {

  lateinit var binding: ActivityWriteBinding

  lateinit var mProductData: ProductData

  var mSelectedThumbnailUri: Uri? = null

  private val mInputTagList = ArrayList<String>()

  private lateinit var resultLauncher: ActivityResultLauncher<Intent>

  // 실제 파일 경로를 읽는 권한 필요 (업로드 가능해짐)
  private val ocl = View.OnClickListener {
    val permissionListener: PermissionListener = object : PermissionListener {
      override fun onPermissionGranted() {
        // (안드로이드 제공) 갤러리로 왕복 이동
        val myIntent = Intent()
        myIntent.action = Intent.ACTION_PICK
        myIntent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        resultLauncher.launch(myIntent)
      }

      override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
        Toast.makeText(mContext, "갤러리 조회 권한이 없습니다", Toast.LENGTH_SHORT).show()
      }
    }

    TedPermission.create()
      .setPermissionListener(permissionListener)
      .setRationaleMessage("앱의 기능을 사용하기 위해서는 권한이 필요합니다")
      .setDeniedMessage("[설정] > [애플리케이션] > [권한] 에서 확인할 수 있습니다")
      .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
      .check()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_write)
    setupEvents()
    setValues()
  }

  override fun setupEvents() {
    binding.llSelectImage.setOnClickListener(ocl)

    binding.imgThumbnail.setOnClickListener(ocl)

    binding.edtKeyword.addTextChangedListener {
      val nowText = it.toString()
      if (nowText == "") {
        return@addTextChangedListener
      }
      Log.d("입력값", nowText)

      // 입력한 값이 스페이스바가 들어오게 되면 태그가 되게 하는 함수
      if (nowText.last() == ' ') {
        Log.d("입력값", "스페이스바가 들어옴")

        val tag = nowText.replace(" ", "")
        mInputTagList.add(tag)

        val tagBox = LayoutInflater.from(mContext)
          .inflate(R.layout.adapter_tag, null)

        val txtTag = tagBox.findViewById<TextView>(R.id.txtTag)
        txtTag.text = "#${tag}"

        binding.flTag.addView(tagBox)
        binding.edtKeyword.setText("")
      }
    }

    binding.btnUpload.setOnClickListener {
      val inputTitle = binding.edtTitle.text.toString()
      val inputContent = binding.edtContent.text.toString()

      // 제목이 입력되지 않으면 버튼이 눌리지 않도록
      if (inputTitle.isEmpty()) {
        Toast.makeText(mContext, "제목을 입력해 주세요", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }
      // 대표 사진이 첨부되지 않으면 버튼이 눌리지 않도록
      if (mSelectedThumbnailUri == null) {
        Toast.makeText(mContext, "대표 사진을 첨부해 주세요", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }
      // 리뷰 내용이 입력되지 않으면 버튼이 눌리지 않도록
      if (inputContent.isEmpty()) {
        Toast.makeText(mContext, "리뷰 내용을 입력해 주세요", Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      val alert = AlertDialog.Builder(mContext)
      alert.setTitle("리뷰 작성 알림")
      alert.setMessage("리뷰를 작성하시겠습니까?\n(키워드 미입력 시 작성되지 않습니다)")
      alert.setPositiveButton("확인", DialogInterface.OnClickListener { _, _ ->
        val rating = binding.ratingBar.rating.toDouble()
        var tagStr = ""

        for (tag in mInputTagList) {
          Log.d("첨부할 태그", tag)
          tagStr += tag
          tagStr += ","
        }

        tagStr = tagStr.substring(0, tagStr.length - 1)
        Log.d("완성된 String", tagStr)

        val productIdBody =
          RequestBody.create(MediaType.parse("text/plain"), mProductData.id.toString())

        val inputContentBody =
          RequestBody.create(MediaType.parse("text/plain"), inputContent)

        val inputTitleBody = RequestBody.create(MediaType.parse("text/plain"), inputTitle)

        val ratingBody =
          RequestBody.create(MediaType.parse("text/plain"), rating.toString())

        val tagStrBody = RequestBody.create(MediaType.parse("text/plain"), tagStr)

        val file = File(URIPathHelper().getPath(mContext, mSelectedThumbnailUri!!))

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

        apiService.postRequestReviewContent(param, thumbNailImageBody).enqueue(object : Callback<BasicResponse> {
          override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
            if (response.isSuccessful) {
              Toast.makeText(mContext, "리뷰가 작성되었습니다", Toast.LENGTH_SHORT).show()
              finish()
            } else {
              val jsonObj = JSONObject(response.errorBody()!!.string())
              Log.d("리뷰 작성 실패", jsonObj.toString())
            }
          }

          override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            TODO()
          }
        })
      })
      alert.setNegativeButton("취소", null)
      alert.show()
    }
  }

  override fun setValues() {
    Glide.with(mContext).load(GlobalData.loginUser!!.profileImageURL).into(binding.imgProfile)
    mProductData = intent.getSerializableExtra("product") as ProductData
    binding.txtNickName.text = GlobalData.loginUser!!.nickname
    binding.txtProduct.text = mProductData.name
    customDate()
    uploadImage()
    btnCart.visibility = View.GONE
    btnShopping.visibility = View.GONE
  }

  private fun uploadImage() {
    resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
      if (it.resultCode == RESULT_OK) {
        mSelectedThumbnailUri = it.data!!.data
        Glide.with(mContext).load(mSelectedThumbnailUri).into(binding.imgThumbnail)
        binding.llSelectImage.visibility = View.GONE
        binding.imgThumbnail.visibility = View.VISIBLE
      }
    }
  }

  private fun customDate() {
    val now = Calendar.getInstance()
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val date = sdf.format(now.time)
    binding.txtDate.text = date
  }

}