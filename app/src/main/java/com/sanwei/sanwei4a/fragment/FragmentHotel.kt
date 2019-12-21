package com.sanwei.sanwei4a.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONArray
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.activity.HotelDetailsActivity
import com.sanwei.sanwei4a.custom.ItemHotelView
import com.sanwei.sanwei4a.custom.ItemRestaurantView
import com.sanwei.sanwei4a.util.JsonUtil
import com.sanwei.sanwei4a.util.LogUtil
import kotlinx.android.synthetic.main.fragment_hotel.*
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder

class FragmentHotel : BaseFragment() {

    private lateinit var mLayoutOne: LinearLayout
    private lateinit var mLayoutTwo: LinearLayout
    private lateinit var jsonArray: JSONArray
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_hotel, container, false)!!
        mLayoutOne = view.find(R.id.layout_hotel_one)
        mLayoutTwo = view.find(R.id.layout_hotel_two)
        mLayoutOne.removeAllViews()
        mLayoutTwo.removeAllViews()
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        jsonArray = JsonUtil.loadJson(context, "hotel.json")
        for (i in 0 until jsonArray.size) {
            val item = jsonArray.getJSONObject(i)
            val layout = if (i > 2) {
                mLayoutTwo
            } else {
                mLayoutOne
            }
            layout.addView(
                    ItemHotelView(context)
                            .setImg(item.getString("img"))
                            .setName(item.getString("name"))
                            .setPrice(item.getString("price"))
            )
        }
//        frontHotelView.setOnClickListener {
//            startActivity<HotelDetailsActivity>()
//        }
    }
}