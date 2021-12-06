package com.neppplus.gudocin_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.SmallCategoriesListAdapter
import com.neppplus.gudocin_android.databinding.FragmentCategoriesBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.SmallCategoriesData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesFragment : BaseFragment() {

    lateinit var binding: FragmentCategoriesBinding

    var mSmallCategoriesList = ArrayList<SmallCategoriesData>()
    lateinit var mSmallcateoriesListAdapter : SmallCategoriesListAdapter
    var mLargeCategoryId = 2

// 기대효과  장점  공부한 내용///여기에 래이아웃 만들어서 스몰 카테고리 만드는 코드 추가되어야 함   -> 아래 리뷰 리스트 리프레시 하는 코드도 추가되어야 함
//    받아오는 카테고리 데이터 리스트로 해야 됨 다시보기

//   eatCategoryId = 2 / wearCategoryId = 1 / lifeCategoryId =3


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_categories,container,false)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.btnCategriesEat.setOnClickListener {
            mLargeCategoryId = 2
            getSmallCategoryListFromServer()

        }

        binding.btnCategriesLife.setOnClickListener {
            mLargeCategoryId = 3
            getSmallCategoryListFromServer()

        }


        binding.btnCategriesWear.setOnClickListener {
            mLargeCategoryId = 1
            getSmallCategoryListFromServer()

        }



    }



    override fun setValues() {
        getSmallCategoryListFromServer()
        mSmallcateoriesListAdapter = SmallCategoriesListAdapter(mContext,mSmallCategoriesList)


    }


    fun getSmallCategoryListFromServer(){
        apiService.getRequestSmallCategoryDependOnLarge(mLargeCategoryId).enqueue(object :Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful ){

                    binding.smalllCategoryList.removeAllViews()

                    val br = response.body()!!

                    mSmallCategoriesList.clear()
                    mSmallCategoriesList.addAll(br.data.small_categories)
                    mSmallcateoriesListAdapter.notifyDataSetChanged()

                    //                    추가한 카테고리 하나하나에 대한 view 생성

                    for (sc in mSmallCategoriesList){
                        val view = LayoutInflater.from(mContext).inflate(R.layout.small_categories_item,null)
                        val txtSmallCategoryName = view.findViewById<TextView>(R.id.txtSmallCategoryName)

                        txtSmallCategoryName.text = sc.name

//                        view.setOnClickListener {
//                            mClickedSmallCategoryNum = sc.id
//                            getProductListInSmallCategoryFromServer()
//                        }

                        binding.smalllCategoryList.addView(view)

                    }


                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })


    }


}

