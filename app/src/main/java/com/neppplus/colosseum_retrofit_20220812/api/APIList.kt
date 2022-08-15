package com.neppplus.colosseum_retrofit_20220812.api

import com.neppplus.colosseum_retrofit_20220812.datas.BasicResponse
import retrofit2.Call
import retrofit2.http.*

interface APIList {

    @FormUrlEncoded//form데이터는 이것도 붙여줘야 한다 => POST, PUT, PATCH 만 해당
    @POST("/user")
    fun postRequestLogin(
        @Field("email") email : String, //form 데이터는 @Field("email") 이렇게 보낸다
        @Field("password") password : String
    ) : Call<BasicResponse> //Call은 레트로핏으로 임포트

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

    @FormUrlEncoded
    @POST("/topic_reply")
    fun postRequestAddReply(
        @Header("X-Http-Token") token: String,
        @Field ("topic_id") topicId : Int,
        @Field ("content") content : String
    ): Call<BasicResponse>
}