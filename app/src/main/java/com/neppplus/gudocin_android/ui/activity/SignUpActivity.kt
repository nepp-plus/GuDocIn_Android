package com.neppplus.gudocin_android.ui.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivitySignUpBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.GlobalData
import com.neppplus.gudocin_android.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    lateinit var callbackManager: CallbackManager

    var isPasswordLengthOk = false
    var isPasswordSame = false
    var isDuplicatedOk = false

    private val googleSignInIntent by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_client_id)).requestEmail().build()
        GoogleSignIn.getClient(this, gso).signInIntent
    }

    companion object {
        const val RESULT_CODE = 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()

       /* binding.btnGoogleLogin.setOnClickListener {
            startActivityForResult(googleSignInIntent, LoginActivity.RESULT_CODE)
        }*/
    }

   /* override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == LoginActivity.RESULT_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            result?.let {
                if (it.isSuccess) {
                    it.signInAccount?.displayName
                    it.signInAccount?.email
                    Log.e("Value", it.signInAccount?.email!!)
                } else {
                    Log.e("Value", "error") // 에러 처리
                }

                apiService.postRequestSocialLogin(
                    "google",
                    it.signInAccount.id.toString(),
                    it.signInAccount.displayName
                ).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            val br = response.body()!!
                            Toast.makeText(
                                mContext,
                                "${br.data.user.nickname}님, 환영합니다",
                                Toast.LENGTH_SHORT
                            ).show()

                            ContextUtil.setToken(mContext, br.data.token)
                            GlobalData.loginUser = br.data.user

                            val myIntent = Intent(mContext, MainActivity::class.java)
                            startActivity(myIntent)
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            }
        }
    }*/

    override fun setupEvents() {
        binding.btnKakaoLogin.setOnClickListener {
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(mContext)) {
                UserApiClient.instance.loginWithKakaoTalk(mContext) { token, error ->
                    if (error != null) {
                        Log.e("카카오톡 로그인", "로그인 실패")
                    } else if (token != null) {
                        Log.e("카카오톡 로그인", "로그인 성공")
                        Log.e("카카오톡 로그인", token.accessToken)
                        getInfoFromKakao()
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(mContext) { token, error ->
                    if (error != null) {
                        Log.e("카카오톡 로그인", "로그인 실패")
                    } else if (token != null) {
                        Log.e("카카오톡 로그인", "로그인 성공")
                        Log.e("카카오톡 로그인", token.accessToken)
                        getInfoFromKakao()
                    }
                }
            }
        }

        binding.btnFacebookLogin.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile"))
        }

        binding.edtPassword.addTextChangedListener {
            if (it.toString().length >= 8) {
                binding.txtCheckPassword1.text = " 사용해도 좋은 비밀번호입니다"
                isPasswordLengthOk = true
            } else {
                binding.txtCheckPassword2.text = " 비밀번호는 8자리 이상이어야 합니다"
                isPasswordLengthOk = false
            }
            isPasswordSame = compareTwoPasswords()
        }

        binding.edtConfirmPassword.addTextChangedListener {
            isPasswordSame = compareTwoPasswords()
        }

        binding.edtNickname.addTextChangedListener {
            binding.txtCheckNickname.text = " 닉네임 중복 확인을 진행해 주세요"
            isDuplicatedOk = false
        }

        binding.btnCheckNickname.setOnClickListener {
            val nickname = binding.edtNickname.text.toString()
            if (nickname.isEmpty()) {
                Toast.makeText(mContext, "닉네임을 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            apiService.getRequestDuplicatedCheck("NICK_NAME", nickname)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            binding.txtCheckNickname.text = " 사용해도 좋은 닉네임입니다"
                            isDuplicatedOk = true
                        } else {
                            binding.txtCheckNickname.text = " 중복된 닉네임이 존재합니다"
                            isDuplicatedOk = false
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
        }

        binding.btnSignUp.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val phone = binding.edtPhoneNum.text.toString()
            val nickname = binding.edtNickname.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(mContext, "이메일을 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!isPasswordLengthOk) {
                Toast.makeText(mContext, "비밀번호는 8글자 이상이어야 합니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!isPasswordSame) {
                Toast.makeText(mContext, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (phone.isEmpty()) {
                Toast.makeText(mContext, "전화번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (nickname.isEmpty()) {
                Toast.makeText(mContext, "닉네임을 입력해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!isDuplicatedOk) {
                Toast.makeText(mContext, "닉네임 중복 확인을 진행해 주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            apiService.putRequestSignUp(email, password, nickname, phone).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!
                        Log.d("가입한 사람 토큰", br.data.token)
                        val signUpUserNickname = br.data.user.nickname
                        Toast.makeText(
                            mContext,
                            "${signUpUserNickname}님 가입을 축하합니다",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    val myIntent = Intent(mContext, MainActivity::class.java)
                    startActivity(myIntent)
                    finish()
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
        }
    }

    fun compareTwoPasswords(): Boolean {
        val password = binding.edtPassword.text.toString()
        val confirmPassword = binding.edtConfirmPassword.text.toString()

        if (password == confirmPassword) {
            binding.txtCheckPassword2.text = " 비밀번호가 일치합니다"
            return true
        } else {
            binding.txtCheckPassword2.text = " 비밀번호가 일치하지 않습니다"
            return false
        }
    }

    override fun setValues() {
        callbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Log.d("페이스북 로그인", result!!.accessToken.token)

                val graphApiRequest = GraphRequest.newMeRequest(
                    result.accessToken, object : GraphRequest.GraphJSONObjectCallback {
                        override fun onCompleted(jsonObj: JSONObject?, response: GraphResponse?) {
                            Log.d("내 정보 요청", jsonObj.toString())

                            val name = jsonObj!!.getString("name")
                            val id = jsonObj.getString("id")

                            apiService.postRequestSocialLogin("facebook", id, name)
                                .enqueue(object : Callback<BasicResponse> {
                                    override fun onResponse(
                                        call: Call<BasicResponse>,
                                        response: Response<BasicResponse>
                                    ) {
                                        if (response.isSuccessful) {
                                            val br = response.body()!!
                                            Toast.makeText(
                                                mContext,
                                                "${br.data.user.nickname}님, 환영합니다",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            ContextUtil.setToken(mContext, br.data.token)
                                            GlobalData.loginUser = br.data.user

                                            val myIntent =
                                                Intent(mContext, MainActivity::class.java)
                                            startActivity(myIntent)
                                            finish()
                                        }
                                    }

                                    override fun onFailure(
                                        call: Call<BasicResponse>,
                                        t: Throwable
                                    ) {

                                    }
                                })
                        }
                    })
                graphApiRequest.executeAsync()
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {

            }
        })
    }

    fun getInfoFromKakao() {
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("카카오톡 로그인", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i(
                    "카카오톡 로그인", "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"
                )

                apiService.postRequestSocialLogin(
                    "kakao",
                    user.id.toString(),
                    user.kakaoAccount?.profile?.nickname!!
                ).enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            val br = response.body()!!
                            Toast.makeText(
                                mContext,
                                "${br.data.user.nickname}님, 환영합니다",
                                Toast.LENGTH_SHORT
                            ).show()

                            ContextUtil.setToken(mContext, br.data.token)
                            GlobalData.loginUser = br.data.user

                            val myIntent = Intent(mContext, MainActivity::class.java)
                            startActivity(myIntent)
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            }
        }
    }

}