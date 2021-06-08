package com.example.composeroom.RoomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface MessageDAO {

    @Query("SELECT * FROM MessageTable")
    fun getAll(): Flow<List<EntityClass>>

    @Insert
    suspend fun addMessage (messageUtil:EntityClass)

    @Delete
    suspend fun deleteMessage(messageUtil: EntityClass)
}