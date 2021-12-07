package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.mancj.materialsearchbar.MaterialSearchBar
import com.neppplus.gudocin_android.adapters.SuggestListAdapter
import com.neppplus.gudocin_android.databinding.ActivitySearchBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : BaseActivity() {

    lateinit var binding: ActivitySearchBinding

    val searchResultListView = findViewById<ListView>(R.id.searchResultListView)
    val searchView = findViewById<MaterialSearchBar>(R.id.searchView)
    var mSugestList = arrayOf( "간식", "다이어트 용품")

    lateinit var mSugestListAdapter : SuggestListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)

        setupEvents()
        setValues()

    }


    override fun setupEvents() {

        binding.btnCategriesEat.setOnClickListener {
            val myIntent = Intent(mContext,EatCategoryListActivity::class.java)

            startActivity(myIntent)
        }

        binding.btnCategriesWear.setOnClickListener {
            val myIntent = Intent(mContext,WearCategoryListActivity::class.java)
            myIntent.putExtra("Large_category_id",1)
            startActivity(myIntent)
        }

        binding.btnCategriesLife.setOnClickListener {
            val myIntent = Intent(mContext,LifeCategoryListActivity::class.java)

            startActivity(myIntent)
        }
    }

    override fun setValues() {


        mSugestListAdapter = SuggestListAdapter(this,R.layout.simple_list_item_1,mSugestList)
        searchResultListView.adapter = mSugestListAdapter

        searchResultListView.visibility = View.INVISIBLE

        searchView.setOnSearchActionListener(object :MaterialSearchBar.OnSearchActionListener{
            override fun onSearchStateChanged(enabled: Boolean) {
                if(enabled){
                    searchResultListView.visibility = View.VISIBLE
                }else{
                    searchResultListView.visibility = View.INVISIBLE
                }

            }

            override fun onSearchConfirmed(text: CharSequence?) {

            }

            override fun onButtonClicked(buttonCode: Int) {

            }

        })

        searchView.addTextChangeListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
        searchResultListView.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 아이템이 가지고 있는 대분류 카테고리로 그에 맞는 ListActivity로 보내줘야 함
                val myIntent = Intent(mContext,EatCategoryListActivity::class.java)
                startActivity(myIntent)
            }

        })
        searchView.setHint("생활에 필요한 구독을 검색하세요")




    }
    // 추천으로 띄워줄 아이템  API 로 호출해서 가져오기

    fun getProductListFromServer(){
        apiService.getRequestProductList().enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })
    }




}
