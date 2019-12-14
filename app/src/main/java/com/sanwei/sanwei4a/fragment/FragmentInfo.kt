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
import com.sanwei.sanwei4a.activity.SystemNotificationActivity
import com.sanwei.sanwei4a.adapter.ItemNotification
import com.sanwei.sanwei4a.adapter.NotificationListAdapter
import kotlinx.android.synthetic.main.fragment_info.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.uiThread

/**
 * Created by Flamingo on 2019/12/14.
 */
class FragmentInfo : BaseFragment() {

    private lateinit var mAdapter: NotificationListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_info, container, false)!!
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initRecyclerView()
        initSwipeRefreshLayout()
        for (i in 0..5) {
            mAdapter.addData(ItemNotification())
        }
    }

    private fun initSystemNotifications() {
        z_cover_system_notification.setOnClickListener {
            if (App.account == null)
                toast("尚未登录")
            else
                startActivity<SystemNotificationActivity>()
        }
    }

    private fun initSwipeRefreshLayout() {
        z_refresh_info.setOnRefreshListener {
            doAsync {
                Thread.sleep(500)
                uiThread {
                    z_refresh_info.isRefreshing = false
                }
            }
        }
    }

    private fun initRecyclerView() {
        mAdapter = NotificationListAdapter(R.layout.z_item_notification, ArrayList())
        z_recycler_info.layoutManager = LinearLayoutManager(context)
        z_recycler_info.adapter = mAdapter

        mAdapter.setOnItemClickListener { _, _, position ->
            val item = mAdapter.getItem(position)!!

            when (item.type) {
                ItemNotification.NotificationType.CHAT_MSG -> {
                    startActivity<ChatActivity>()
                }
                ItemNotification.NotificationType.PLATFORM_MSG -> {

                }
            }
        }
    }
}