package com.example.nearmeet.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [EventEntity::class], version = 1)
abstract class NearMeetDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}