package com.neppplus.colosseum_retrofit_20220812

import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_retrofit_20220812.api.APIList
import com.neppplus.colosseum_retrofit_20220812.api.ServerAPI
import com.neppplus.colosseum_retrofit_20220812.databinding.ActivityLoginBinding
import com.neppplus.colosseum_retrofit_20220812.datas.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : BaseActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.loginBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            val inputPw = binding.passwordEdt.text.toString()

            apiList.postRequestLogin(inputEmail, inputPw).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
//                        (code == 200)
                        val br = response.body()!!
                        Log.d("로그인한 유저", br.data.user.toString())
                        Log.d("BasicResponse", br.toString())
                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
        }
    }

    override fun setValues() {

    }
}