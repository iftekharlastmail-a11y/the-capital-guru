package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TradeDao {
    @Query("SELECT * FROM trades ORDER BY tradeDate DESC")
    fun getAllTrades(): Flow<List<TradeEntry>>

    @Query("SELECT * FROM trades WHERE id = :id LIMIT 1")
    fun getTradeById(id: Int): Flow<TradeEntry?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrade(trade: TradeEntry)

    @Query("DELETE FROM trades WHERE id = :id")
    suspend fun deleteTradeById(id: Int)
    
    @Query("SELECT SUM(pnl) FROM trades")
    fun getTotalPnl(): Flow<Double?>
}
