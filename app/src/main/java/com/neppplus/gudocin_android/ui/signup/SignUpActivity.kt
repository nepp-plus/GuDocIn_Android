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
import com.neppplus.gudocin_android.util.ContextUtil
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : BaseActivity() {

  lateinit var binding: ActivitySignUpBinding

  private lateinit var callbackManager: CallbackManager

  private lateinit var resultLauncher: ActivityResultLauncher<Intent>

  private var isPasswordLengthOk = false

  private var isPasswordSame = false

  var isDuplicatedOk = false

  private val googleSignInIntent by lazy {
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(getString(R.string.google_default_client_id)).requestEmail().build()
    GoogleSignIn.getClient(this, gso).signInIntent
  }

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
            Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_login_failed))
          } else if (token != null) {
            Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_login_success))
            Log.e(resources.getString(R.string.kakao_login), token.accessToken)
            getInfoFromKakao()
          }
        }
      } else {
        UserApiClient.instance.loginWithKakaoAccount(mContext) { token, error ->
          if (error != null) {
            Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_login_failed))
          } else if (token != null) {
            Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_login_success))
            Log.e(resources.getString(R.string.kakao_login), token.accessToken)
            getInfoFromKakao()
          }
        }
      }
    }

    binding.btnGoogleLogin.setOnClickListener {
      resultLauncher.launch(googleSignInIntent)
    }

    binding.btnFacebookLogin.setOnClickListener {
      LoginManager.getInstance()
        .logInWithReadPermissions(this, listOf("public_profile"))
    }

    binding.edtPassword.addTextChangedListener {
      if (it.toString().length >= 8) {
        binding.txtCheckPassword1.text = resources.getString(R.string.password_pass)
        isPasswordLengthOk = true
      } else {
        binding.txtCheckPassword2.text = resources.getString(R.string.password_length)
        isPasswordLengthOk = false
      }
      isPasswordSame = comparePasswords()
    }

    binding.edtConfirmPassword.addTextChangedListener {
      isPasswordSame = comparePasswords()
    }

    binding.edtNickname.addTextChangedListener {
      binding.txtCheckNickname.text = resources.getString(R.string.nickname_duplicated)
      isDuplicatedOk = false
    }

    binding.btnCheckNickname.setOnClickListener {
      val nickname = binding.edtNickname.text.toString()
      if (nickname.isEmpty()) {
        Toast.makeText(mContext, resources.getString(R.string.nickname_input), Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      apiService.getRequestDuplicateCheck("NICK_NAME", nickname).enqueue(object : Callback<BasicResponse> {
        override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
          if (response.isSuccessful) {
            binding.txtCheckNickname.text = resources.getString(R.string.nickname_pass)
            isDuplicatedOk = true
          } else {
            binding.txtCheckNickname.text = resources.getString(R.string.nickname_exist)
            isDuplicatedOk = false
          }
        }

        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
      })
    }

    binding.btnSignUp.setOnClickListener {
      val email = binding.edtEmail.text.toString()
      val password = binding.edtPassword.text.toString()
      val phone = binding.edtPhoneNum.text.toString()
      val nickname = binding.edtNickname.text.toString()

      if (email.isEmpty()) {
        Toast.makeText(mContext, resources.getString(R.string.email_input), Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }
      if (!isPasswordLengthOk) {
        Toast.makeText(mContext, resources.getString(R.string.password_length), Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }
      if (!isPasswordSame) {
        Toast.makeText(mContext, resources.getString(R.string.password_incorrect), Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }
      if (phone.isEmpty()) {
        Toast.makeText(mContext, resources.getString(R.string.phone_num_input), Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }
      if (nickname.isEmpty()) {
        Toast.makeText(mContext, resources.getString(R.string.nickname_input), Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }
      if (!isDuplicatedOk) {
        Toast.makeText(mContext, resources.getString(R.string.nickname_duplicated), Toast.LENGTH_SHORT).show()
        return@setOnClickListener
      }

      apiService.putRequestSignUp(email, password, nickname, phone).enqueue(object : Callback<BasicResponse> {
        override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
          if (response.isSuccessful) {
            val br = response.body()!!
            Log.d(resources.getString(R.string.fcm_token), br.data.token)

            val signUpUserNickname = br.data.user.nickname
            Toast.makeText(mContext, resources.getString(R.string.congratulation_user).replace("{user}", signUpUserNickname), Toast.LENGTH_SHORT)
              .show()
          }

          val myIntent = Intent(mContext, MainActivity::class.java)
          startActivity(myIntent)
          finish()
        }

        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
      })
    }
  }

  private fun comparePasswords(): Boolean {
    val password = binding.edtPassword.text.toString()
    val confirmPassword = binding.edtConfirmPassword.text.toString()

    return if (password == confirmPassword) {
      binding.txtCheckPassword2.text = resources.getString(R.string.password_correct)
      true
    } else {
      binding.txtCheckPassword2.text = resources.getString(R.string.password_incorrect)
      false
    }
  }

  override fun setValues() {
    getGoogle()
    getFacebook()
  }

  private fun getInfoFromKakao() {
    UserApiClient.instance.me { user, error ->
      if (error != null) {
        Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_request_failed), error)
      } else if (user != null) {
        Log.i(
          resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_request_success) +
              "\n회원번호: ${user.id}" +
              "\n이메일: ${user.kakaoAccount?.email}" +
              "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"
        )

        apiService.postRequestSocialLogin("kakao", user.id.toString(), user.kakaoAccount?.profile?.nickname!!)
          .enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
              if (response.isSuccessful) {
                val br = response.body()!!
                Toast.makeText(mContext, resources.getString(R.string.welcome_user).replace("{user}", br.data.user.nickname), Toast.LENGTH_SHORT)
                  .show()

                ContextUtil.setToken(mContext, br.data.token)
                GlobalData.loginUser = br.data.user

                val myIntent = Intent(mContext, MainActivity::class.java)
                startActivity(myIntent)
                finish()
              }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
          })
      }
    }
  }

  private fun getGoogle() {
    resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
      if (result.resultCode == RESULT_OK) {
        val signInGoogle = Auth.GoogleSignInApi.getSignInResultFromIntent(result.data!!)
        signInGoogle?.let {
          if (it.isSuccess) {
            it.signInAccount?.displayName
            it.signInAccount?.email
            Log.e("Value", it.signInAccount?.email!!)
          } else
            Log.e("Value", "error") // 에러 처리

          apiService.postRequestSocialLogin("google", it.signInAccount?.id.toString(), it.signInAccount?.displayName.toString())
            .enqueue(object : Callback<BasicResponse> {
              override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                  val br = response.body()!!
                  Toast.makeText(mContext, resources.getString(R.string.welcome_user).replace("{user}", br.data.user.nickname), Toast.LENGTH_SHORT)
                    .show()

                  ContextUtil.setToken(mContext, br.data.token)
                  GlobalData.loginUser = br.data.user

                  val myIntent = Intent(mContext, MainActivity::class.java)
                  startActivity(myIntent)
                  finish()
                }
              }

              override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
            })
        }
      }
    }
  }

  private fun getFacebook() {
    callbackManager = CallbackManager.Factory.create()
    LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
      override fun onSuccess(result: LoginResult?) {
        Log.d(resources.getString(R.string.facebook_login), result!!.accessToken.token)
        // Before Convert to Lambda
        /* val graphApiRequest = GraphRequest.newMeRequest(result.accessToken, object : GraphRequest.GraphJSONObjectCallback {
          override fun onCompleted(`object`: JSONObject?, response: GraphResponse?) {
          }
        }) */

        // After Convert to Lambda
        val graphApiRequest = GraphRequest.newMeRequest(result.accessToken) { jsonObj, _ ->
          Log.d(resources.getString(R.string.info_request), jsonObj.toString())

          val name = jsonObj!!.getString("name")
          val id = jsonObj.getString("id")

          apiService.postRequestSocialLogin("facebook", id, name).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
              if (response.isSuccessful) {
                val br = response.body()!!
                Toast.makeText(mContext, resources.getString(R.string.welcome_user).replace("{user}", br.data.user.nickname), Toast.LENGTH_SHORT)
                  .show()

                ContextUtil.setToken(mContext, br.data.token)
                GlobalData.loginUser = br.data.user

                val myIntent =
                  Intent(mContext, MainActivity::class.java)
                startActivity(myIntent)
                finish()
              }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
          })
        }
        graphApiRequest.executeAsync()
      }

      override fun onCancel() {}

      override fun onError(error: FacebookException?) {}
    })
  }

}