package com.neppplus.gudocin_android

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.adapters.ProductRecyclerViewAdapter
import com.neppplus.gudocin_android.adapters.SmallCategoriesListAdapter
import com.neppplus.gudocin_android.databinding.ActivityEatCategoryListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.ProductData
import com.neppplus.gudocin_android.datas.ReviewData
import com.neppplus.gudocin_android.datas.SmallCategoriesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EatCategoryListActivity : BaseActivity() {



    lateinit var binding:ActivityEatCategoryListBinding

    val mSmallcategoryList = ArrayList<SmallCategoriesData>()
    lateinit var mSmallcategoryListAdapter : SmallCategoriesListAdapter
    var mLargeCategoryId = 2

    var mClickedSmallCategoryNum = 7

    val mProductList = ArrayList<ProductData>()
    val mReviewsList = ArrayList<ReviewData>()
    lateinit var mProductRecyclerAdapter : ProductRecyclerViewAdapter




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_eat_category_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        binding.txtSelectedCategoryName.text = "먹는 구독"


        getSmallCategoryListFromServer()
        mSmallcategoryListAdapter = SmallCategoriesListAdapter(mContext,mSmallcategoryList)


        getProductListInSmallCategoryFromServer()
        mProductRecyclerAdapter = ProductRecyclerViewAdapter(mContext,mProductList)
        binding.productListRecyclerView.adapter = mProductRecyclerAdapter
        binding.productListRecyclerView.layoutManager = LinearLayoutManager(mContext)


    }

    fun getProductListInSmallCategoryFromServer(){

        apiService.getRequestSmallCategorysItemList(mClickedSmallCategoryNum).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful ){
                    mProductList.clear()
                    mProductList.addAll(response.body()!!.data.products)
                    mProductRecyclerAdapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

    fun getSmallCategoryListFromServer(){
        apiService.getRequestSmallCategoryDependOnLarge(mLargeCategoryId).enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful ){

                    binding.smalllCategoryList.removeAllViews()

                    val br = response.body()!!

                    mSmallcategoryList.clear()
                    mSmallcategoryList.addAll(br.data.small_categories)

//                    추가한 카테고리 하나하나에 대한 view 생성

                    for (sc in mSmallcategoryList){
                        val view = LayoutInflater.from(mContext).inflate(R.layout.small_categories_item,null)
                        val txtSmallCategoryName = view.findViewById<TextView>(R.id.txtSmallCategoryName)

                        txtSmallCategoryName.text = sc.name

                        view.setOnClickListener {
                            mClickedSmallCategoryNum = sc.id
                            getProductListInSmallCategoryFromServer()
                        }

                        binding.smalllCategoryList.addView(view)

                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })


    }

}