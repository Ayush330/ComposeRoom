package com.example.composeroom.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = arrayOf(EntityClass::class),version = 2,exportSchema = false)
abstract class DatabaseClass : RoomDatabase() {
    abstract fun messageDAO():MessageDAO

    companion object{
        @Volatile
        private var INSTANCE:DatabaseClass? = null

        fun getDatabase(context: Context):DatabaseClass{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                DatabaseClass::class.java,
                "messageDatabase")
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance

                instance
            }
        }
    }
}