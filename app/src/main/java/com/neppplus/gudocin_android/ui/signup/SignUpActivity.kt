package com.neppplus.gudocin_android.ui.signup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.kakao.sdk.user.UserApiClient
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivitySignUpBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.user.GlobalData
import com.neppplus.gudocin_android.network.Retrofit
import com.neppplus.gudocin_android.network.RetrofitService
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.ui.main.MainActivity
import com.neppplus.gudocin_android.util.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

  lateinit var binding: ActivitySignUpBinding

  lateinit var retrofitService: RetrofitService

  private lateinit var callbackManager: CallbackManager

  private lateinit var resultLauncher: ActivityResultLauncher<Intent>

  private var isPasswordLength = false

  private var isPasswordAccord = false

  var isDuplicated = false

  private val googleSignInIntent by lazy {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(getString(R.string.google_default_client_id)).requestEmail().build()
    GoogleSignIn.getClient(this, gso).signInIntent
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
    binding.apply {
      activity = this@SignUpActivity
      retrofitService = Retrofit.getRetrofit(this@SignUpActivity).create(RetrofitService::class.java)
      initView()
      passwordChangedListener()
      confirmPasswordChangedListener()
      nicknameChangedListener()
    }
  }

  fun googleLogin() {
    resultLauncher.launch(googleSignInIntent)
  }

  fun facebookLogin() {
    LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile"))
  }

  fun kakaoLogin() {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(this@SignUpActivity)) {
      UserApiClient.instance.loginWithKakaoTalk(this@SignUpActivity) { token, error ->
        if (error != null) {
          Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_login_failed))
        } else if (token != null) {
          Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_login_success))
          Log.e(resources.getString(R.string.kakao_login), token.accessToken)
          postRequestKakaoLogin()
        }
      }
    } else {
      UserApiClient.instance.loginWithKakaoAccount(this@SignUpActivity) { token, error ->
        if (error != null) {
          Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_login_failed))
        } else if (token != null) {
          Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_login_success))
          Log.e(resources.getString(R.string.kakao_login), token.accessToken)
          postRequestKakaoLogin()
        }
      }
    }
  }

  private fun ActivitySignUpBinding.passwordChangedListener() {
    edtPassword.addTextChangedListener {
      if (it.toString().length >= 8) {
        txtCheckPassword1.text = resources.getString(R.string.password_pass)
        isPasswordLength = true
      } else {
        txtCheckPassword2.text = resources.getString(R.string.password_length)
        isPasswordLength = false
      }
      isPasswordAccord = comparePasswords()
    }
  }

  private fun ActivitySignUpBinding.confirmPasswordChangedListener() {
    edtConfirmPassword.addTextChangedListener {
      isPasswordAccord = comparePasswords()
    }
  }

  private fun ActivitySignUpBinding.nicknameChangedListener() {
    edtNickname.addTextChangedListener {
      txtCheckNickname.text = resources.getString(R.string.nickname_duplicated)
      isDuplicated = false
    }

  }

  fun getRequestDuplicateCheck() {
      val nickname = binding.edtNickname.text.toString()
      if (nickname.isEmpty()) {
        Toast.makeText(this@SignUpActivity, resources.getString(R.string.nickname_input), Toast.LENGTH_SHORT).show()
        return
      }

      retrofitService.getRequestDuplicateCheck("NICK_NAME", nickname)
        .enqueue(object : Callback<BasicResponse> {
          override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
            if (response.isSuccessful) {
              binding.txtCheckNickname.text = resources.getString(R.string.nickname_pass)
              isDuplicated = true
            } else {
              binding.txtCheckNickname.text = resources.getString(R.string.nickname_exist)
              isDuplicated = false
            }
          }

          override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            Log.d("failure", resources.getString(R.string.data_loading_failed))
          }
        })
  }

  fun putRequestSignUp() {
      val email = binding.edtEmail.text.toString()
      val password = binding.edtPassword.text.toString()
      val phone = binding.edtPhoneNum.text.toString()
      val nickname = binding.edtNickname.text.toString()

      if (email.isEmpty()) {
        Toast.makeText(this@SignUpActivity,
          resources.getString(R.string.email_input),
          Toast.LENGTH_SHORT).show()
        return
      }

      if (!isPasswordLength) {
        Toast.makeText(this@SignUpActivity,
          resources.getString(R.string.password_length),
          Toast.LENGTH_SHORT).show()
        return
      }

      if (!isPasswordAccord) {
        Toast.makeText(this@SignUpActivity,
          resources.getString(R.string.password_incorrect),
          Toast.LENGTH_SHORT).show()
        return
      }

      if (phone.isEmpty()) {
        Toast.makeText(this@SignUpActivity, resources.getString(R.string.phone_num_input),
          Toast.LENGTH_SHORT).show()
        return
      }

      if (nickname.isEmpty()) {
        Toast.makeText(this@SignUpActivity, resources.getString(R.string.nickname_input),
          Toast.LENGTH_SHORT).show()
        return
      }

      if (!isDuplicated) {
        Toast.makeText(this@SignUpActivity, resources.getString(R.string.nickname_duplicated),
          Toast.LENGTH_SHORT).show()
        return
      }

      retrofitService.putRequestSignUp(email, password, nickname, phone)
        .enqueue(object : Callback<BasicResponse> {
          override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
            if (response.isSuccessful) {
              val basicResponse = response.body()!!
              Log.d(resources.getString(R.string.fcm_token), basicResponse.data.token)

              val signUpUserNickname = basicResponse.data.user.nickname
              Toast.makeText(this@SignUpActivity, resources.getString(R.string.congratulation_user).replace(resources.getString(R.string.user), signUpUserNickname),
                Toast.LENGTH_SHORT).show()
            }

            startMainActivity()
          }

          override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
            Log.d("onFailure", resources.getString(R.string.data_loading_failed))
          }
        })
  }

  private fun ActivitySignUpBinding.comparePasswords(): Boolean {
    val password = edtPassword.text.toString()
    val confirmPassword = edtConfirmPassword.text.toString()

    return if (password == confirmPassword) {
      txtCheckPassword2.text = resources.getString(R.string.password_correct)
      true
    } else {
      txtCheckPassword2.text = resources.getString(R.string.password_incorrect)
      false
    }
  }

  private fun initView() {
    postRequestGoogleLogin()
    postRequestFacebookLogin()
  }

  private fun postRequestKakaoLogin() {
    UserApiClient.instance.me { user, error ->
      if (error != null) {
        Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_request_failed), error)
      } else if (user != null) {
        Log.i(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_request_success)
                + "\n회원번호: ${user.id}"
                + "\n이메일: ${user.kakaoAccount?.email}"
                + "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"
        )

        retrofitService.postRequestSocialLogin("kakao", user.id.toString(), user.kakaoAccount?.profile?.nickname!!)
          .enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
              if (response.isSuccessful) {
                val basicResponse = response.body()!!
                Toast.makeText(this@SignUpActivity, resources.getString(R.string.welcome_user).replace(resources.getString(R.string.user),
                  basicResponse.data.user.nickname), Toast.LENGTH_SHORT).show()

                ContextUtil.setToken(this@SignUpActivity, basicResponse.data.token)
                GlobalData.loginUser = basicResponse.data.user

                startMainActivity()
              }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
              Log.d("onFailure", resources.getString(R.string.data_loading_failed))
            }
          })
      }
    }
  }

  private fun postRequestGoogleLogin() {
    resultLauncher =
      registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
          val signInGoogle = Auth.GoogleSignInApi.getSignInResultFromIntent(result.data!!)
          signInGoogle?.let {
            if (it.isSuccess) {
              it.signInAccount?.displayName
              it.signInAccount?.email
              Log.e("Value", it.signInAccount?.email!!)
            } else
              Log.e("Value", "error")

            retrofitService.postRequestSocialLogin("google", it.signInAccount?.id.toString(), it.signInAccount?.displayName.toString())
              .enqueue(object : Callback<BasicResponse> {
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                  if (response.isSuccessful) {
                    val basicResponse = response.body()!!
                    Toast.makeText(this@SignUpActivity, resources.getString(R.string.welcome_user).replace(resources.getString(R.string.user),
                      basicResponse.data.user.nickname), Toast.LENGTH_SHORT).show()

                    ContextUtil.setToken(this@SignUpActivity, basicResponse.data.token)
                    GlobalData.loginUser = basicResponse.data.user

                    startMainActivity()
                  }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                  Log.d("onFailure", resources.getString(R.string.data_loading_failed))
                }
              })
          }
        }
      }
  }

  private fun postRequestFacebookLogin() {
    callbackManager = CallbackManager.Factory.create()
    LoginManager.getInstance()
      .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
        override fun onSuccess(result: LoginResult?) {
          Log.d(resources.getString(R.string.facebook_login), result!!.accessToken.token)

          val graphApiRequest =
            GraphRequest.newMeRequest(result.accessToken) { jsonObj, _ ->
              Log.d(resources.getString(R.string.info_request), jsonObj.toString())

              val name = jsonObj!!.getString("name")
              val id = jsonObj.getString("id")

              retrofitService.postRequestSocialLogin("facebook", id, name).enqueue(object : Callback<BasicResponse> {
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                  if (response.isSuccessful) {
                    val basicResponse = response.body()!!
                    Toast.makeText(this@SignUpActivity, resources.getString(R.string.welcome_user).replace(resources.getString(R.string.user),
                      basicResponse.data.user.nickname),
                      Toast.LENGTH_SHORT).show()

                    ContextUtil.setToken(this@SignUpActivity, basicResponse.data.token)
                    GlobalData.loginUser = basicResponse.data.user

                    startMainActivity()
                  }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                  Log.d("onFailure", resources.getString(R.string.data_loading_failed))
                }
              })
            }

          graphApiRequest.executeAsync()
        }

        override fun onCancel() {
          TODO("Not yet implemented")
        }

        override fun onError(error: FacebookException?) {
          Log.d("onError", resources.getString(R.string.data_loading_failed))
        }
      })
  }

  private fun startMainActivity() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
  }

}