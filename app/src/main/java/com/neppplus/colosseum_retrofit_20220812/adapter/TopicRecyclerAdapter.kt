package com.neppplus.colosseum_retrofit_20220812.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.colosseum_retrofit_20220812.R
import com.neppplus.colosseum_retrofit_20220812.datas.TopicData

class TopicRecyclerAdapter(
    val mContext : Context, val mList : List<TopicData>
) : RecyclerView.Adapter<TopicRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        fun bind (item : TopicData) {
            val titleTxt = itemView.findViewById<TextView>(R.id.titleTxt)
            val replyCountTxt = itemView.findViewById<TextView>(R.id.replyCountTxt)
            val backgroundImg = itemView.findViewById<ImageView>(R.id.backgroundImg)

            titleTxt.text = item.title
            replyCountTxt.text = "현재 댓글 수 : ${item.reply_count}개"
            Glide.with(mContext).load(item.imgUrl).into(backgroundImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.topic_list_item, parent, false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}