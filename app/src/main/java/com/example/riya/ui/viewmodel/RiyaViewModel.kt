package com.example.riya.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.riya.data.db.MessageEntity
import com.example.riya.data.db.RiyaDatabase
import com.example.riya.data.db.TaskEntity
import com.example.riya.data.engine.RiyaAssistantEngine
import com.example.riya.data.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RiyaViewModel(application: Application) : AndroidViewModel(application) {

    private val db = RiyaDatabase.getDatabase(application)
    private val dao = db.riyaDao()
    val engine = RiyaAssistantEngine(application)

    private val _currentTab = MutableStateFlow(WorkspaceTab.HOME)
    val currentTab: StateFlow<WorkspaceTab> = _currentTab.asStateFlow()

    private val _currentOutfit = MutableStateFlow(RiyaOutfit.DEFAULT)
    val currentOutfit: StateFlow<RiyaOutfit> = _currentOutfit.asStateFlow()

    private val _avatarState = MutableStateFlow(AvatarState.IDLE)
    val avatarState: StateFlow<AvatarState> = _avatarState.asStateFlow()

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _tasks = MutableStateFlow<List<RiyaTask>>(emptyList())
    val tasks: StateFlow<List<RiyaTask>> = _tasks.asStateFlow()

    private val _agents = MutableStateFlow<List<AgentItem>>(emptyList())
    val agents: StateFlow<List<AgentItem>> = _agents.asStateFlow()

    private val _systemStatus = MutableStateFlow(SystemStatusState())
    val systemStatus: StateFlow<SystemStatusState> = _systemStatus.asStateFlow()

    private val _micLevel = MutableStateFlow(0f)
    val micLevel: StateFlow<Float> = _micLevel.asStateFlow()

    private val _isListening = MutableStateFlow(false)
    val isListening: StateFlow<Boolean> = _isListening.asStateFlow()

    private val _inputText = MutableStateFlow("")
    val inputText: StateFlow<String> = _inputText.asStateFlow()

    private val _speechText = MutableStateFlow("")
    val speechText: StateFlow<String> = _speechText.asStateFlow()

    private val _greetingMessage = MutableStateFlow("“ Hi, I'm RIYA.\nI'm here to make your life easier, smarter and more productive. ”")
    val greetingMessage: StateFlow<String> = _greetingMessage.asStateFlow()

    private val _geminiApiKey = MutableStateFlow("")
    val geminiApiKey: StateFlow<String> = _geminiApiKey.asStateFlow()

    private val timeFormatter = SimpleDateFormat("h:mm a", Locale.getDefault())

    init {
        initDefaultAgents()
        observeDatabase()
        seedInitialDataIfEmpty()
    }

    private fun initDefaultAgents() {
        _agents.value = listOf(
            AgentItem("research", "Research Agent", "Web & Knowledge", "Active", "Web search in progress...", "search", "2m ago"),
            AgentItem("coding", "Coding Agent", "Architecture & Dev", "Active", "Analyzing code...", "code", "5m ago"),
            AgentItem("android", "Android Agent", "Native Device APIs", "Idle", "Checking permissions...", "android", "6m ago"),
            AgentItem("orchestrator", "Orchestrator", "Task Decomposition", "Active", "Coordinating pipeline", "hub", "Just now"),
            AgentItem("windows", "Windows Companion", "PC Bridge (Port 8001)", "Standby", "Awaiting local bridge", "desktop", "10m ago"),
            AgentItem("data", "Data Agent", "SQLite & Pipelines", "Idle", "Room DB synchronized", "database", "1m ago")
        )
    }

    private fun observeDatabase() {
        viewModelScope.launch {
            dao.getAllMessages().collect { entities ->
                if (entities.isNotEmpty()) {
                    _messages.value = entities.map {
                        ChatMessage(
                            id = it.id,
                            sender = SenderType.valueOf(it.sender),
                            text = it.text,
                            timestamp = it.timestamp,
                            isAudio = it.isAudio,
                            agentName = it.agentName
                        )
                    }
                }
            }
        }

        viewModelScope.launch {
            dao.getAllTasks().collect { entities ->
                if (entities.isNotEmpty()) {
                    _tasks.value = entities.map {
                        RiyaTask(
                            id = it.id,
                            title = it.title,
                            description = it.description,
                            priority = TaskPriority.valueOf(it.priority),
                            status = TaskStatus.valueOf(it.status),
                            progress = it.progress,
                            assignedAgent = it.assignedAgent,
                            timestamp = it.timestamp
                        )
                    }
                }
            }
        }
    }

    private fun seedInitialDataIfEmpty() {
        viewModelScope.launch {
            // Seed sample reference messages
            val initMsgs = listOf(
                MessageEntity(UUID.randomUUID().toString(), SenderType.USER.name, "Riya, YouTube kholo.", "10:31 AM", false, null),
                MessageEntity(UUID.randomUUID().toString(), SenderType.RIYA.name, "YouTube open kar rahi hoon...", "10:31 AM", true, null),
                MessageEntity(UUID.randomUUID().toString(), SenderType.SYSTEM.name, "Task 'Project Report' completed.", "10:28 AM", false, null),
                MessageEntity(UUID.randomUUID().toString(), SenderType.AGENT.name, "Research completed. 5 sources found.", "10:25 AM", false, "Research Agent"),
                MessageEntity(UUID.randomUUID().toString(), SenderType.USER.name, "Set reminder for GST filing.", "10:22 AM", false, null)
            )
            initMsgs.forEach { dao.insertMessage(it) }

            // Seed sample tasks
            val initTasks = listOf(
                TaskEntity(UUID.randomUUID().toString(), "Analyze Project Report", "Summarize Q3 financial metrics with Research Agent", TaskPriority.HIGH.name, TaskStatus.COMPLETED.name, 1.0f, "Research Agent", "10:28 AM"),
                TaskEntity(UUID.randomUUID().toString(), "GST Filing Reminder", "Check tax deadlines and draft compliance checklist", TaskPriority.URGENT.name, TaskStatus.RUNNING.name, 0.65f, "Orchestrator", "10:22 AM"),
                TaskEntity(UUID.randomUUID().toString(), "Windows Desktop Sync", "Establish local bridge to port 8001 companion", TaskPriority.MEDIUM.name, TaskStatus.QUEUED.name, 0.1f, "Windows Companion", "10:15 AM")
            )
            initTasks.forEach { dao.insertTask(it) }
        }
    }

    fun setTab(tab: WorkspaceTab) {
        _currentTab.value = tab
    }

    fun setOutfit(outfit: RiyaOutfit) {
        _currentOutfit.value = outfit
        updateAgentActivity("Avatar", "Updated outfit aura to ${outfit.title}")
    }

    fun setInputText(text: String) {
        _inputText.value = text
    }

    fun setGeminiApiKey(key: String) {
        _geminiApiKey.value = key
        engine.userApiKey = key
    }

    fun sendMessage(text: String) {
        if (text.isBlank()) return
        val now = timeFormatter.format(Date())
        val userMsg = ChatMessage(sender = SenderType.USER, text = text, timestamp = now)

        viewModelScope.launch {
            dao.insertMessage(
                MessageEntity(userMsg.id, userMsg.sender.name, userMsg.text, userMsg.timestamp, false, null)
            )
            _inputText.value = ""

            // Process with Riya Engine
            val result = engine.processUserPrompt(
                prompt = text,
                history = _messages.value,
                onStateChange = { state -> _avatarState.value = state },
                onAgentActivity = { agent, action -> updateAgentActivity(agent, action) }
            )

            val replyText = result.first
            val riyaMsg = ChatMessage(
                sender = SenderType.RIYA,
                text = replyText,
                timestamp = timeFormatter.format(Date()),
                isAudio = true
            )

            dao.insertMessage(
                MessageEntity(riyaMsg.id, riyaMsg.sender.name, riyaMsg.text, riyaMsg.timestamp, true, null)
            )

            _greetingMessage.value = "“ $replyText ”"
            engine.speak(replyText) {
                _avatarState.value = AvatarState.IDLE
            }

            delay(3000)
            _avatarState.value = AvatarState.IDLE
        }
    }

    fun toggleVoiceListening() {
        if (_isListening.value) {
            _isListening.value = false
            _avatarState.value = AvatarState.IDLE
            engine.stopListening()
        } else {
            _isListening.value = true
            _avatarState.value = AvatarState.LISTENING
            engine.startListening(
                onResult = { recognizedText ->
                    _isListening.value = false
                    _speechText.value = recognizedText
                    sendMessage(recognizedText)
                },
                onError = { err ->
                    _isListening.value = false
                    _avatarState.value = AvatarState.IDLE
                    updateAgentActivity("Voice Agent", err)
                },
                onRmsChanged = { rms ->
                    _micLevel.value = (rms.coerceIn(0f, 10f) / 10f)
                }
            )
        }
    }

    fun addTask(title: String, desc: String, priority: TaskPriority = TaskPriority.MEDIUM) {
        viewModelScope.launch {
            val entity = TaskEntity(
                id = UUID.randomUUID().toString(),
                title = title,
                description = desc,
                priority = priority.name,
                status = TaskStatus.QUEUED.name,
                progress = 0f,
                assignedAgent = "Orchestrator",
                timestamp = timeFormatter.format(Date())
            )
            dao.insertTask(entity)
            updateAgentActivity("Orchestrator", "Scheduled task: $title")
        }
    }

    fun updateTaskStatus(taskId: String, newStatus: TaskStatus) {
        viewModelScope.launch {
            val existing = _tasks.value.find { it.id == taskId } ?: return@launch
            val updated = TaskEntity(
                id = existing.id,
                title = existing.title,
                description = existing.description,
                priority = existing.priority.name,
                status = newStatus.name,
                progress = if (newStatus == TaskStatus.COMPLETED) 1.0f else existing.progress,
                assignedAgent = existing.assignedAgent,
                timestamp = existing.timestamp
            )
            dao.updateTask(updated)
            updateAgentActivity("Task Manager", "Task '${existing.title}' set to ${newStatus.name}")
        }
    }

    fun deleteTask(taskId: String) {
        viewModelScope.launch {
            val existing = _tasks.value.find { it.id == taskId } ?: return@launch
            val entity = TaskEntity(
                id = existing.id,
                title = existing.title,
                description = existing.description,
                priority = existing.priority.name,
                status = existing.status.name,
                progress = existing.progress,
                assignedAgent = existing.assignedAgent,
                timestamp = existing.timestamp
            )
            dao.deleteTask(entity)
        }
    }

    fun executeQuickTool(toolName: String) {
        when (toolName) {
            "Open YouTube" -> sendMessage("Open YouTube")
            "Open WhatsApp" -> sendMessage("Open WhatsApp")
            "Create Reminder" -> sendMessage("Set reminder for tomorrow morning")
            "System Status" -> sendMessage("Check system status and diagnostics")
            else -> sendMessage("Execute tool $toolName")
        }
    }

    fun stopAll() {
        engine.stopSpeaking()
        engine.stopListening()
        _isListening.value = false
        _avatarState.value = AvatarState.IDLE
        viewModelScope.launch {
            _tasks.value.filter { it.status == TaskStatus.RUNNING }.forEach { task ->
                updateTaskStatus(task.id, TaskStatus.PAUSED)
            }
            updateAgentActivity("Security", "EMERGENCY STOP executed. Active pipelines paused.")
        }
    }

    private fun updateAgentActivity(agentName: String, activity: String) {
        val current = _agents.value.toMutableList()
        val index = current.indexOfFirst { it.name.contains(agentName, ignoreCase = true) }
        if (index >= 0) {
            val old = current[index]
            current[index] = old.copy(currentActivity = activity, lastActive = "Just now", status = "Active")
            _agents.value = current
        }
    }

    override fun onCleared() {
        super.onCleared()
        engine.release()
    }
}
