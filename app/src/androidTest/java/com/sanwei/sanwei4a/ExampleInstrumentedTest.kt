package com.com1.sanwei4a

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import com.com1.sanwei4a.entity.po.BookDetailInfo
import com.sanwei.sanwei4a.model.discovery.BookDetailService
import com.com1.sanwei4a.model.discovery.IBookDetailModel

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest:IBookDetailModel {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("sanwei.com.sanwei4a", appContext.packageName)
    }

    val service = BookDetailService(this)

    override fun onSuccess(info: BookDetailInfo) {
        println(info.toString())
    }

    override fun onFail(msg: String?) {
        println(msg)
    }
    @Test
    fun testDetail(){
        service.request(1)
        Thread.sleep(1000000)
    }
}
