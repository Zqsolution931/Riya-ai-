package com.example

import com.example.riya.data.model.*
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testRiyaOutfitConfiguration() {
        assertEquals(6, RiyaOutfit.values().size)
        assertEquals("Default", RiyaOutfit.DEFAULT.title)
        assertEquals("Business", RiyaOutfit.BUSINESS.title)
        assertEquals("Tech", RiyaOutfit.TECH.title)
        assertEquals("Cyber", RiyaOutfit.CYBER.title)
        assertEquals("Casual", RiyaOutfit.CASUAL.title)
        assertEquals("Formal", RiyaOutfit.FORMAL.title)
    }

    @Test
    fun testAvatarStates() {
        assertEquals("Listening...", AvatarState.LISTENING.label)
        assertEquals("Speaking...", AvatarState.SPEAKING.label)
        assertEquals("Thinking...", AvatarState.THINKING.label)
        assertEquals("Idle", AvatarState.IDLE.label)
    }

    @Test
    fun testTaskCreationAndStatus() {
        val task = RiyaTask(
            title = "Analyze Q3 Report",
            description = "Research task with Research Agent",
            priority = TaskPriority.HIGH,
            status = TaskStatus.QUEUED,
            assignedAgent = "Research Agent"
        )
        assertEquals("Analyze Q3 Report", task.title)
        assertEquals(TaskStatus.QUEUED, task.status)
        assertEquals("Research Agent", task.assignedAgent)
    }

    @Test
    fun testChatMessageTurn() {
        val msg = ChatMessage(
            sender = SenderType.RIYA,
            text = "YouTube open kar rahi hoon...",
            timestamp = "10:31 AM",
            isAudio = true
        )
        assertEquals(SenderType.RIYA, msg.sender)
        assertTrue(msg.isAudio)
    }
}
