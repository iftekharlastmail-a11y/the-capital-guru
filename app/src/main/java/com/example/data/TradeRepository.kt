package com.example.data

import kotlinx.coroutines.flow.Flow

class TradeRepository(private val tradeDao: TradeDao) {
    val allTrades: Flow<List<TradeEntry>> = tradeDao.getAllTrades()
    val totalPnl: Flow<Double?> = tradeDao.getTotalPnl()

    fun getTradeById(id: Int): Flow<TradeEntry?> {
        return tradeDao.getTradeById(id)
    }

    suspend fun insert(trade: TradeEntry) {
        tradeDao.insertTrade(trade)
    }

    suspend fun deleteById(id: Int) {
        tradeDao.deleteTradeById(id)
    }
}
