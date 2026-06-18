package com.example.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_stats")

class UserStatsRepository(private val context: Context) {

    private val DISCIPLINE_SCORE = intPreferencesKey("discipline_score")
    private val CURRENT_STREAK = intPreferencesKey("current_streak")
    private val TOTAL_XP = intPreferencesKey("total_xp")
    
    val userStatsFlow: Flow<UserStats> = context.dataStore.data
        .map { preferences ->
            val score = preferences[DISCIPLINE_SCORE] ?: 50 // Default 50
            val streak = preferences[CURRENT_STREAK] ?: 0
            val xp = preferences[TOTAL_XP] ?: 0
            UserStats(score, streak, xp)
        }

    suspend fun updateDisciplineScore(newScore: Int) {
        context.dataStore.edit { preferences ->
            preferences[DISCIPLINE_SCORE] = newScore.coerceIn(0, 100)
        }
    }

    suspend fun incrementStreak() {
        context.dataStore.edit { preferences ->
            val current = preferences[CURRENT_STREAK] ?: 0
            preferences[CURRENT_STREAK] = current + 1
        }
    }

    suspend fun addXp(amount: Int) {
        context.dataStore.edit { preferences ->
            val current = preferences[TOTAL_XP] ?: 0
            preferences[TOTAL_XP] = current + amount
        }
    }
}

data class UserStats(
    val disciplineScore: Int,
    val currentStreak: Int,
    val totalXp: Int
)
