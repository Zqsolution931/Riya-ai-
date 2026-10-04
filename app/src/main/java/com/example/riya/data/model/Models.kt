package com.example.riya.data.model

enum class RiyaOutfit(
    val title: String,
    val subtitle: String,
    val auraHex: Long
) {
    DEFAULT("Default", "Command Center Outfit", 0xFF00E5FF),
    BUSINESS("Business", "Executive Suit", 0xFFFFD54F),
    TECH("Tech", "Cyber Tactical Armor", 0xFF00E676),
    CYBER("Cyber", "Neon Netrunner Gear", 0xFFB388FF),
    CASUAL("Casual", "Smart Everyday Style", 0xFF2979FF),
    FORMAL("Formal", "Diplomatic Gala Attire", 0xFFFF80AB)
}

enum class AvatarState(val label: String, val hexColor: Long) {
    IDLE("Idle", 0xFFFFD54F),
    LISTENING("Listening...", 0xFF00E5FF),
    THINKING("Thinking...", 0xFFB388FF),
    SPEAKING("Speaking...", 0xFF00E676),
    WORKING("Working...", 0xFF2979FF),
    ERROR("Error", 0xFFFF3D71),
    OFFLINE("Offline", 0xFF8B9BB4)
}

enum class WorkspaceTab(val title: String) {
    HOME("Home"),
    CHAT("Chat"),
    TASKS("Tasks"),
    AGENTS("Agents"),
    TOOLS("Tools"),
    MEMORY("Memory"),
    SETTINGS("Settings")
}

enum class SenderType {
    USER, RIYA, SYSTEM, AGENT
}

data class ChatMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: SenderType,
    val text: String,
    val timestamp: String,
    val isAudio: Boolean = false,
    val agentName: String? = null
)

enum class TaskPriority {
    LOW, MEDIUM, HIGH, URGENT
}

enum class TaskStatus {
    QUEUED, RUNNING, PAUSED, COMPLETED, CANCELLED
}

data class RiyaTask(
    val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val status: TaskStatus = TaskStatus.QUEUED,
    val progress: Float = 0f,
    val assignedAgent: String = "Orchestrator",
    val timestamp: String = "Just now"
)

data class AgentItem(
    val id: String,
    val name: String,
    val role: String,
    val status: String,
    val currentActivity: String,
    val iconType: String,
    val lastActive: String = "Just now"
)

data class ToolDefinition(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val actionKey: String
)

data class SystemStatusState(
    val backendOnline: Boolean = true,
    val databaseOnline: Boolean = true,
    val aiConnected: Boolean = true,
    val wsConnected: Boolean = true,
    val permissionsGranted: Boolean = true,
    val storageNormal: Boolean = true
)
