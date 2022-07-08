package com.neppplus.gudocin_android.ui.reply

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.gudocin_android.databinding.AdapterReplyBinding
import com.neppplus.gudocin_android.model.reply.ReplyData
import com.neppplus.gudocin_android.util.TimeAgo

class ReplyRecyclerViewAdapter(
  private val mReplyList: List<ReplyData>
) : RecyclerView.Adapter<ReplyRecyclerViewAdapter.ReplyViewHolder>() {

  inner class ReplyViewHolder(private val binding: AdapterReplyBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: ReplyData) {
      Glide.with(itemView.context).load(data.user.profileImageURL).into(binding.imgProfile)
      binding.txtNickName.text = data.user.nickname
      binding.txtTime.text = TimeAgo.getTimeAgoString(data.createdAt)
      binding.txtReply.text = data.content
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReplyViewHolder {
    val binding = AdapterReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return ReplyViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
    holder.bind(mReplyList[position])
  }

  override fun getItemCount() = mReplyList.size

}