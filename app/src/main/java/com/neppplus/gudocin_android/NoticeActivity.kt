package com.neppplus.gudocin_android

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class NoticeActivity : AppCompatActivity() {

    lateinit var imgBack: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.let {

            setCustomActionBar()

        }

    }

    fun setCustomActionBar() {

        val defActionBar = supportActionBar!!

        defActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

        defActionBar.setCustomView(R.layout.notice_action_bar)

        val toolBar = defActionBar.customView.parent as Toolbar
        toolBar.setContentInsetsAbsolute(0, 0)

        imgBack = defActionBar.customView.findViewById(R.id.imgBack)

        imgBack.setOnClickListener {

            finish()
        }

    }

}

