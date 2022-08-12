package com.neppplus.colosseum_retrofit_20220812

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.colosseum_retrofit_20220812.databinding.ActivityEditReplyBinding
import com.neppplus.colosseum_retrofit_20220812.datas.BasicResponse
import com.neppplus.colosseum_retrofit_20220812.datas.TopicData
import com.neppplus.colosseum_retrofit_20220812.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditReplyActivity : BaseActivity() {

    lateinit var binding : ActivityEditReplyBinding

    lateinit var mTopicData : TopicData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_reply)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.saveBtn.setOnClickListener {
            val inputContent = binding.contentEdt.text.toString()

            if (inputContent.isBlank()) {
                Toast.makeText(mContext, "내용은 공백이거나 빈칸일 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val token = ContextUtil.getLoginToken(mContext)

            apiList.postRequestAddReply(
                token, mTopicData.id, inputContent
            ).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val message = response.body()!!.message
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else {
                        val errorBodyStr = response.errorBody()!!.string()
                        val jsonObj = JSONObject(errorBodyStr)
                        val code = jsonObj.getInt("code")
                        val message = jsonObj.getString("message")

                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
                    Log.e(TAG, t.toString())
                }
            })
        }
    }

    override fun setValues() {
        mTopicData = intent.getSerializableExtra("topicData") as TopicData

        binding.topicTitleTxt.text = mTopicData.title
        binding.selectedSideTxt.text = "(${mTopicData.my_side.title})"
    }
}