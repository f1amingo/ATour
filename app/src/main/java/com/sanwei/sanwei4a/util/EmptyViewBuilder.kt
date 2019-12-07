package com.sanwei.sanwei4a.util

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.sanwei.sanwei4a.R
import org.jetbrains.anko.find

/**
 * Created by VincentLaf on 2018/1/23.
 * 生成EmptyView
 * 三种情况加载中、网络错误、没有数据
 */
class EmptyViewBuilder {

    enum class EmptyType {
        TYPE_LOADING,
        TYPE_NETWORK_ERROR,
        TYPE_NO_DATA
    }

    companion object {

        @SuppressLint("InflateParams")
        fun createEmptyView(context: Context, type: EmptyType): View {
            val view = LayoutInflater.from(context).inflate(R.layout.z_emptyview_worklist, null)
            val imageView = view.find<ImageView>(R.id.z_imageview_emptyview_worklist)
            val progressBar = view.find<ProgressBar>(R.id.z_progressbar_emptyview_worklist)
            val textView = view.find<TextView>(R.id.z_textview_emptyview_worklist)

            if (type == EmptyType.TYPE_LOADING) {
                imageView.visibility = View.GONE
                textView.text = "加载中..."
            } else {
                progressBar.visibility = View.GONE
            }

            if (type == EmptyType.TYPE_NETWORK_ERROR) {
                textView.text = "网络错误,下拉重试"
            }

            if (type == EmptyType.TYPE_NO_DATA) {
                textView.text = "没有查到相关数据,下拉重试"
            }
            return view
        }//createEmptyView

        @SuppressLint("InflateParams")
        fun createEmptyForDetails(context: Context, type: EmptyType, listener: View.OnClickListener): View {
            val view = LayoutInflater.from(context).inflate(R.layout.z_emptyview_details, null)
            val imageView = view.find<ImageView>(R.id.z_imageview_emptyview_worklist)
            val progressBar = view.find<ProgressBar>(R.id.z_progressbar_emptyview_worklist)
            val textView = view.find<TextView>(R.id.z_textview_emptyview_worklist)
            val btnRetry = view.find<Button>(R.id.z_btn_retry_empty)

            btnRetry.setOnClickListener(listener)
            btnRetry.visibility = View.GONE
            if (type == EmptyType.TYPE_LOADING) {
                imageView.visibility = View.GONE
                textView.text = "加载中..."
            } else {
                progressBar.visibility = View.GONE
            }

            if (type == EmptyType.TYPE_NETWORK_ERROR) {
                textView.text = "网络错误"
                btnRetry.visibility = View.VISIBLE
            }

            if (type == EmptyType.TYPE_NO_DATA) {
                textView.text = "没有查到相关数据"
                btnRetry.visibility = View.VISIBLE
            }
            return view
        }
    }//companion object
}