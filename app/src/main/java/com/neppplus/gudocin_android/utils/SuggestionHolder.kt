package com.neppplus.gudocin_android.utils

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R

class SuggestionHolder(view: View) : RecyclerView.ViewHolder(view) {



    val title = view.findViewById<TextView>(R.id.txtProductName)

}

