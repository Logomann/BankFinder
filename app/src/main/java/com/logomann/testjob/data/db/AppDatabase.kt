package com.logomann.testjob.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.logomann.testjob.data.db.dao.CardDao

@Database(
    version = 1,
    entities = [CardEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun aircraftDao(): CardDao

}