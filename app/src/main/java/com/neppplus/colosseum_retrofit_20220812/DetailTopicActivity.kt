package com.neppplus.colosseum_retrofit_20220812

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.neppplus.colosseum_retrofit_20220812.adapter.ReplyRecyclerAdapter
import com.neppplus.colosseum_retrofit_20220812.databinding.ActivityDetailTopicBinding
import com.neppplus.colosseum_retrofit_20220812.datas.BasicResponse
import com.neppplus.colosseum_retrofit_20220812.datas.ReplyData
import com.neppplus.colosseum_retrofit_20220812.datas.TopicData
import com.neppplus.colosseum_retrofit_20220812.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailTopicActivity : BaseActivity() {

    lateinit var binding : ActivityDetailTopicBinding

    lateinit var mTopicData : TopicData

    lateinit var token : String

    lateinit var mReplyAdapter : ReplyRecyclerAdapter
    val mReplyList = ArrayList<ReplyData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_topic)
        setupEvents()
        setValues()
    }

    //의견을 남기고 화면이 뒤로갔다가 다시 오면서 새로고침!
    override fun onResume() {
        super.onResume()
//        의견 남긴 후 RecyclerView 새로고침
        getTopicDetailFromServer()
    }

    override fun setupEvents() {

        //투표 새로운 방법 //태그(xml안에)를이용 함
        val ocl = object : View.OnClickListener{
            override fun onClick(p0: View?) {
                var sideId : Int

                if (p0!!.tag == 0) {
                   sideId = mTopicData.sides[0].id
                }
                else {
                    sideId = mTopicData.sides[1].id
                }
//                                                             페이지 다운 3번 위로 한번 레트로핏 나옴
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

        binding.addReplyBtn.setOnClickListener {
//            진형 선택을 하지 않았다면, 진형 선택을 하도록 설정(의견 게제 불가!)
            if (mTopicData.my_side_id == -1) {
                Toast.makeText(mContext, "의견을 남기시려면 진영을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val myIntent = Intent(mContext, EditReplyActivity::class.java)
            myIntent.putExtra("topicData", mTopicData)
            startActivity(myIntent)
        }
    }

    override fun setValues() {//여기서 어답터 이용해서 연결
        mTopicData = intent.getSerializableExtra("topicData") as TopicData
        token = ContextUtil.getLoginToken(mContext)
        setDataToUi()

        mReplyAdapter = ReplyRecyclerAdapter(mContext, mReplyList)
        binding.replyRecyclerView.adapter = mReplyAdapter
        binding.replyRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun setDataToUi() {//여긴 투표 상단(리사이클러뷰 말고) 이미지 넣기
        binding.titleTxt.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.img_url).into(binding.backgroundImg)
        binding.side1Txt.text = mTopicData.sides[0].title
        binding.side2Txt.text = mTopicData.sides[1].title
        binding.vote1CountTxt.text = "${mTopicData.sides[0].vote_count}표"
        binding.vote2CountTxt.text = "${mTopicData.sides[1].vote_count}표"
    }

    //서버에서 받아오는것!
    fun getTopicDetailFromServer() {
        apiList.getRequestTopicDetail(
            token, mTopicData.id,"NEW"
        ).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!

                    mTopicData = br.data.topic

                    mReplyList.clear()
                    mReplyList.addAll(br.data.topic.replies)

                    mReplyAdapter.notifyDataSetChanged()
//                    Log.d(TAG, br.data.topic.replies.toString())
                }
                else {
                    val errorBodyStr = response.errorBody()!!.string()
                    val jsonObj = JSONObject(errorBodyStr)
                    val code = jsonObj.getInt("code")
                    val message = jsonObj.getString("message")

//                    Log.d(TAG, "code : $code, message : $message")
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
//                Log.e(TAG, t.toString())
            }
        })
    }
}