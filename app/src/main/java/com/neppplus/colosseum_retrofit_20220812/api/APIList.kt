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

    @FormUrlEncoded
    @POST("/topic_vote")
    fun postRequestTopicVote(
        @Header("X-Http-Token") token: String,
        @Field("side_id") sideId : Int
    ) : Call<BasicResponse>

    @GET("/topic/{topic_id}")
    fun getRequestTopicDetail(
        @Header("X-Http-Token") token: String,
        @Path("topic_id") topicId : Int,
        @Query("order_type") orderType : String,
    ) : Call<BasicResponse>

}