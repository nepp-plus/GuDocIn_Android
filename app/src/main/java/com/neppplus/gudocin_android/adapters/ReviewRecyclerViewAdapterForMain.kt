package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.ReviewData
import java.text.SimpleDateFormat

class ReviewRecyclerViewAdapterForMain
    (val mContext:Context, val mList:List<ReviewData>)
    :RecyclerView.Adapter<ReviewRecyclerViewAdapterForMain.ReviewViewHolder>(){


    inner class ReviewViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){


        val txtProductPrice = itemView.findViewById<TextView>(R.id.txtProductPrice)
        val txtProductName   = itemView.findViewById<TextView>(R.id.txtProductName)
        val txtReviewerNickName   = itemView.findViewById<TextView>(R.id.txtReviewerNickName)

        fun bind(data: ReviewData ){
            txtProductName.text =data.product.name
            txtProductPrice.text=data.product.price.toString()
            txtReviewerNickName.text=data.user.nickname




        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {


        val  row  = LayoutInflater.from(mContext).inflate(R.layout.review_item_for_main,  parent, false)
        return  ReviewViewHolder( row )

    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind( mList [position])
    }

    override fun getItemCount()= mList.size

    }
