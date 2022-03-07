package com.neppplus.gudocin_android.activities

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityEditCardInfoBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import okhttp3.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCardInfoActivity : BaseActivity() {

    lateinit var binding: ActivityEditCardInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_card_info)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnSave.setOnClickListener {
            val alert = AlertDialog.Builder(mContext)
            alert.setTitle("사용자 카드 정보 저장")
            alert.setMessage("카드 정보를 저장하시겠습니까?")
            alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->
                val inputNum = binding.edtCardNum.text.toString()
                if (inputNum.length != 16) {
                    Toast.makeText(mContext, "카드 번호 16자리를 입력해 주세요", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }

                val inputNickname = binding.edtCardNickname.text.toString()

                val inputValid = binding.edtCardValid.text.toString()
                if (inputValid.length != 4) {
                    Toast.makeText(mContext, "카드 유효기간 4자리를 입력해 주세요", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }

                val inputBirth = binding.edtCardBirth.text.toString()
                if (inputBirth.length != 6) {
                    Toast.makeText(mContext, "카드 생년월일 6자리를 입력해 주세요", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }

                val inputPassword = binding.edtCardPassword.text.toString()
                if (inputPassword.length != 2) {
                    Toast.makeText(mContext, "카드 비밀번호 앞 두자리를 입력해 주세요", Toast.LENGTH_SHORT).show()
                    return@OnClickListener
                }

                apiService.postRequestCardInfo(
                    inputNum,
                    inputNickname,
                    inputValid,
                    inputBirth,
                    inputPassword
                ).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(mContext, "사용자 카드 정보가 저장되었습니다", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {

                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            })
            alert.setNegativeButton("취소", null)
            alert.show()
        }
    }

    override fun setValues() {
        btnExplore.visibility = View.GONE
        btnCart.visibility = View.GONE
    }

}