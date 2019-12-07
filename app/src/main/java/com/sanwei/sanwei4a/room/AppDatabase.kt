package com.sanwei.sanwei4a.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.sanwei.sanwei4a.App

@Database(entities = [(SystemNotiEntity::class)], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        fun get(): AppDatabase {
            return Inner.db
        }
    }

    private object Inner {
        val db = Room.databaseBuilder<AppDatabase>(App.mContext, AppDatabase::class.java,
                "cheese.db").build()
    }

    abstract fun getSystemNotiDao():SystemNotiDao
}