package com.example.meetmewhere2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EventDAO {
    @Insert
    fun insertEvent(eventEntity: EventEntity)

    @Query("Select * FROM Events")
    fun getAllEvents(): List<EventEntity>
}