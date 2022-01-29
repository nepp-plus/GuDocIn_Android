package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.mancj.materialsearchbar.MaterialSearchBar
import com.neppplus.gudocin_android.adapters.SuggestListAdapter
import com.neppplus.gudocin_android.databinding.ActivitySearchBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : BaseActivity() {

    lateinit var binding: ActivitySearchBinding
    var mSuggestList = ArrayList<ProductData>()
    lateinit var mSuggestListAdapter: SuggestListAdapter
    val mTotalProductList = ArrayList<ProductData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.btnCategriesEat.setOnClickListener {
            val myIntent = Intent(mContext, EatCategoryListActivity::class.java)
            startActivity(myIntent)
        }
        binding.btnCategriesWear.setOnClickListener {
            val myIntent = Intent(mContext, WearCategoryListActivity::class.java)
            myIntent.putExtra("Large_category_id", 1)
            startActivity(myIntent)
        }
        binding.btnCategriesLife.setOnClickListener {
            val myIntent = Intent(mContext, LifeCategoryListActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        getProductListFromServer()

        binding.searchView.setMaxSuggestionCount(5)
        binding.searchView.setHint("생활에 필요한 구독을 검색하세요")
        binding.searchView.setCardViewElevation(10)

        mSuggestListAdapter =
            SuggestListAdapter(mContext, LayoutInflater.from(mContext), mSuggestList)
        mSuggestListAdapter.setSuggestions(mSuggestList)
        binding.searchView.setCustomSuggestionAdapter(mSuggestListAdapter)

        binding.searchView.setOnSearchActionListener(object :
            MaterialSearchBar.OnSearchActionListener {
            override fun onSearchStateChanged(enabled: Boolean) {
            }

            override fun onSearchConfirmed(text: CharSequence?) {
                Log.d("검색어", binding.searchView.getText())
                val searchText = binding.searchView.text
                for (product in mTotalProductList) {
                    if (product.name.contains(searchText)) {
                        mSuggestList.add(product)
                    }
                }
            }

            override fun onButtonClicked(buttonCode: Int) {
//            if로 상품이 있으면 상품 띄워주고 없으면 없습니다 띄워주기
                val myIntent = Intent(mContext, ProductItemDetailActivity::class.java)
//                    myIntent.putExtra("product_id",)
                startActivity(myIntent)
            }
        })

        binding.searchView.addTextChangeListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("LOG_TAG", " view text changed " + binding.searchView.getText())
                val searchText = binding.searchView.text
                mSuggestList.clear()

                if (searchText == "") {
//                    검색어가 없을 때
                    mSuggestList.addAll(mTotalProductList)
                } else {
//                    검색어가 있으면 이름이 검색되는 상품만 표시
                    for (product in mTotalProductList) {
                        if (product.name.contains(searchText)) {
                            mSuggestList.add(product)
                        }
                    }
                }
                mSuggestListAdapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    // 추천으로 띄워줄 아이템 API 로 호출해서 가져오기
    fun getProductListFromServer() {
        apiService.getRequestProductList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mSuggestList.clear()
                    mSuggestList.addAll(br.data.products)

                    mTotalProductList.clear()
                    mTotalProductList.addAll(br.data.products)

                    mSuggestListAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}
