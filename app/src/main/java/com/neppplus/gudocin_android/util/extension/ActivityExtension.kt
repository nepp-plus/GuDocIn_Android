package com.neppplus.gudocin_android.util.extension

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Activity Intent
 * Start-projections(*): 어떤 타입이 들어올 지 미리 알 수 없어도 그 타입을 안전하게 사용하고 싶을 때 사용
 */
fun AppCompatActivity.startActivityWithFinish(context: Context, activity: Class<*>) {
  startActivity(Intent(context, activity).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
  this.finish()
}

/**
 * Vertical RecyclerView & Horizontal RecyclerView
 */
fun RecyclerView.showVertical(context: Context) {
  this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
}

fun RecyclerView.showHorizontal(context: Context) {
  this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
}

