package com.example.riya.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RiyaDao {
    @Query("SELECT * FROM tasks ORDER BY timestamp DESC")
    fun getAllTasks(): Flow<List<TaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)

    @Query("SELECT * FROM messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flow<List<MessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageEntity)

    @Query("DELETE FROM messages")
    suspend fun clearMessages()

    @Query("SELECT * FROM memories ORDER BY timestamp DESC")
    fun getAllMemories(): Flow<List<MemoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMemory(memory: MemoryEntity)

    @Delete
    suspend fun deleteMemory(memory: MemoryEntity)
}
