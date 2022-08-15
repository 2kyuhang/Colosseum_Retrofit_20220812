package com.neppplus.colosseum_retrofit_20220812

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_retrofit_20220812.api.APIList
import com.neppplus.colosseum_retrofit_20220812.api.ServerAPI
import com.neppplus.colosseum_retrofit_20220812.databinding.ActivityLoginBinding
import com.neppplus.colosseum_retrofit_20220812.datas.BasicResponse
import com.neppplus.colosseum_retrofit_20220812.utils.ContextUtil
import com.neppplus.colosseum_retrofit_20220812.utils.GlobalData
import org.json.JSONObject
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
            //                                                              레트로핏2로 임포트하기
            apiList.postRequestLogin(inputEmail, inputPw).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    //(code == 200) > 로그인 성공 (응답정보위치 : response.body() : BasicResponse)
                    if (response.isSuccessful) {
                        val br = response.body()!!//이제 이안에 정보가 다 있다?

                        ContextUtil.setLoginToken(mContext, br.data.token)

                        Log.d("접속 토큰", br.data.token)

                        ContextUtil.setAutoLogin(mContext, binding.autoLoginCb.isChecked)

                        GlobalData.loginUser = br.data.user

                        Toast.makeText(mContext, GlobalData.loginUser!!.nick_name, Toast.LENGTH_SHORT).show()

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)
                        finish()
                    }
                    else {
//                        code == 200이 아닐경우 > 로그인 실패 (응답정보위치 : response.errorBody() : ResponseBody)
                        //200코드랑 위치가 달라서 아래처럼 받아줘야 한다 OKHttp처럼!
                        val errorBodyStr = response.errorBody()!!.string()
                        val jsonObj = JSONObject(errorBodyStr)
                        val code = jsonObj.getInt("code")
                        val message = jsonObj.getString("message")

                        if (code == 400) {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                        else {
                            Toast.makeText(mContext, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                            Log.e("LoginAct", "code : $code, message : $message")
                        }
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