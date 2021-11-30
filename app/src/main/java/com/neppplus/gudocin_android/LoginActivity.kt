package com.neppplus.gudocin_android

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient
import com.neppplus.gudocin_android.databinding.ActivityLoginBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.MessageDigest
import java.util.*

class LoginActivity : BaseActivity() {

    lateinit var binding: ActivityLoginBinding

    lateinit var callbackManager: CallbackManager

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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()

        binding.btnGoogleLogin.setOnClickListener {

            startActivityForResult(googleSignInIntent, RESULT_CODE)

            val myIntent = Intent(mContext, NavigationActivity::class.java)
            startActivity(myIntent)

            finish()

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_CODE) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)

            result?.let {
                if (it.isSuccess) {
                    it.signInAccount?.displayName //이름
                    it.signInAccount?.email //이메일
                    Log.e("Value", it.signInAccount?.email!!)
                    // 기타 등등
                } else {
                    Log.e("Value", "error")
                    // 에러 처리
                }
            }
        }
    }

    override fun setupEvents() {

        binding.btnKakaoLogin.setOnClickListener {

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(mContext)) {

                UserApiClient.instance.loginWithKakaoTalk(mContext) { token, error ->

                    if (error != null) {
                        Log.e("카톡로그인", "로그인 실패")
                    } else if (token != null) {

                        Log.e("카톡로그인", "로그인 성공")
                        Log.e("카톡로그인", token.accessToken)

                        getMyInfoFromKakao()

                    }

                }

            } else {

                UserApiClient.instance.loginWithKakaoAccount(mContext) { token, error ->

                    if (error != null) {
                        Log.e("카톡로그인", "로그인 실패")
                    } else if (token != null) {

                        Log.e("카톡로그인", "로그인 성공")
                        Log.e("카톡로그인", token.accessToken)

                        getMyInfoFromKakao()

                    }

                }

            }

        }

        binding.btnFacebookLogin.setOnClickListener {

            LoginManager.getInstance()
                .logInWithReadPermissions(this, Arrays.asList("public_profile"))

        }

        binding.txtSignUp.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

        binding.btnLogin.setOnClickListener {

            val inputEmail = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()

            apiService.postRequestLogin(inputEmail, inputPw).enqueue(object :
                Callback<BasicResponse> {
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if (response.isSuccessful) {
                        val basicResponse = response.body()!!

                        val userNickname = basicResponse.data.user.nickname

                        Toast.makeText(mContext, "${userNickname}님, 환영합니다!!!", Toast.LENGTH_SHORT)
                            .show()

                        ContextUtil.setToken(mContext, basicResponse.data.token)

                        GlobalData.loginUser = basicResponse.data.user

                        val myIntent = Intent(mContext, NavigationActivity::class.java)
                        startActivity(myIntent)

                        finish()

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

//        val myHandler = Handler(Looper.getMainLooper())
//
//        myHandler.postDelayed({
//
//            val myIntent: Intent
//
//            if (ContextUtil.getToken(mContext) != "") {
//                myIntent = Intent(mContext, NavigationActivity::class.java)
//            } else {
//                myIntent = Intent(mContext, LoginActivity::class.java)
//            }
//
//            startActivity(myIntent)
//
//            finish()
//
//        }, 0)

        callbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {

                Log.d("페북 로그인", result!!.accessToken.token)

                val graphApiRequest = GraphRequest.newMeRequest(
                    result.accessToken,
                    object : GraphRequest.GraphJSONObjectCallback {
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
                                                "${br.data.user.nickname}님, 환영합니다!",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            ContextUtil.setToken(mContext, br.data.token)

                                            GlobalData.loginUser = br.data.user

                                            val myIntent =
                                                Intent(mContext, NavigationActivity::class.java)
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

    fun getMyInfoFromKakao() {

        UserApiClient.instance.me { user, error ->

            if (error != null) {
                Log.e("카톡로그인", "사용자 정보 요청 실패", error)
            } else if (user != null) {
                Log.i(
                    "카톡로그인", "사용자 정보 요청 성공" +
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
                                "${br.data.user.nickname}님, 환영합니다!",
                                Toast.LENGTH_SHORT
                            ).show()

                            ContextUtil.setToken(mContext, br.data.token)

                            GlobalData.loginUser = br.data.user

                            val myIntent = Intent(mContext, NavigationActivity::class.java)
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