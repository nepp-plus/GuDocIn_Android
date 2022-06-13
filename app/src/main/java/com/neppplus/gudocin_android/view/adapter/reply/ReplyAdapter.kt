package com.neppplus.gudocin_android.view.adapter.reply

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.R
import com.neppplus.gudocin_android.model.reply.ReplyData
import com.neppplus.gudocin_android.util.time.TimeAgoUtil

class ReplyAdapter(
  val mContext: Context,
  private val resId: Int,
  private val mList: List<ReplyData>
) : ArrayAdapter<ReplyData>(mContext, resId, mList) {

  private val mInflater: LayoutInflater = LayoutInflater.from(mContext)

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    var tempRow = convertView
    if (tempRow == null) {
      tempRow = mInflater.inflate(R.layout.adapter_reply, null)
    }
    val row = tempRow!!
    val data = mList[position]

    val imgProfile = row.findViewById<ImageView>(R.id.imgProfile)
    val txtNickName = row.findViewById<TextView>(R.id.txtNickName)
    val txtTime = row.findViewById<TextView>(R.id.txtTime)
    val txtReply = row.findViewById<TextView>(R.id.txtReply)

    Glide.with(mContext).load(data.user.profileImageURL).into(imgProfile)
    txtNickName.text = data.user.nickname
    txtTime.text = TimeAgoUtil.getTimeAgoString(data.createdAt)
    txtReply.text = data.content

    return row
  }

}