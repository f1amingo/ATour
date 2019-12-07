package com.sanwei.sanwei4a.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sanwei.sanwei4a.R
import kotlinx.android.synthetic.main.activity_doc_details.*
import org.jetbrains.anko.startActivity

class DocDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_details)

        z_layout_chat_docDetails.setOnClickListener {
            startActivity<ChatActivity>()
        }
    }
}
