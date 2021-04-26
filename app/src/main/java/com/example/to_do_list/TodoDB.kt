package com.example.to_do_list

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoDate::class], version = 2)
abstract class TodoDB : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}