package com.neppplus.colosseum_retrofit_20220812.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.colosseum_retrofit_20220812.R
import com.neppplus.colosseum_retrofit_20220812.datas.ReplyData
import java.text.SimpleDateFormat
import java.util.*

class ReplyRecyclerAdapter(
    val mContext : Context, val mList : List<ReplyData>
) : RecyclerView.Adapter<ReplyRecyclerAdapter.MyViewHolder>() {

    inner class MyViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        fun bind (item : ReplyData) {
            val nickTxt = itemView.findViewById<TextView>(R.id.nickTxt)
            val sideTxt = itemView.findViewById<TextView>(R.id.sideTxt)
            val timeTxt = itemView.findViewById<TextView>(R.id.timeTxt)
            val contentTxt = itemView.findViewById<TextView>(R.id.contentTxt)
            val replyCountTxt = itemView.findViewById<TextView>(R.id.replyCountTxt)
            val likeCountTxt = itemView.findViewById<TextView>(R.id.likeCountTxt)
            val dislikeCountTxt = itemView.findViewById<TextView>(R.id.dislikeCountTxt)

            nickTxt.text = item.user.nick_name
            sideTxt.text = "(${item.selected_side.title})"

            var calendar = Calendar.getInstance()  // 현재 시간이 기록된다(getInstance)
//            서버가 내려주는 양식으로 분석하기 위한 SimpleDateFormat (parse 용 : String > Date)
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            calendar.time = sdf.parse(item.created_at)

//            우리가 가공할 양식을 위한 SimpleDateFormat (format용 : Date > String)
            val formatter = SimpleDateFormat("yy.MM.dd (E) a h:mm")

            timeTxt.text = formatter.format(calendar.time)  //  "yyyy-MM-dd HH:mm:ss"  => yy.MM.dd h:mm

            contentTxt.text = item.content

            replyCountTxt.text = "답글 : ${ item.reply_count }표"
            likeCountTxt.text = "좋아요 : ${ item.like_count }표"
            dislikeCountTxt.text = "싫어요 : ${ item.dislike_count }표"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.reply_list_item, parent, false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}