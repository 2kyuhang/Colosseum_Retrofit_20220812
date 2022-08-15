package com.neppplus.colosseum_retrofit_20220812.datas

class BasicResponse ( //여긴 데이터 전송시 왔다갔다하는 모든 변수들을 만든것!?
    val code : Int,
    val message : String,
    val data : DataResponse
        ) {

    override fun toString(): String {
        return "BasicResponse(code = ${this.code}, message = ${this.message}, data = ${this.data}"
    }

}