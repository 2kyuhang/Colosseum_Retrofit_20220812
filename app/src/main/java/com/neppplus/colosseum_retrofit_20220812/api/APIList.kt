package com.neppplus.colosseum_retrofit_20220812.api

import com.neppplus.colosseum_retrofit_20220812.datas.BasicResponse
import retrofit2.Call
import retrofit2.http.*

interface APIList {

    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email : String,
        @Field("password") password : String
    ) : Call<BasicResponse>

    @GET("/v2/main_info")
    fun getRequestMainInfo(
        @Header("X-Http-Token") token : String
    ) : Call<BasicResponse>

    @GET("/user_info")
    fun getRequestMyInfo(
        @Header("X-Http-Token") token : String
    ): Call<BasicResponse>

}