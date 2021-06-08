package com.example.composeroom.Repository

import com.example.composeroom.RoomDatabase.EntityClass
import com.example.composeroom.RoomDatabase.MessageDAO
import kotlinx.coroutines.flow.Flow

class MessageRepo(private val dao: MessageDAO) {

    //val conversationHistory: Flow<List<EntityClass>> = dao.getAll()

    suspend fun insert(entityClass: EntityClass)
    {
        dao.addMessage(entityClass)
    }
}