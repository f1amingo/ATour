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

class ItemRestaurantView : LinearLayout {

    var view: View = LayoutInflater.from(context).inflate(R.layout.item_restaurant, this, true)

    constructor(context: Context) : super(context) {
        view.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f)
        view.find<TextView>(R.id.txt_item_restaurant_title).visibility = View.INVISIBLE
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    fun setImg(src: String): ItemRestaurantView {
        Glide.with(context)
                .load(src)
                .apply(RequestOptions().error(R.drawable.test).placeholder(R.drawable.test))
                .into(view.find<RoundedImageView>(R.id.img_item_restaurant))
        return this
    }

    fun setTitle(title: String): ItemRestaurantView {
        if (title.isBlank()) {
            return this
        }
        val txtView = view.find<TextView>(R.id.txt_item_restaurant_title)
        txtView.text = title
        txtView.visibility = View.VISIBLE
        return this
    }

    fun setName(name: String): ItemRestaurantView {
        view.find<TextView>(R.id.txt_item_restaurant_name).text = name
        return this
    }

    fun setRating(rating: Float): ItemRestaurantView {
        view.find<ScaleRatingBar>(R.id.rating_item_restaurant).rating = rating
        return this
    }

    fun setCount(count: Int): ItemRestaurantView {
        val text = "${count}条"
        view.find<TextView>(R.id.txt_item_restaurant_count).text = text
        return this
    }

    fun setOnItemClickListener(listener: (View) -> Unit) {
        view.find<View>(R.id.z_cover_item_knowledge).setOnClickListener(listener)
    }
}