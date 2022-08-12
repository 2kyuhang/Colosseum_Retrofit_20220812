package com.neppplus.colosseum_retrofit_20220812.datas

import java.io.Serializable

data class TopicData(
    val id : Int,
    val title : String,
    val img_url : String,
    val reply_count : Int,  // String, 데이터 클래스 => null값으로 기본값
    val sides : List<SideData>,
    val replies : List<ReplyData>,
    val my_side : SideData,
    val my_side_id : Int
) : Serializable
