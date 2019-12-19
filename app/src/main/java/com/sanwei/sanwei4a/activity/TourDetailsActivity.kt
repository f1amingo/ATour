package com.sanwei.sanwei4a.activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
//import com.alibaba.fastjson.JSONObject
import com.alibaba.fastjson.TypeReference
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.entity.Constant
import com.sanwei.sanwei4a.entity.po.SysBook
import com.sanwei.sanwei4a.entity.result.Result
import com.sanwei.sanwei4a.util.RequestUtil
import kotlinx.android.synthetic.main.activity_tour_details.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class TourDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour_details)
        confirmOrder.setOnClickListener {
            var okHttpClient = OkHttpClient()
//            var param = HashMap<String,String>()
//            param.put("product_category", "定制游")
//            param.put("price","267899")
//            param.put("merchantname", "i")
//            param.put("username", "李刚")
//            param.put("detail", "哎呦不错哦")
//
//
//            val jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(param)
//            Log.d("444",jsonStr)
//            val request = Request.Builder()
//                    .post(RequestBody.create(JSON, jsonStr))
//                    .url("http://47.97.175.189:8080/Entity/U20dc5fd38286f/ATour/Order")
//                    .build()



            var JSON = MediaType.parse("application/json; charset=utf-8");
            var param = HashMap<String,String>()
            param.put("product_category", "定制游")
            param.put("state", "toPay")
            param.put("price", tourPrice.text.toString())
            param.put("date", tourDay.text.toString())
            param.put("merchant_name", merchantName.text.toString())
            param.put("product_name", productName.text.toString())
            val jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(param)

            val request = Request.Builder()
                    .post(RequestBody.create(JSON, jsonStr))
                    .url("http://47.97.175.189:8080/Entity/U20dc5fd38286f/ATour/Order")
                    .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                    Log.d("1", "请求失败")
                }


                override fun onResponse(call: Call, response: Response) {

                    if (response?.isSuccessful == true) {
                        Log.d("1", "请求成功")
                        val str = response.body()?.string()

                        if (!str.isNullOrEmpty()) {

                            val result = JSONObject(str)
                            Log.d("123",str)
                        }
                    }

                }
            })
        }
    }
}
