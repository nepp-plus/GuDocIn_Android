package com.neppplus.gudocin_android

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.databinding.ActivityProductItemDetailBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductItemDetailActivity : BaseActivity() {

    lateinit var binding: ActivityProductItemDetailBinding
    var selectedItemFromList = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this, R.layout.activity_product_item_detail)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {



    }

    override fun setValues() {

        getProductItemDetailFromServer()



    }

    fun getProductItemDetailFromServer(){
        apiService.getRequestProductDetail(selectedItemFromList).enqueue( object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {
                    val br = response.body()!!
                    binding.txtProductName.text= br.data.products.name
                    Glide.with(mContext).load(br.data.products.imageUrl).into(binding.imgProduct)

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }
}