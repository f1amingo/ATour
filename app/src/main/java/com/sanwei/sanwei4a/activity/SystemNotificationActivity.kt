package com.sanwei.sanwei4a.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.sanwei.sanwei4a.R
import com.sanwei.sanwei4a.adapter.AdapterSystemNotification
import com.sanwei.sanwei4a.room.AppDatabase
import com.sanwei.sanwei4a.room.SystemNotiEntity
import com.sanwei.sanwei4a.util.LogUtil
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread

class SystemNotificationActivity : BaseActivity() {

    private lateinit var mToolbar: Toolbar
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    private val mAdapter: AdapterSystemNotification = AdapterSystemNotification(R.layout.z_item_system_notification, ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_notification)

        mToolbar = find(R.id.z_toolbar_system_notification)
        mRecyclerView = find(R.id.z_recycler_system_notification)
        mSwipeRefreshLayout = find(R.id.z_refresh_system_notification)

        initToolbar()
        initRecyclerView()
        initSwipeRefreshLayout()

        doAsync {
            val list = AppDatabase.get().getSystemNotiDao().selectAll()
            uiThread {
                for(i in list){
                    mAdapter.addData(0,i)
                }
            }
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
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.adapter = mAdapter
    }

    private fun initToolbar() {
        mToolbar.setNavigationOnClickListener { finish() }

//        mToolbar.inflateMenu(R.menu.menu_system_noti)
//        mToolbar.setOnMenuItemClickListener {
//            when (it.itemId) {
//                R.id.z_option_add -> {
//                    val title = "这里是标题"
//                    val content = "这里是内容"
//                    val timestamp = System.currentTimeMillis()
//                    doAsync {
//                        AppDatabase.get().getSystemNotiDao().insertOne(SystemNotiEntity(title, content, timestamp))
//                    }
//                }
//                R.id.z_option_all -> {
//                    doAsync {
//                        val list = AppDatabase.get().getSystemNotiDao().selectAll()
//                        for(i in list){
//                            LogUtil.e("TAG","${i.id} ${i.title} ${i.content} ${i.timestamp}")
//                        }
//                    }
//                }
//            }
//            return@setOnMenuItemClickListener true
//        }

    }

}
