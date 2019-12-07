package com.sanwei.sanwei4a.room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "system_notis")
class SystemNotiEntity(var title: String,
                       var content: String,
                       var timestamp: Long) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}