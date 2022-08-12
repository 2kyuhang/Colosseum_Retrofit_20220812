package com.neppplus.colosseum_retrofit_20220812

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.neppplus.colosseum_retrofit_20220812.databinding.ActivityDetailTopicBinding
import com.neppplus.colosseum_retrofit_20220812.datas.BasicResponse
import com.neppplus.colosseum_retrofit_20220812.datas.TopicData
import com.neppplus.colosseum_retrofit_20220812.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTopicActivity : BaseActivity() {

    lateinit var binding : ActivityDetailTopicBinding

    lateinit var mTopicData : TopicData

    lateinit var token : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_topic)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        val ocl = object : View.OnClickListener{
            override fun onClick(p0: View?) {
                var sideId : Int

                if (p0!!.tag == 0) {
                   sideId = mTopicData.sides[0].id
                }
                else {
                    sideId = mTopicData.sides[1].id
                }

                apiList.postRequestTopicVote(token, sideId).enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            val br = response.body()!!

                            Toast.makeText(mContext, br.message, Toast.LENGTH_SHORT).show()

                            mTopicData = br.data.topic

                            setDataToUi()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            }
        }

        binding.vote1Btn.setOnClickListener(ocl)
        binding.vote2Btn.setOnClickListener(ocl)
    }

    override fun setValues() {
        mTopicData = intent.getSerializableExtra("topicData") as TopicData
        token = ContextUtil.getLoginToken(mContext)
        getTopicDetailFromServer()
        setDataToUi()
    }

    fun setDataToUi() {
        binding.titleTxt.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.img_url).into(binding.backgroundImg)
        binding.side1Txt.text = mTopicData.sides[0].title
        binding.side2Txt.text = mTopicData.sides[1].title
        binding.vote1CountTxt.text = "${mTopicData.sides[0].vote_count}표"
        binding.vote2CountTxt.text = "${mTopicData.sides[1].vote_count}표"
    }

    fun getTopicDetailFromServer() {
        apiList.getRequestTopicDetail(
            token, "NEW", mTopicData.id
        ).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mTopicData = br.data.topic
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}