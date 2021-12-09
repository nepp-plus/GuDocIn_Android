package com.neppplus.gudocin_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.adapters.SaveMoneyViewPagerAdapter
import com.neppplus.gudocin_android.databinding.ActivityMySaveMoneyDetailBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.GlobalData
import com.neppplus.gudocin_android.datas.UserData
import com.neppplus.gudocin_android.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MySaveMoneyDetailActivity : BaseActivity() {

    lateinit var binding: ActivityMySaveMoneyDetailBinding

    lateinit var mAdapter: SaveMoneyViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_save_money_detail)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

//      뷰페이져,텝레이아웃 작업한 어댑터 가져와 Activity와 연결
        mAdapter = SaveMoneyViewPagerAdapter(supportFragmentManager)
        binding.saveMondyViewPager.adapter = mAdapter

        binding.saveMoneyTabLayout.setupWithViewPager(binding.saveMondyViewPager)






        apiService.getRequestMyInfo(


        ).enqueue(object : Callback<BasicResponse> {
            override fun onResponse(
                call: Call<BasicResponse>,
                response: Response<BasicResponse>
            ) {
                if(response.isSuccessful){

                    val br = response.body()!!
                  val inputTotalPoint =  br.data.user.point.toString()


                }
                    Toast.makeText(mContext, "현재 적립금 입니다.", Toast.LENGTH_SHORT).show()

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }



}