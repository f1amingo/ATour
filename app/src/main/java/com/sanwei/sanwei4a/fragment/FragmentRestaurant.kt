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
import com.sanwei.sanwei4a.custom.ItemRestaurantView
import com.sanwei.sanwei4a.util.JsonUtil
import com.sanwei.sanwei4a.util.LogUtil
import org.jetbrains.anko.find
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.StringBuilder


class FragmentRestaurant : BaseFragment() {

    private lateinit var mLayoutOne: LinearLayout
    private lateinit var mLayoutTwo: LinearLayout
    private lateinit var jsonArray: JSONArray
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_restaurant, container, false)!!
        mLayoutOne = view.find(R.id.layout_restaurant_one)
        mLayoutTwo = view.find(R.id.layout_restaurant_two)
        mLayoutOne.removeAllViews()
        mLayoutTwo.removeAllViews()
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        jsonArray = JsonUtil.loadJson(context, "restaurant.json")
        for (i in 0 until jsonArray.size) {
            val item = jsonArray.getJSONObject(i)
            val layout = if (i > 2) {
                mLayoutTwo
            } else {
                mLayoutOne
            }
            layout.addView(
                    ItemRestaurantView(context)
                            .setImg(item.getString("img"))
                            .setTitle(item.getString("title"))
                            .setName(item.getString("name"))
                            .setRating(item.getFloat("rating"))
                            .setCount(item.getInteger("count"))
            )
        }
//        frontRestView.setOnClickListener {
//            startActivity<RestDetailsActivity>()
//        }
    }


}