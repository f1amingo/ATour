package com.sanwei.sanwei4a.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.activity.HotelDetailsActivity
import kotlinx.android.synthetic.main.fragment_hotel.*
import org.jetbrains.anko.support.v4.startActivity

class FragmentHotel : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_hotel, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated")
        frontHotelView.setOnClickListener {
            startActivity<HotelDetailsActivity>()
        }
    }

}