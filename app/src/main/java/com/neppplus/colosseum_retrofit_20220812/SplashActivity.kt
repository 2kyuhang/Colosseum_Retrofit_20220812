package com.neppplus.colosseum_retrofit_20220812

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.neppplus.colosseum_retrofit_20220812.datas.BasicResponse
import com.neppplus.colosseum_retrofit_20220812.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity() {

    var isTokenOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        val token = ContextUtil.getLoginToken(mContext)
        apiList.getRequestMyInfo(token).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    isTokenOk = true
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

    override fun setValues() {
        val myHandler = Handler(Looper.getMainLooper())

        myHandler.postDelayed({


            if (isTokenOk && ContextUtil.getAutoLogin(mContext)) {
                val myIntent = Intent(mContext, MainActivity::class.java)
                startActivity(myIntent)
            }
            else {
                val myIntent = Intent(mContext, LoginActivity::class.java)
                startActivity(myIntent)
            }

            finish()
        }, 2500)
    }
}