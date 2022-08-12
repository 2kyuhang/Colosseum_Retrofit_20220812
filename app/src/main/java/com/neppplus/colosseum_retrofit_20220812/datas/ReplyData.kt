package com.neppplus.colosseum_retrofit_20220812.datas

import java.io.Serializable

data class ReplyData (
    val id : Int,
    val content : String,
    val topic_id : Int,
    val side_id : Int,
    val user : UserData,
    val selected_side : SideData,
    val like_count : Int,
    val dislike_count : Int,
    val my_like : Boolean,
    val my_dislike : Boolean,
    val reply_count : Boolean
        ) : Serializable