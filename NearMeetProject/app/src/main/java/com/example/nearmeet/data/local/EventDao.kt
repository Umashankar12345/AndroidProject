package com.example.nearmeet.data.local
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {

    @Query("SELECT * FROM events")
    fun getAllEvents(): Flow<List<EventEntity>>

    @Upsert
    suspend fun upsertEvents(events: List<EventEntity>)

    @Query("DELETE FROM events")
    suspend fun clearAll()
}