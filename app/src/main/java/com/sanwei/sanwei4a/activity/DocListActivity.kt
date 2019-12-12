package com.sanwei.sanwei4a.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.AdapterDocList
import com.sanwei.sanwei4a.adapter.DocItem
import kotlinx.android.synthetic.main.activity_doc_list.*
import org.jetbrains.anko.startActivity

class DocListActivity : AppCompatActivity() {

    private val mAdapter = AdapterDocList(R.layout.layout_doc_item, ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doc_list)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        z_recycler_docList.adapter = mAdapter
        z_recycler_docList.layoutManager = LinearLayoutManager(this)
        for (i in 1..5)
            mAdapter.addData(DocItem())
        mAdapter.setOnItemClickListener { _, _, position ->
            val item = mAdapter.getItem(position)
            //startActivity<DocDetailsActivity>()
        }
    }
}
