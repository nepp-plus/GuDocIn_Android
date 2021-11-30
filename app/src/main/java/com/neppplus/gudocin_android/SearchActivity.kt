package com.neppplus.gudocin_android

import android.app.DownloadManager
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.databinding.ActivitySearchBinding
import retrofit2.http.Query

class SearchActivity : BaseActivity() {

    lateinit var binding: ActivitySearchBinding
//    val mSearchView = binding.searchView
//    val CharSequence = mSearchView.getQuery()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        setupEvents()
        setValues()


    }

    override fun setupEvents() {

    }

    override fun setValues() {


        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
//                doMySearch(query)
            }
        }





//        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
////                검색버튼이 눌렸을 때
//                Log.d("검색어",query.toString())
//                Toast.makeText(mContext, "검색하기", Toast.LENGTH_SHORT).show()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
////                  검색어가 변경 되었을 때
//
//                return false
//            }
//
//        })


    }
    fun doMySearch(){

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