package com.logomann.testjob.data.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.logomann.testjob.data.db.CardEntity

interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(aircraft: CardEntity)

    @Query("SELECT * FROM card_table")
    suspend fun getCardList(): List<CardEntity>

    @Delete
    suspend fun deleteCard(track: CardEntity)
}