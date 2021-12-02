package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.ProductItemDetailActivity
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.ProductData

class ProductRecyclerViewAdapter(val mContext: Context, val mList:List<ProductData>): RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder>()  {


    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val rootLayout = view.findViewById<LinearLayout>(R.id.rootLayout)
        val txtProductName = view.findViewById<TextView>(R.id.txtProductName)
        val txtProductPrice = view.findViewById<TextView>(R.id.txtProductPrice)

        fun bind(data: ProductData){
            txtProductName.text = data.name
            txtProductPrice.text = data.getFormatedPrice()

            rootLayout.setOnClickListener{

                val myIntent = Intent(mContext,ProductItemDetailActivity::class.java)
                mContext.startActivity(myIntent)


            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.product_item,parent,false)
        return ProductViewHolder(row)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind( mList [position])
    }

    override fun getItemCount()= mList.size


}