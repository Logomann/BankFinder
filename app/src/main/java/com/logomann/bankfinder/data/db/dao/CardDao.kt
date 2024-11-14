package com.logomann.bankfinder.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.logomann.bankfinder.data.db.CardEntity

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(cardEntity: CardEntity)

    @Query("SELECT * FROM card_table ORDER BY cardId DESC")
    suspend fun getCardList(): List<CardEntity>

    @Delete
    suspend fun deleteCard(cardEntity: CardEntity)

    @Query("DELETE FROM card_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM card_table WHERE cardId =:cardId")
    suspend fun getCard(cardId: Int): CardEntity
}