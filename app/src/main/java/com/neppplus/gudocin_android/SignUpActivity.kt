package com.neppplus.gudocin_android

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
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
import com.neppplus.gudocin_android.databinding.ActivitySignUpBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.terms.TermsActivity
import com.neppplus.gudocin_android.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SignUpActivity : BaseActivity() {

    lateinit var binding: ActivitySignUpBinding

    lateinit var callbackManager: CallbackManager

    var isDuplicatedOk = false
    var isPasswordLengthOk = false
    var isPasswordSame = false

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

        binding.btnGoogleLogin.setOnClickListener {
            startActivityForResult(googleSignInIntent, LoginActivity.RESULT_CODE)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == LoginActivity.RESULT_CODE) {
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

    override fun setupEvents() {

        binding.checkSignUpConfirm.setOnClickListener {

            if (binding.checkSignUpConfirm.isChecked) {
                val myIntent = Intent(mContext, TermsActivity::class.java)
                startActivity(myIntent)
            }
        }

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
            isDuplicatedOk = false

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
                            isDuplicatedOk = true

                        } else {

                            binding.txtNicknameCheckResult.text = "다른 닉네임으로 다시 검사해주세요."
                            isDuplicatedOk = false

                        }

                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }

                })

        }

        binding.btnSignUp.setOnClickListener {

            if (!isPasswordLengthOk) {
                Toast.makeText(mContext, "비밀번호는 8글자 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (!isPasswordSame) {
                Toast.makeText(mContext, "두개의 비밀번호는 서로 같아야합니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isDuplicatedOk) {
                Toast.makeText(mContext, "닉네임 중복검사를 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!binding.checkSignUpConfirm.isChecked) {
                Toast.makeText(mContext, "회원약관 동의를 해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val email = binding.edtEmail.text.toString()
            val password = binding.edtPassword.text.toString()
            val nickname = binding.edtNickname.text.toString()
            val phone = binding.edtPhone.text.toString()

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
                            "${signUpUserNickname}님 가입을 축하합니다!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    val myIntent = Intent(mContext, NavigationActivity::class.java)
                    startActivity(myIntent)

                    finish()

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

        binding.checkMarketingConfirm.setOnClickListener {

            if (binding.checkMarketingConfirm.isChecked) {
                val alert = AlertDialog.Builder(mContext, R.style.MyDialogTheme)
                alert.setTitle("마케팅 정보 수신 동의")
                alert.setMessage("신상품 소식, 이벤트 안내, 고객 혜택 등 다양한 정보를 제공합니다.")
                alert.setPositiveButton(
                    "확인",
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        Toast.makeText(mContext, "마케팅 정보 수신 동의하였습니다.", Toast.LENGTH_SHORT).show()
                    })
                alert.show()
            }

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