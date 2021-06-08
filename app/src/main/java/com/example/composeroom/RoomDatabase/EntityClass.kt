package com.example.composeroom.RoomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "MessageTable")
data class EntityClass(
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0,
    @ColumnInfo(name = "message") val message:String,
    @ColumnInfo(name = "entity") val entity:String

)