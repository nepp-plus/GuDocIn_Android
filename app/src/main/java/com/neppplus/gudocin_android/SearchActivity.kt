package com.neppplus.gudocin_android

import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.adapters.CategoriesAdapter
import com.neppplus.gudocin_android.databinding.ActivitySearchBinding
import com.neppplus.gudocin_android.datas.CategoriesData

class SearchActivity : BaseActivity() {

    lateinit var binding: ActivitySearchBinding
    val mSearchView = binding.searchView
    val CharSequence = mSearchView.getQuery()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        setupEvents()
        setValues()


    }

    override fun setupEvents() {

    }

    override fun setValues() {







        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
//                검색버튼이 눌렸을 때
                Log.d("검색어",query.toString())
                Toast.makeText(mContext, "검색하기", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
//                  검색어가 변경 되었을 때

                return false
            }

        })


    }

//    fun searchResult() {
//        mSearchView.setOnQueryTextListener(object :
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//
//            }
//
//            override fun onQueryTextChange(query: String?): Boolean {
//                //mAdapter!!.filter.filter(query)
//                return true
//            }
//        })
//    }


}