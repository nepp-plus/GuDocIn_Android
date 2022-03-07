package com.neppplus.gudocin_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.databinding.ActivityPaymentBinding
import com.neppplus.gudocin_android.datas.BasicResponse
import com.neppplus.gudocin_android.datas.CardData
import com.neppplus.gudocin_android.dummy.DummyActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PaymentActivity : BaseActivity() {

    lateinit var binding: ActivityPaymentBinding

    var mSelectedCardInfo: CardData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        setupEvents()
        setValues()

        /* if (intent.hasExtra("nickname")) {
            binding.txtShipmentNickname.text = intent.getStringExtra("nickname")
        }
        if (intent.hasExtra("shipment")) {
            binding.txtShipmentAddress.text = intent.getStringExtra("shipment")
        } */
    }

    override fun setupEvents() {
        binding.shipmentOptionsSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    p0: AdapterView<*>?,
                    p1: View?,
                    position: Int,
                    p3: Long
                ) {
                    binding.edtShipmentOptions.visibility = if (position == 4) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }

        /* binding.btnAddCardInfo.setOnClickListener {
            val myIntent = Intent(mContext, EditCardInfoActivity::class.java)
            startActivity(myIntent)
        } */

        val resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == RESULT_OK) {
                    val dataIntent = it.data!!
                    mSelectedCardInfo =
                        dataIntent.getSerializableExtra("card") as CardData
                    setSelectedCardInfoToUi()
                }
            }
        )

        binding.btnCardSelect.setOnClickListener {
            val myIntent = Intent(mContext, CardInfoListActivity::class.java)
            resultLauncher.launch(myIntent)
        }

        /* binding.btnShipmentSelect.setOnClickListener {
            val myIntent = Intent(mContext, EditShipmentInfoActivity::class.java)
            startActivity(myIntent)
        } */

        binding.btnPayment.setOnClickListener {
            val myIntent = Intent(mContext, DummyActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        getMyCardInfoList()
        btnExplore.visibility = View.GONE
        btnCart.visibility = View.GONE
    }

    private fun getMyCardInfoList() {
        apiService.getRequestCardInfoList().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    if (mSelectedCardInfo != null) {
                        setSelectedCardInfoToUi()
                    }
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

    private fun setSelectedCardInfoToUi() {
        val info = mSelectedCardInfo!!
        binding.txtCardNickname.text = info.cardNickname
        binding.txtCardNum.text = info.cardNum
    }

}