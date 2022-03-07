package com.neppplus.gudocin_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.adapters.CardInfoAdapter
import com.neppplus.gudocin_android.databinding.ActivityCardInfoListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.CardData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardInfoListActivity : BaseActivity() {

    lateinit var binding: ActivityCardInfoListBinding

    val mCardInfoList = ArrayList<CardData>()

    lateinit var mAdapter: CardInfoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_card_info_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        mAdapter = CardInfoAdapter(mContext, mCardInfoList)
        mAdapter.setOnItemClick(object : CardInfoAdapter.OnItemClick {
            override fun onItemClick(position: Int) {
                val clickedCardInfo = mCardInfoList[position]
                val resultIntent = Intent()
                resultIntent.putExtra("card", clickedCardInfo)
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        })
        btnAdd.setOnClickListener {
            val myIntent = Intent(mContext, EditCardInfoActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        binding.cardInfoRecyclerView.adapter = mAdapter
        binding.cardInfoRecyclerView.layoutManager = LinearLayoutManager(mContext)
        btnExplore.visibility = View.GONE
        btnCart.visibility = View.GONE
        btnAdd.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        getMyCardInfoList()
    }

    private fun getMyCardInfoList() {
        apiService.getRequestCardInfoList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mCardInfoList.clear()
                    mCardInfoList.addAll(br.data.cards)
                    mAdapter.notifyDataSetChanged()
                } else {

                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}