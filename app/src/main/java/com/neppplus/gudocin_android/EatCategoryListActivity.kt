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
import com.neppplus.gudocin_android.datas.SmallCategoriesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EatCategoryListActivity : BaseActivity() {



    lateinit var binding:ActivityEatCategoryListBinding

    val mSmallcategoryList = ArrayList<SmallCategoriesData>()
    lateinit var mSmallcategoryListAdapter : SmallCategoriesListAdapter
    var mLargeCategoryId = 2

    //   eatCategoryId = 2 / wearCategoryId = 1 / lifeCategoryId =3
    var mClickedSmallCategoryNum = 7

    val mProductList = ArrayList<ProductData>()
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


        getSmallCategoryListFromServer()
        mSmallcategoryListAdapter = SmallCategoriesListAdapter(mContext,mSmallcategoryList)
//        binding.smallcategoryRecyclerView.adapter = mSmallcategoryListAdapter
//        binding.smallcategoryRecyclerView.layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false)



        getProductListInSmallCategoryFromServer()

        mProductRecyclerAdapter = ProductRecyclerViewAdapter(mContext,mProductList)
        binding.productListRecyclerView.adapter = mProductRecyclerAdapter
        binding.productListRecyclerView.layoutManager = LinearLayoutManager(mContext)
//

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

//                    mSmallcategoryList.clear()
//                    mSmallcategoryList.addAll(response.body()!!.data.small_categories)
//                    mSmallcategoryListAdapter.notifyDataSetChanged()

                    for (sc in mSmallcategoryList){
                        val view = LayoutInflater.from(mContext).inflate(R.layout.small_categories_item,null)
                        val txtSmallCategoryName = view.findViewById<TextView>(R.id.txtSmallCategoryName)

                        txtSmallCategoryName.text = sc.name

                        view.setOnClickListener {
                            mClickedSmallCategoryNum = sc.id
                            getProductListInSmallCategoryFromServer()
                        }

                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })


    }

}