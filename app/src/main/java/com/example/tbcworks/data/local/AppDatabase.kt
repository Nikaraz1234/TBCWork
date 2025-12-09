package com.example.tbcworks.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tbcworks.data.local.dao.PostDao
import com.example.tbcworks.data.local.dao.StoryDao
import com.example.tbcworks.data.local.entity.PostEntity
import com.example.tbcworks.data.local.entity.StoryEntity

@Database(
    entities = [PostEntity::class, StoryEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
    abstract fun storyDao(): StoryDao
}