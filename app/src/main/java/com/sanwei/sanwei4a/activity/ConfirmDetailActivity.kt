package com.sanwei.sanwei4a.activity
import com.alibaba.fastjson.JSONObject
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.sanwei.sanwei4a.R
import kotlinx.android.synthetic.main.activity_confirm_order.*
import okhttp3.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.io.IOException

class ConfirmDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_order)
        var bundle  =intent.extras
        var productName = bundle.getString("name")
        var price = bundle.getString("price")
        var mId = bundle.getString("mId")
        var num = price.toInt()
        var merchantName = bundle.getString("merchantName")
        var imageUrl = bundle.getString("imageUrl")
        var curSelection = bundle.getString("curSelection")
        selection_name.setText(curSelection)
        merchant_name.setText(merchantName)
        order_price.setText(price)
        var msg = "to toast"
        if (imageUrl.isNotEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .into(z_img_cover_tour)
        }
        btn_confirm_order.setOnClickListener{
            var comment = comment_text.getText().toString()
            price = num.toString()
            var okHttpClient = OkHttpClient()
            var JSON = MediaType.parse("application/json; charset=utf-8");
            var param = HashMap<String, String>()
            param.put("product_category", "定制游")
            param.put("state", "review")
            param.put("price", price)
            param.put("comment", comment)
            param.put("user_id", "克里斯多夫")
            param.put("merchant_name", merchantName)
            param.put("product_name", productName)
            param.put("product_id",mId)
            param.put("section_name",curSelection)
            val jsonStr = JSONObject.toJSONString(param)
            val request = Request.Builder()
                    .post(RequestBody.create(JSON, jsonStr))
                    .url("http://47.97.175.189:8080/Entity/U20dc5fd38286f/ATour/Order")
                    .build()
            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    msg = "下单失败"
                }

                override fun onResponse(call: Call, response: Response) {
                    if (response?.isSuccessful == true) {
                        msg = "下单成功"
                    }
                }
            })
            toast(msg)
            startActivity<TourDetailsActivity>()
        }
        z_toolbar_borrow.setNavigationOnClickListener { finish() }

        select_box1.setOnClickListener(){
            num = price.toInt()
            if(select_box1.isChecked) {
                num += 15
            }
            if(select_box2.isChecked) {
                num += 50
            }
            order_price.setText(num.toString())
        }
        select_box2.setOnClickListener() {
            num = price.toInt()
            if(select_box1.isChecked) {
                num += 15
            }
            if(select_box2.isChecked) {
                num += 50
            }
            order_price.setText(num.toString())
        }
    }
}
