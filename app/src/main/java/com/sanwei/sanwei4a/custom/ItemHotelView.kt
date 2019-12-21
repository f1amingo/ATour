package com.sanwei.sanwei4a.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.makeramen.roundedimageview.RoundedImageView
import com.sanwei.sanwei4a.R
import com.willy.ratingbar.ScaleRatingBar
import org.jetbrains.anko.find

class ItemHotelView : LinearLayout {

    var view: View = LayoutInflater.from(context).inflate(R.layout.item_hotel, this, true)

    constructor(context: Context) : super(context) {
        view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setImg(src: String): ItemHotelView {
        Glide.with(context)
                .load(src)
                .apply(RequestOptions().error(R.drawable.test).placeholder(R.drawable.test))
                .into(view.find<RoundedImageView>(R.id.img_item_hotel))
        return this
    }

    fun setName(name: String): ItemHotelView {
        view.find<TextView>(R.id.txt_item_hotel_name).text = name
        return this
    }

    fun setPrice(price: String): ItemHotelView {
        val text = "￥${price}"
        view.find<TextView>(R.id.txt_item_hotel_price).text = text
        return this
    }

    fun setOnItemClickListener(listener: (View) -> Unit) {
        view.find<View>(R.id.z_cover_item_knowledge).setOnClickListener(listener)
    }
}