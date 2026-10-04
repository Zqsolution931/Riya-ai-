package com.example.riya.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val priority: String,
    val status: String,
    val progress: Float,
    val assignedAgent: String,
    val timestamp: String
)

@Entity(tableName = "messages")
data class MessageEntity(
    @PrimaryKey val id: String,
    val sender: String,
    val text: String,
    val timestamp: String,
    val isAudio: Boolean,
    val agentName: String?
)

@Entity(tableName = "memories")
data class MemoryEntity(
    @PrimaryKey val id: String,
    val key: String,
    val value: String,
    val category: String,
    val timestamp: String
)
