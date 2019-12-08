package com.sanwei.sanwei4a.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sanwei.sanwei4a.R
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.discover_fragment.*


class FragmentDiscover : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.discover_fragment, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        z_discover_banner.setImages(listOf("https://dimg04.c-ctrip.com/images/zg0c1b000001a6c4p3595.jpg"
                , "https://dimg04.c-ctrip.com/images/zg0k1b000001a5tueEAB8.jpg"))
                .setImageLoader(object : ImageLoader() {
                    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
                        Glide.with(context).load(path as String).into(imageView)
                    }
                })
                .start()
    }

}