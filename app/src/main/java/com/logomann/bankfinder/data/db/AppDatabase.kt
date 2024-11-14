package com.logomann.bankfinder.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.logomann.bankfinder.data.db.dao.CardDao

@Database(
    version = 1,
    entities = [CardEntity::class]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}