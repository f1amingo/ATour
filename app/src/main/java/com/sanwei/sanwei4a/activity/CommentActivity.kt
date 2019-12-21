package com.sanwei.sanwei4a.activity

import android.os.Bundle
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.bumptech.glide.Glide
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.util.LogUtil
import kotlinx.android.synthetic.main.activity_comment.*
import okhttp3.*
import org.jetbrains.anko.toast
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class CommentActivity : BaseActivity() {

    private lateinit var name: String
    private lateinit var tags: List<String>
    private lateinit var price: String
    private lateinit var imgList: List<String>
    private lateinit var details: JSONArray
    private lateinit var selections: List<String>
    private lateinit var merchantName: String
    private lateinit var merchantId: String

    private lateinit var tourId: String
    private var avatar: String = "http://img5.imgtn.bdimg.com/it/u=2491588266,1963864761&fm=26&gp=0.jpg"
    private var userName: String = "孙文台"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        showWaitingDlg()
        initToolbar()
        initSubmitBtn()
        tourId = intent.getStringExtra("tourId")
        getDetails()
    }

    private fun initSubmitBtn() {
        z_btn_submit_comment.setOnClickListener {
            val content = z_edit_comment.text.toString()
            val rating = rating_comment.rating
            if (content.isBlank() || content.length < 5) {
                toast("请至少输入5字评价内容！")
                return@setOnClickListener
            }
            val mapData = HashMap<String, String>()
            mapData["stars"] = rating.toInt().toString()
            mapData["content"] = content
            mapData["avatar"] = avatar
            mapData["name"] = userName
            mapData["date"] = Date().time.toString()
            mapData["tourId"] = tourId


            val url = "http://47.97.175.189:8080/Entity/U20dc5fd38286f/ATour/Comment/"
            val client = OkHttpClient.Builder().retryOnConnectionFailure(true).build()
            val parameterType = MediaType.parse("application/json;charset=UTF-8")
            val jsonString = JSONObject.toJSONString(mapData)
            val requestBody = RequestBody.create(parameterType, jsonString)
            val request = Request.Builder()
                    .post(requestBody)
                    .url(url)
                    .build()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    LogUtil.e(TAG, e.toString())
                    toast("提交失败")
                }

                override fun onResponse(call: Call, response: Response) {
                    LogUtil.e(TAG, response.body()!!.string())
                    runOnUiThread { toast("提交成功") }
                }
            })


        }
    }

    private fun getDetails() {
        if (tourId.isBlank()) {
            return
        }
        val client = OkHttpClient()
        val url = "http://47.97.175.189:8080/Entity/U20dc5fd38286f/ATour/Tour/$tourId"
        val request = Request.Builder()
                .url(url)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                LogUtil.e(TAG, "请求失败")
                LogUtil.e(TAG, e.message!!)
                toast("请求失败")
                dismissWaitingDlg()
            }

            override fun onResponse(call: Call, response: Response) {
                val strBody = response.body()!!.string()
                LogUtil.e(TAG, strBody)
                val jsonObj = JSONObject.parseObject(strBody)
                name = jsonObj.getString("name")
                tags = jsonObj.getJSONArray("tags").toJavaList(String::class.java)
                price = jsonObj.getString("price")
                imgList = jsonObj.getJSONArray("img_list").toJavaList(String::class.java)
                details = jsonObj.getJSONArray("details")
                merchantName = jsonObj.getString("merchant_name")
                merchantId = jsonObj.getString("merchant_id")
                selections = jsonObj.getJSONArray("selections").toJavaList(String::class.java)
                runOnUiThread {
                    updateUI()
                }
            }
        })
    }

    private fun updateUI() {
        if (this.imgList.isNotEmpty()) {
            Glide.with(this)
                    .load(imgList[0])
                    .into(z_img_comment_tour)
        }
        z_txt_comment_tour_name.text = name
        z_txt_comment_tour_merchant.text = StringBuffer("店铺：${merchantName}")
        z_txt_comment_tour_price.text = StringBuffer("￥$price")
        dismissWaitingDlg()
    }

    private fun initToolbar() {
        z_toolbar_comment.setNavigationOnClickListener { finish() }
    }
}
