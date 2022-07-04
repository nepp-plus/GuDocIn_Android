package com.neppplus.gudocin_android.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import com.neppplus.gudocin_android.databinding.ActivityLoginBinding
import com.neppplus.gudocin_android.model.BasicResponse
import com.neppplus.gudocin_android.model.user.GlobalData
import com.neppplus.gudocin_android.util.ContextUtil
import com.neppplus.gudocin_android.ui.base.BaseActivity
import com.neppplus.gudocin_android.ui.main.MainActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {

  lateinit var binding: ActivityLoginBinding

  private lateinit var callbackManager: CallbackManager

  private lateinit var resultLauncher: ActivityResultLauncher<Intent>

  private val googleSignInIntent by lazy {
    val gso =
      GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.google_default_client_id))
        .requestEmail()
        .build()
    GoogleSignIn.getClient(this, gso).signInIntent
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
    binding.activity = this@LoginActivity
    setupEvents()
    setValues()
  }

  override fun setupEvents() {}

  override fun setValues() {
    getGoogle()
    getFacebook()
  }

  fun generalLogin(view: View) {
    val inputEmail = binding.edtEmail.text.toString()
    val inputPw = binding.edtPassword.text.toString()

    apiService.postRequestLogin(inputEmail, inputPw).enqueue(object : Callback<BasicResponse> {
      override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
        if (response.isSuccessful) {
          val basicResponse = response.body()!!
          val userNickname = basicResponse.data.user.nickname
          Toast.makeText(mContext, resources.getString(R.string.welcome_user).replace("{user}", userNickname), Toast.LENGTH_SHORT).show()

          ContextUtil.setToken(mContext, basicResponse.data.token)
          GlobalData.loginUser = basicResponse.data.user

          getAutoLogin()
          startMain()
        } else {
          val errorJson = JSONObject(response.errorBody()!!.string())
          Log.d(resources.getString(R.string.error_case), errorJson.toString())

          val message = errorJson.getString("message")
          Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
        }
      }

      override fun onFailure(call: Call<BasicResponse>, t: Throwable) {}
    })
  }

  fun kakaoLogin(view: View) {
    if (UserApiClient.instance.isKakaoTalkLoginAvailable(mContext)) {
      UserApiClient.instance.loginWithKakaoTalk(mContext) { token, error ->
        if (error != null) {
          Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_login_failed))
        } else if (token != null) {
          Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_login_success))
          Log.e(resources.getString(R.string.kakao_login), token.accessToken)
          getKakao()
        }
      }
    } else {
      UserApiClient.instance.loginWithKakaoAccount(mContext) { token, error ->
        if (error != null) {
          Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_login_failed))
        } else if (token != null) {
          Log.e(resources.getString(R.string.kakao_login), resources.getString(R.string.kakao_login_success))
          Log.e(resources.getString(R.string.kakao_login), token.accessToken)
          getKakao()
        }
      }
    }
  }

  fun googleLogin(view: View) {
    resultLauncher.launch(googleSignInIntent)
  }

  fun facebookLogin(view: View) {
    LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile"))
  }

  private fun getKakao() {
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

                getAutoLogin()
                startMain()
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

                  getAutoLogin()
                  startMain()
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

                getAutoLogin()
                startMain()
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

  fun startMain() {
    val myIntent = Intent(mContext, MainActivity::class.java)
    startActivity(myIntent)
    finish()
  }

  fun getAutoLogin() {
    binding.checkAutoLogin.isChecked = ContextUtil.getAutoLogin(mContext)
  }

  fun setAutoLogin(buttonView: CompoundButton?, isChecked: Boolean) {
    Log.d(resources.getString(R.string.checkbox_change), isChecked.toString())
    ContextUtil.setAutoLogin(mContext, isChecked)
  }

}