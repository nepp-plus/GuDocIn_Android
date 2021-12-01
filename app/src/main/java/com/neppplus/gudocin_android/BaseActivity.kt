package com.neppplus.gudocin_android

import android.content.Context
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.neppplus.gudocin_android.api.ServerAPI
import com.neppplus.gudocin_android.api.ServerAPIInterface

abstract class BaseActivity() : AppCompatActivity() {

    lateinit var mContext: Context

    lateinit var apiService: ServerAPIInterface

    lateinit var btnBack : ImageView
    lateinit var btnBell : ImageView
    lateinit var btnBasket : ImageView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this

        val retrofit = ServerAPI.getRetrofit(mContext)
        apiService = retrofit.create(ServerAPIInterface::class.java)

        supportActionBar?.let {

//            supportActionBar 이 변수가 null 아닐때만 해달라는 코드.
            setCustomActionBar()


        }


    }

    fun setCustomActionBar() {

//        기본 액션바 가져오기 -> 액션바는 무조건 있다고 전제.
        val defActionBar = supportActionBar!!

//        이 액션바를 커스텀 모드로 변경
        defActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

//        실제 커스텀뷰를 어떤 xml인지 설정
        defActionBar.setCustomView(R.layout.custom_action_bar)

//        좌우 여백 제거 : ToolBar 소환 -> 여백값 세팅
        val toolBar =  defActionBar.customView.parent as Toolbar
        toolBar.setContentInsetsAbsolute(0, 0)


//       (액션바의 커스텀뷰에) 추가된 UI요소들을 멤버변수에 연결
        btnBack = defActionBar.customView.findViewById(R.id.btnBack)
        btnBell = defActionBar.customView.findViewById(R.id.btnBell)
        btnBasket = defActionBar.customView.findViewById(R.id.btnBasket)

//        모든 화면 공통 이벤트처리
        btnBack.setOnClickListener {

//            뒤로가기 : 무조건 지금 화면 종료
            finish()
        }

//        프로필 설정 화면 이동




    }





    abstract fun setupEvents()
    abstract fun setValues()

}