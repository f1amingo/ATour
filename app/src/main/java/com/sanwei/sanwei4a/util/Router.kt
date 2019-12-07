package com.sanwei.sanwei4a.util

import android.content.Context
import com.sanwei.sanwei4a.activity.BookDetailsActivity
import org.jetbrains.anko.startActivity

object Router {

    fun toBookDetails(context: Context?, showType: Int, booId: Int) {
        context?.startActivity<BookDetailsActivity>("showType" to showType, "booId" to booId)
    }

}