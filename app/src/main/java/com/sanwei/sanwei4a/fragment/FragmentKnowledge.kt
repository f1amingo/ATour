package com.sanwei.sanwei4a.fragment

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sanwei.sanwei4a.App
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.activity.ChatActivity
import com.sanwei.sanwei4a.activity.DocListActivity
import com.sanwei.sanwei4a.activity.SystemNotificationActivity
import com.sanwei.sanwei4a.adapter.ItemNotification
import com.sanwei.sanwei4a.adapter.NotificationListAdapter
import kotlinx.android.synthetic.main.friends_fragment.*
import kotlinx.android.synthetic.main.knowledge_fragment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.uiThread


/**
 * Created by Johnson on 2018/3/31.
 *
 */
class FragmentKnowledge : BaseFragment() {

    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater?.inflate(R.layout.knowledge_fragment, container, false)!!
        return mView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        z_item4_knowledge.setOnItemClickListener { startActivity<DocListActivity>() }
        z_item3_knowledge.setOnItemClickListener { startActivity<DocListActivity>() }
        z_item2_knowledge.setOnItemClickListener { startActivity<DocListActivity>() }
        z_item1_knowledge.setOnItemClickListener { startActivity<DocListActivity>() }
    }

}