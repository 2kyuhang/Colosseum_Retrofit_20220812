package com.neppplus.colosseum_retrofit_20220812.datas

//  data class는 자동으로 toString이 오버라이드되어있다.
data class DataResponse (
    val token : String,
    val user : UserData
        ) {
}