package com.neppplus.colosseum_retrofit_20220812

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.colosseum_retrofit_20220812.adapter.TopicRecyclerAdapter
import com.neppplus.colosseum_retrofit_20220812.databinding.ActivityMainBinding
import com.neppplus.colosseum_retrofit_20220812.datas.BasicResponse
import com.neppplus.colosseum_retrofit_20220812.datas.TopicData
import com.neppplus.colosseum_retrofit_20220812.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    lateinit var mTopicAdapter : TopicRecyclerAdapter

    val mTopicList = ArrayList<TopicData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.logoutBtn.setOnClickListener {
            ContextUtil.clearData(mContext)

            val myIntent = Intent(mContext, LoginActivity::class.java)
            startActivity(myIntent)
            finishAffinity()
        }
    }

    override fun setValues() {

        getTopicFromServer()

        mTopicAdapter = TopicRecyclerAdapter(mContext, mTopicList)
        binding.topicRecyclerView.adapter = mTopicAdapter
        binding.topicRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getTopicFromServer() { //서버에서 정보 받아와서 위에서 넣어주는 것!
        Log.d("서버 함수", "진입")

        val token = ContextUtil.getLoginToken(mContext)
        apiList.getRequestMainInfo(token).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!

                    Log.d("응답 성공", br.toString())

                    mTopicList.clear()

                    mTopicList.addAll(br.data.topics)

                    //너 데이터 바뀌었으니 확인해!!
                    mTopicAdapter.notifyDataSetChanged()
                }
                else {
                    val errorBodyStr = response.errorBody()!!.string()
                    val jsonObj = JSONObject(errorBodyStr)
                    val code = jsonObj.getInt("code")
                    val message = jsonObj.getString("message")

                    Log.e("응답 실패", message)
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}