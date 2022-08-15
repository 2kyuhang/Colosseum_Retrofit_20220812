package com.neppplus.colosseum_retrofit_20220812.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerAPI {

    companion object {

        private var retrofit : Retrofit? = null
        private val BASE_URL = "http://54.180.52.26"

        fun getRetrofit() : Retrofit {//레트로핏 객체 생성

            if (retrofit == null) {//없으면 만들고

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                        //JSON(라이브러리 받아온 것) 알아서 던져주세요 라는 것!
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            //있으면 그냥 바로

            return retrofit!!  //리턴
        }

    }

}