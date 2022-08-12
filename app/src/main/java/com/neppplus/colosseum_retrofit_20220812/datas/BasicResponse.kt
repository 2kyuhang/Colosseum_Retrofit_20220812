package com.neppplus.colosseum_retrofit_20220812.datas

class BasicResponse (
    val code : Int,
    val message : String,
    val data : DataResponse
        ) {

    override fun toString(): String {
        return "BasicResponse(code = ${this.code}, message = ${this.message}, data = ${this.data}"
    }

}