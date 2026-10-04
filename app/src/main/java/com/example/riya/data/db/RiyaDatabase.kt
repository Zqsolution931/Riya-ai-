package com.example.riya.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TaskEntity::class, MessageEntity::class, MemoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RiyaDatabase : RoomDatabase() {
    abstract fun riyaDao(): RiyaDao

    companion object {
        @Volatile
        private var INSTANCE: RiyaDatabase? = null

        fun getDatabase(context: Context): RiyaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RiyaDatabase::class.java,
                    "riya.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
