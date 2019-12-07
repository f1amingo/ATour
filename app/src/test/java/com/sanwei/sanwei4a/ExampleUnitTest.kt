package com.sanwei.sanwei4a

import org.junit.Test

import org.junit.Assert.*
import com.sanwei.sanwei4a.util.EncryptUtil
import com.sanwei.sanwei4a.util.RequestUtil
import java.util.regex.Pattern

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun teatPatten() {
        val patten: Pattern = Pattern.compile("^[a-zA-Z0-9_-]{4,16}$")
        println(patten.matcher("giug.hhiuFS").find())
    }

    @Test
    fun testHead() {
        //println(RequestUtil.getHeadPicUrl("johnson"))
    }

    @Test
    fun testMD5(){
        println(EncryptUtil.md5("lihaojiang"))
    }

}
