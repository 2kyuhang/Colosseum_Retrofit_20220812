package com.neppplus.colosseum_retrofit_20220812.datas

import java.io.Serializable

data class SideData(
    val id : Int,
    val topic_id : Int,
    val title : String,
    val vote_count : Int
) : Serializable
