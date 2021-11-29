package com.neppplus.gudocin_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.kakao.sdk.user.UserApiClient
import com.neppplus.gudocin_android.databinding.ActivitySignUpBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    lateinit var callbackManager: CallbackManager

    var isDuplOk = false
    var isPasswordLengthOk = false
    var isPasswordSame = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setupEvents()
        setValues()
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

        binding.edtPassword.addTextChangedListener {

            if (it.toString().length >= 8) {
                binding.txtPasswordCheckResult1.text = "사용해도 좋은 비밀번호입니다."
                isPasswordLengthOk = true
            } else {
                binding.txtPasswordCheckResult1.text = "8글자 이상으로 해주세요."
                isPasswordLengthOk = false
            }

            isPasswordSame = compareTwoPasswords()

        }

        binding.edtPasswordConfirm.addTextChangedListener {

            isPasswordSame = compareTwoPasswords()

        }

        binding.edtNickname.addTextChangedListener {

            binding.txtNicknameCheckResult.text = "닉네임 중복검사를 해주세요."
            isDuplOk = false

        }

        binding.btnNicknameCheck.setOnClickListener {

            val nickname = binding.edtNickname.text.toString()

            apiService.getRequestDuplicatedCheck("NICK_NAME", nickname)
                .enqueue(object : Callback<BasicResponse> {
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {

                        if (response.isSuccessful) {

                            binding.txtNicknameCheckResult.text = "사용해도 좋은 닉네임 입니다."
                            isDuplOk = true

                        } else {

                            binding.txtNicknameCheckResult.text = "다른 닉네임으로 다시 검사해주세요."
                            isDuplOk = false

                        }

                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }

                })

        }

        binding.btnSignUp.setOnClickListener {

            if (!isDuplOk) {
                Toast.makeText(mContext, "닉네임 중복검사를 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isPasswordLengthOk) {
                Toast.makeText(mContext, "비밀번호는 8글자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (!isPasswordSame) {
                Toast.makeText(mContext, "두개의 비밀번호는 서로 같아야합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val nickname = binding.edtNickname.text.toString()

            apiService.putRequestSignUp(email, password, nickname).enqueue(object :
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
                            "${signUpUserNickname}님 가입을 축하합니다!",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()

                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

    }

    fun compareTwoPasswords(): Boolean {

        val originalPassword = binding.edtPassword.text.toString()
        val confirmPassword = binding.edtPasswordConfirm.text.toString()

        if (originalPassword == confirmPassword) {
            binding.txtPasswordCheckResult2.text = "사용해도 좋습니다."
            return true
        } else {
            binding.txtPasswordCheckResult2.text = "위의 비밀번호와 일치해야 합니다."
            return false
        }

    }

    override fun setValues() {

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

//        getKeyHash()

    }

//    fun getKeyHash() {
//        val info = packageManager.getPackageInfo(
//            "com.neppplus.GuDocIn_Android",
//            PackageManager.GET_SIGNATURES
//        )
//        for (signature in info.signatures) {
//            val md: MessageDigest = MessageDigest.getInstance("SHA")
//            md.update(signature.toByteArray())
//            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
//        }
//
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

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