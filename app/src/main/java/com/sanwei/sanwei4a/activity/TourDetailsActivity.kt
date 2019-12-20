package com.sanwei.sanwei4a.activity

//import com.alibaba.fastjson.JSONObject

import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.alibaba.fastjson.JSONArray
import com.alibaba.fastjson.JSONObject
import com.bumptech.glide.Glide
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.ImgListAdapter
import com.sanwei.sanwei4a.adapter.ItemImg
import com.sanwei.sanwei4a.adapter.TourDayItem
import com.sanwei.sanwei4a.adapter.TourDayItemAdapter
import com.sanwei.sanwei4a.util.LogUtil
import kotlinx.android.synthetic.main.activity_tour_details.*
import kotlinx.android.synthetic.main.z_bottom_dialog_tour.*
import okhttp3.*
import org.apmem.tools.layouts.FlowLayout
import org.jetbrains.anko.dip
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor
import java.io.IOException


class TourDetailsActivity : BaseActivity() {

    private lateinit var mId: String
    private lateinit var name: String
    private lateinit var tags: List<String>
    private lateinit var price: String
    private lateinit var imgList: List<String>
    private lateinit var details: JSONArray
    private lateinit var selections: List<String>
    private lateinit var merchantName: String
    private lateinit var merchantId: String

    private lateinit var mAdapterImg: ImgListAdapter
    private lateinit var mAdapterDay: TourDayItemAdapter

    private var mBottomDlg: BottomSheetDialog? = null
    private lateinit var flowlayout: FlowLayout
    private lateinit var btnSelect: Button
    private var curSelection = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tour_details)
        this.showWaitingDlg()
        mId = "1576748697602" //todo 获取页面传参

        initRecyclerImg()
        initRecyclerDay()
        initBottomView()

        getDetails()

        btn_confirm.setOnClickListener {
            this.mBottomDlg?.show()
        }


//        confirmOrder.setOnClickListener {
//            var okHttpClient = OkHttpClient()
//            var JSON = MediaType.parse("application/json; charset=utf-8");
//            var param = HashMap<String, String>()
//            param.put("product_category", "定制游")
//            param.put("state", "toPay")
//            param.put("price", tourPrice.text.toString())
//            param.put("date", tourDay.text.toString())
//            param.put("merchant_name", merchantName.text.toString())
//            param.put("product_name", z_txt_name_tour.text.toString())
//            val jsonStr = com.alibaba.fastjson.JSONObject.toJSONString(param)
//
//            val request = Request.Builder()
//                    .post(RequestBody.create(JSON, jsonStr))
//                    .url("http://47.97.175.189:8080/Entity/U20dc5fd38286f/ATour/Order")
//                    .build()
//            okHttpClient.newCall(request).enqueue(object : Callback {
//                override fun onFailure(call: Call, e: IOException) {
//                    Log.d("1", "请求失败")
//                }
//
//                override fun onResponse(call: Call, response: Response) {
//                    if (response?.isSuccessful == true) {
//                        Log.d("1", "请求成功")
//                        val str = response.body()?.string()
//
//                        if (!str.isNullOrEmpty()) {
//                            val result = JSONObject(str)
//                            Log.d("123", str)
//                        }
//                    }
//
//                }
//            })
//        }
    }

    private fun initBottomView() {
        val bottomView = LayoutInflater.from(this).inflate(R.layout.z_bottom_dialog_tour, null)
        flowlayout = bottomView.find(R.id.flowlayout_tour)
        btnSelect = bottomView.find(R.id.btn_select_tour)
        btnSelect.setOnClickListener {
            toast("选择套餐${curSelection}")
        }
        mBottomDlg = BottomSheetDialog(this)
        mBottomDlg!!.window!!.setBackgroundDrawable(ColorDrawable())
        mBottomDlg!!.setContentView(bottomView)
    }

    private fun initRecyclerDay() {
        mAdapterDay = TourDayItemAdapter(R.layout.z_item_tour_day, ArrayList())
        z_recycler_tour_day.adapter = mAdapterDay
        val layoutManager = LinearLayoutManager(this)
        z_recycler_tour_day.layoutManager = layoutManager
    }

    private fun initRecyclerImg() {
        mAdapterImg = ImgListAdapter(R.layout.z_item_img, ArrayList())
        z_recycler_img_tour.adapter = mAdapterImg
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        z_recycler_img_tour.layoutManager = layoutManager
    }

    private fun getDetails() {
        val client = OkHttpClient()
        val url = "http://47.97.175.189:8080/Entity/U20dc5fd38286f/ATour/Tour/$mId"
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
//        封面图
        if (this.imgList.isNotEmpty()) {
            Glide.with(this)
                    .load(imgList[0])
                    .into(z_img_cover_tour)
        }
        z_txt_name_tour.text = name
        z_txt_price.text = price
        z_txt_tour_merchant.text = merchantName
//        标签
        tags.forEach {
            this.addTagUI(it)
        }
//        景点风光
        imgList.forEach {
            mAdapterImg.addData(ItemImg(it))
        }
//        行程介绍
        for ((index, value) in details.withIndex()) {
            val json = value as JSONObject
            mAdapterDay.addData(TourDayItem(index,
                    json.getString("activity"),
                    json.getString("description"),
                    json.getString("img")
            ))
        }
//        套餐选择
        for ((index, value) in selections.withIndex()) {
            addSelectionUI(value, flowlayout, index)
        }
        selectOneSelection(0)
        dismissWaitingDlg()
    }

    private fun addTagUI(txt: String) {
        val txtView = TextView(this)
        txtView.setPadding(dip(5), dip(1), dip(5), dip(1))
        txtView.text = txt
        txtView.typeface = Typeface.DEFAULT_BOLD
        val drawable = resources.getDrawable(R.drawable.z_shape_person_tag, this.theme)
        txtView.background = drawable
        val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(dip(10), 0, 0, 0)
        txtView.layoutParams = params
        layout_price_tags.addView(txtView)
    }

    private fun addSelectionUI(txt: String, layout: FlowLayout, index: Int) {
        val txtView = TextView(this)
        txtView.setPadding(dip(8), dip(5), dip(8), dip(5))
        txtView.text = txt
        txtView.setTextColor(resources.getColorStateList(R.color.text_gray, this.theme))
        val drawable = resources.getDrawable(R.drawable.shape_tour_selection, this.theme)
        txtView.background = drawable
        val params = FlowLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(dip(8), dip(8), dip(8), dip(8))
        txtView.layoutParams = params
        txtView.setOnClickListener {
            selectOneSelection(index)
        }
        layout.addView(txtView)
    }

    private fun selectOneSelection(index: Int) {
        var txtView: TextView
        for (i in 0 until flowlayout.childCount) {
            txtView = flowlayout.getChildAt(i) as TextView
            val drawable = resources.getDrawable(R.drawable.shape_tour_selection, this.theme)
            txtView.background = drawable
            txtView.setTextColor(resources.getColorStateList(R.color.text_gray, this.theme))
        }
        txtView = flowlayout.getChildAt(index) as TextView
        val drawable = resources.getDrawable(R.drawable.shape_tour_selection_active, this.theme)
        txtView.background = drawable
        txtView.setTextColor(resources.getColorStateList(R.color.color_yellow_google, this.theme))
        curSelection = txtView.text.toString()
    }

}
