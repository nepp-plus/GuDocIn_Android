package com.neppplus.gudocin_android

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivityFindAccountBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindAccountActivity : BaseActivity() {

    lateinit var binding: ActivityFindAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_account)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_account)
        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.btnSubmit1.setOnClickListener {

            val inputNickname = binding.edtNickname1.text.toString()
            val inputPhone = binding.edtPhone.text.toString()

            apiService.getRequestEmail(inputNickname, inputPhone).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if (response.isSuccessful) {

                        val basicResponse = response.body()!!

                        val userEmail = basicResponse.data.user.email

                        Toast.makeText(mContext, "찾으시는 아이디는 ${userEmail}입니다.", Toast.LENGTH_SHORT)
                            .show()

                    } else {

                        val errorJson = JSONObject(response.errorBody()!!.string())
                        Log.d("에러경우", errorJson.toString())

                        val message = errorJson.getString("message")

                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

    }

    override fun setValues() {
    }
}