package com.logomann.testjob.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card_table")
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val cardId: Int,
    val content: String
)
