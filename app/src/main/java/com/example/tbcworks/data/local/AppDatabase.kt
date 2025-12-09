package com.example.tbcworks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tbcworks.data.local.dao.PostDao
import com.example.tbcworks.data.local.dao.StoryDao
import com.example.tbcworks.data.local.entity.StoryEntity

@Database(entities = [StoryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun postDao(): PostDao
}