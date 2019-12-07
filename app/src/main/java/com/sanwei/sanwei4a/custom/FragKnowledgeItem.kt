package com.sanwei.sanwei4a.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.sanwei.sanwei4a.R
import org.jetbrains.anko.find

class FragKnowledgeItem : RelativeLayout {

    var view: View = LayoutInflater.from(context).inflate(R.layout.layout_frag_knowledge_item, this, true)

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val typeArr = context.obtainStyledAttributes(attrs, R.styleable.FragKnowledgeItem)
        val title = typeArr.getText(R.styleable.FragKnowledgeItem_title)
        val subtitle = typeArr.getText(R.styleable.FragKnowledgeItem_subtitle)
        val img = typeArr.getResourceId(R.styleable.FragKnowledgeItem_img, R.drawable.ic_knowledge_item1)
        view.find<ImageView>(R.id.z_img_item_knowledge).setImageResource(img)
        view.find<TextView>(R.id.z_title_item_knowledge).text = title
        view.find<TextView>(R.id.z_subtitle_item_knowledge).text = subtitle

        typeArr.recycle()
    }

    fun setOnItemClickListener(l: (View) -> Unit) {
        view.find<View>(R.id.z_cover_item_knowledge).setOnClickListener(l)
    }
}