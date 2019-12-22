package com.sanwei.sanwei4a.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.alibaba.fastjson.JSONObject
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.ItemMyOrder
import com.sanwei.sanwei4a.adapter.ZMyOrderAdapter
import com.sanwei.sanwei4a.util.LogUtil
import okhttp3.*
import org.jetbrains.anko.custom.onUiThread
import org.jetbrains.anko.support.v4.onUiThread
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast
import java.io.IOException
import kotlinx.android.synthetic.main.activity_my_order.*
class MyOrderActivity : AppCompatActivity() {

    private lateinit var mAdapter: ZMyOrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_order)
        initRecyclerView()
        loadOrderList()
    }


    private fun initRecyclerView() {
        mAdapter = ZMyOrderAdapter(R.layout.z_item_my_order, ArrayList())
        z_recycler_my_order.adapter = mAdapter
        z_recycler_my_order.layoutManager = LinearLayoutManager(this)
    }


    private fun loadOrderList() {
        val url = "http://47.97.175.189:8080/Entity/U20dc5fd38286f/ATour/Order/?user_id=克里斯多夫"
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(url)
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                LogUtil.e("order", "请求失败")
            }

            override fun onResponse(call: Call, response: Response) {
                val strBody = response.body()!!.string()
                Log.d("result-price",strBody)
                val jsonObj = JSONObject.parseObject(strBody)
                val jsonArray = jsonObj.getJSONArray("Order")
                jsonArray.forEach {
                    val jObject = it as JSONObject
                    runOnUiThread {
                        mAdapter.addData(ItemMyOrder(
                                jObject.getString("product_name"),
                                jObject.getString("merchant_name"),
                                jObject.getString("price"),
                                jObject.getString("section_name")
                        ))
                    }
                }
            }
        })
    }
}
