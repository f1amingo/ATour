package com.sanwei.sanwei4a.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface SystemNotiDao {
    @Query("SELECT * FROM system_notis")
    fun selectAll():List<SystemNotiEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(noti:SystemNotiEntity)
}