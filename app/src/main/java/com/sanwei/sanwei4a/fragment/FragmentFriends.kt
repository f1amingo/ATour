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
import kotlinx.android.synthetic.main.friends_fragment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.uiThread


/**
 * Created by Johnson on 2018/3/31.
 *
 */
class FragmentFriends : BaseFragment() {

    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: NotificationListAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.friends_fragment, container, false)!!
        mSwipeRefreshLayout = view.find(R.id.z_refresh_friend)
        mRecyclerView = view.find(R.id.z_recycler_friend)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        initRecyclerView()
        initSwipeRefreshLayout()
        initSystemNotifications()
        initBarMomentsListener()
        for (i in 0..10) {
            mAdapter.addData(ItemNotification())
        }
        initNoFunction()
    }

    private fun initNoFunction() {
        z_cover_contacts_friends.setOnClickListener { toast("敬请期待") }
        z_cover_moments_friends.setOnClickListener {toast("敬请期待") }
        z_cover_study_friends.setOnClickListener {toast("敬请期待") }
    }

    private fun initBarMomentsListener() {
        z_view_book_bar.setOnClickListener {
            toast("敬请期待")
        }
        z_view_moment.setOnClickListener {
            toast("敬请期待")
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
        mSwipeRefreshLayout.setOnRefreshListener {
            doAsync {
                Thread.sleep(500)
                uiThread {
                    mSwipeRefreshLayout.isRefreshing = false
                }
            }
        }
    }

    private fun initRecyclerView() {
        mAdapter = NotificationListAdapter(R.layout.z_item_notification, ArrayList())
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.adapter = mAdapter

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