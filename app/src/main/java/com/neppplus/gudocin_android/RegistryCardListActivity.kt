package com.neppplus.gudocin_android

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.gudocin_android.adapters.CardRecyclerAdapter
import com.neppplus.gudocin_android.databinding.ActivityRegistryCardListBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.CardData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistryCardListActivity : BaseActivity() {

    lateinit var binding: ActivityRegistryCardListBinding

    val mCardList = ArrayList<CardData>()

    lateinit var mCardRecyclerAdapter: CardRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_registry_card_list)
        setupEvents()
        setValues()

//        binding.imgDropDownArrow.setOnClickListener {
//
//            if (binding.layoutExpand1.visibility == View.VISIBLE) {
//                binding.layoutExpand1.visibility = View.GONE
//                binding.imgDropDownArrow.animate().setDuration(200).rotation(180f)
//            } else {
//                binding.layoutExpand1.visibility = View.VISIBLE
//                binding.imgDropDownArrow.animate().setDuration(200).rotation(0f)
//            }
//
//        }

    }


    fun getCardListFromServer() {

        apiService.getRequestUserCardLookup().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful) {

                    val br = response.body()!!

                    mCardList.clear()
                    mCardList.addAll(br.data.cards)

                    mCardRecyclerAdapter.notifyDataSetChanged()

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

    override fun setupEvents() {

        binding.btnCardInfoRevise.setOnClickListener {

            val myIntent = Intent(mContext, RegistryCardReviseActivity::class.java)
            startActivity(myIntent)

            finish()

        }

        binding.btnRegistryNewCard.setOnClickListener {

            val myIntent = Intent(mContext, CardInfoActivity::class.java)
            startActivity(myIntent)

            finish()

        }

    }

    override fun setValues() {

        getCardListFromServer()

        mCardRecyclerAdapter = CardRecyclerAdapter(mContext, mCardList)

        binding.cardListRecyclerView.adapter = mCardRecyclerAdapter
        binding.cardListRecyclerView.layoutManager = LinearLayoutManager(mContext)

    }
}