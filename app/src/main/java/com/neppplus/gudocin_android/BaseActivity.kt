package com.neppplus.gudocin_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.neppplus.gudocin_android.api.ServerAPI
import com.neppplus.gudocin_android.api.ServerAPIInterface

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context

    lateinit var apiService: ServerAPIInterface

    lateinit var btnBack: ImageView
    lateinit var btnCart: ImageView

    lateinit var searchBoxInActionBar: LinearLayout
    lateinit var txtCategoryNameInActionBar: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        val retrofit = ServerAPI.getRetrofit(mContext)
        apiService = retrofit.create(ServerAPIInterface::class.java)

        supportActionBar?.let {
            setCustomActionBar()
        }
    }

    fun setCustomActionBar() {
//        기본 액션바 가져오기 -> 액션바는 무조건 있다고 전제
        val defActionBar = supportActionBar!!
        Log.d("액션바", "설정으로 들어옴")

//        이 액션바를 커스텀 모드로 변경
        defActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

//        실제 커스텀뷰를 어떤 xml 인지 설정
        defActionBar.setCustomView(R.layout.custom_action_bar)

//        좌우 여백 제거 : ToolBar 소환 -> 여백값 세팅
        val toolBar = defActionBar.customView.parent as Toolbar
        toolBar.setContentInsetsAbsolute(0, 0)

//       (액션바의 커스텀뷰에) 추가된 UI 요소들을 멤버변수에 연결
        btnBack = defActionBar.customView.findViewById(R.id.btnBack)
        btnCart = defActionBar.customView.findViewById(R.id.btnCart)
        searchBoxInActionBar = defActionBar.customView.findViewById(R.id.SearchBoxInActionBar)
        txtCategoryNameInActionBar =
            defActionBar.customView.findViewById(R.id.txtCategoryNameInActionBar)

        btnBack.setOnClickListener {
            finish()
        }

        btnCart.setOnClickListener {
            val myIntent = Intent(mContext, CartListActivity::class.java)
            startActivity(myIntent)
        }

        searchBoxInActionBar.setOnClickListener {
            val myIntent = Intent(mContext, SearchActivity::class.java)
            startActivity(myIntent)
        }
    }

    abstract fun setupEvents()
    abstract fun setValues()

}