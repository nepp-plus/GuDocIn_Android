package com.neppplus.gudocin_android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.datas.ReplyData
import com.neppplus.gudocin_android.utils.TimeAgoUtil

class ReplyListAdapter(
    val mContext: Context,
    val resId: Int,
    val mList: List<ReplyData>
) : ArrayAdapter<ReplyData>(mContext, resId, mList) {

    val mInflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if (tempRow == null) {
            tempRow = mInflater.inflate(R.layout.reply_list_item, null)
        }
        val row = tempRow!!
        val data = mList[position]

        val imgProfile = row.findViewById<ImageView>(R.id.imgProfile)
        val txtNickName = row.findViewById<TextView>(R.id.txtNickName)
        val txtReplyTime = row.findViewById<TextView>(R.id.txtReplyTime)
        val txtReply = row.findViewById<TextView>(R.id.txtReply)

        Glide.with(mContext).load(data.user.profileImageURL).into(imgProfile)
        txtNickName.text = data.user.nickname
        txtReplyTime.text = TimeAgoUtil.getTimeAgoString(data.createdAt)
        txtReply.text = data.content

        return row
    }

}