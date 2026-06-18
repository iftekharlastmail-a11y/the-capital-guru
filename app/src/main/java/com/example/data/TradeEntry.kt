package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trades")
data class TradeEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tradeDate: Long = System.currentTimeMillis(),
    val market: String,
    val instrument: String,
    val direction: String, // "Long" or "Short"
    val entryPrice: Double,
    val exitPrice: Double,
    val stopLoss: Double,
    val target: Double,
    val risk: Double,
    val reward: Double,
    val pnl: Double,
    val setupType: String,
    val emotionBefore: String,
    val emotionAfter: String,
    val mistakes: String,
    val notes: String
)
