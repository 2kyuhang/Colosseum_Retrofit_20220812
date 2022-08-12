package com.neppplus.colosseum_retrofit_20220812.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerAPI {

    companion object {

        private var retrofit : Retrofit? = null
        private val BASE_URL = "http://54.180.52.26"

        fun getRetrofit() : Retrofit {

            if (retrofit == null) {

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }


            return retrofit!!
        }

    }

}