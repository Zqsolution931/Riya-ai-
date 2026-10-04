package com.example.riya.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.riya.data.model.*
import com.example.riya.ui.components.LiveMessageItem
import com.example.riya.ui.viewmodel.RiyaViewModel
import com.example.ui.theme.*

@Composable
fun ChatWorkspace(viewModel: RiyaViewModel, modifier: Modifier = Modifier) {
    val messages by viewModel.messages.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "MULTIMODAL CHAT WORKSPACE",
                color = RiyaCyan,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Text(
                text = "${messages.size} Messages",
                color = RiyaTextMuted,
                fontSize = 11.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(RiyaSurface)
                .border(1.dp, RiyaBorderCyan, RoundedCornerShape(8.dp))
                .padding(10.dp)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize(),
                reverseLayout = true
            ) {
                items(messages.reversed()) { msg ->
                    LiveMessageItem(msg)
                }
            }
        }
    }
}

@Composable
fun TasksWorkspace(viewModel: RiyaViewModel, modifier: Modifier = Modifier) {
    val tasks by viewModel.tasks.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var newTaskTitle by remember { mutableStateOf("") }
    var newTaskDesc by remember { mutableStateOf("") }
    var newTaskPriority by remember { mutableStateOf(TaskPriority.MEDIUM) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "PERSISTENT TASK MANAGEMENT",
                    color = RiyaCyan,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Stored in local riya.db via Room SQLite",
                    color = RiyaTextMuted,
                    fontSize = 11.sp
                )
            }

            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = RiyaCyan),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.testTag("create_task_button")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = RiyaBgDark, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("New Task", color = RiyaBgDark, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(tasks) { task ->
                TaskCardItem(
                    task = task,
                    onStatusChange = { newStatus -> viewModel.updateTaskStatus(task.id, newStatus) },
                    onDelete = { viewModel.deleteTask(task.id) }
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            containerColor = RiyaSurfaceElevated,
            title = {
                Text("Create New RIYA Task", color = RiyaCyan, fontSize = 15.sp, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = newTaskTitle,
                        onValueChange = { newTaskTitle = it },
                        label = { Text("Task Title", color = RiyaTextSecondary) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RiyaCyan,
                            unfocusedBorderColor = RiyaBorderSubtle,
                            focusedTextColor = RiyaTextPrimary,
                            unfocusedTextColor = RiyaTextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = newTaskDesc,
                        onValueChange = { newTaskDesc = it },
                        label = { Text("Description / Prompt", color = RiyaTextSecondary) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = RiyaCyan,
                            unfocusedBorderColor = RiyaBorderSubtle,
                            focusedTextColor = RiyaTextPrimary,
                            unfocusedTextColor = RiyaTextPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newTaskTitle.isNotBlank()) {
                            viewModel.addTask(newTaskTitle, newTaskDesc, newTaskPriority)
                            showDialog = false
                            newTaskTitle = ""
                            newTaskDesc = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = RiyaCyan)
                ) {
                    Text("Add Task", color = RiyaBgDark)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel", color = RiyaTextSecondary)
                }
            }
        )
    }
}

@Composable
fun TaskCardItem(
    task: RiyaTask,
    onStatusChange: (TaskStatus) -> Unit,
    onDelete: () -> Unit
) {
    val statusColor = when (task.status) {
        TaskStatus.COMPLETED -> RiyaNeonGreen
        TaskStatus.RUNNING -> RiyaCyan
        TaskStatus.PAUSED -> RiyaAmber
        TaskStatus.QUEUED -> RiyaGold
        TaskStatus.CANCELLED -> RiyaNeonRed
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(RiyaSurface)
            .border(1.dp, statusColor.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.title,
                    color = RiyaTextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "● ${task.status.name}",
                    color = statusColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = task.description,
                color = RiyaTextSecondary,
                fontSize = 11.sp
            )

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Agent: ${task.assignedAgent} • Priority: ${task.priority.name}",
                    color = RiyaTextMuted,
                    fontSize = 10.sp
                )

                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    if (task.status != TaskStatus.COMPLETED) {
                        IconButton(onClick = { onStatusChange(TaskStatus.COMPLETED) }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.Check, contentDescription = "Complete", tint = RiyaNeonGreen, modifier = Modifier.size(16.dp))
                        }
                    }
                    if (task.status == TaskStatus.RUNNING) {
                        IconButton(onClick = { onStatusChange(TaskStatus.PAUSED) }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.Pause, contentDescription = "Pause", tint = RiyaAmber, modifier = Modifier.size(16.dp))
                        }
                    } else if (task.status == TaskStatus.PAUSED || task.status == TaskStatus.QUEUED) {
                        IconButton(onClick = { onStatusChange(TaskStatus.RUNNING) }, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.PlayArrow, contentDescription = "Run", tint = RiyaCyan, modifier = Modifier.size(16.dp))
                        }
                    }
                    IconButton(onClick = onDelete, modifier = Modifier.size(28.dp)) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = RiyaNeonRed, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AgentsWorkspace(viewModel: RiyaViewModel, modifier: Modifier = Modifier) {
    val agents by viewModel.agents.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "MULTI-AGENT COORDINATION RUNTIME",
            color = RiyaCyan,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Text(
            text = "Coordinated autonomous agents orchestrated by RIYA Core",
            color = RiyaTextMuted,
            fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(agents) { agent ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(RiyaSurface)
                        .border(1.dp, RiyaBorderSubtle, RoundedCornerShape(8.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(RiyaSurfaceElevated)
                                .border(1.5.dp, RiyaCyan, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.SmartToy, contentDescription = null, tint = RiyaCyan, modifier = Modifier.size(20.dp))
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = agent.name, color = RiyaTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                                Text(text = "● ${agent.status}", color = if (agent.status == "Active") RiyaNeonGreen else RiyaTextMuted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                            Text(text = "Role: ${agent.role}", color = RiyaGold, fontSize = 10.5.sp)
                            Text(text = "Current: ${agent.currentActivity}", color = RiyaTextSecondary, fontSize = 10.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ToolsWorkspace(viewModel: RiyaViewModel, modifier: Modifier = Modifier) {
    val tools = listOf(
        Pair("Open YouTube", "Launch video service and control media streams"),
        Pair("Open WhatsApp", "Launch messaging interface and drafts"),
        Pair("Create Reminder", "Set system alarm and task reminders"),
        Pair("System Status", "Run memory, network, and battery telemetry diagnostics"),
        Pair("Web Search", "Search the web for real-time information via Research Agent"),
        Pair("Safe File Operations", "Inspect granted workspace files securely")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "DISCOVERABLE TOOL SYSTEM",
            color = RiyaCyan,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Text(
            text = "Directly executable native Android & cloud actions",
            color = RiyaTextMuted,
            fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(tools) { (title, desc) ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(RiyaSurface)
                        .border(1.dp, RiyaBorderCyan.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                        .clickable { viewModel.executeQuickTool(title) }
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = title, color = RiyaTextPrimary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            Text(text = desc, color = RiyaTextSecondary, fontSize = 10.5.sp)
                        }
                        Button(
                            onClick = { viewModel.executeQuickTool(title) },
                            colors = ButtonDefaults.buttonColors(containerColor = RiyaSurfaceElevated),
                            shape = RoundedCornerShape(6.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, RiyaCyan)
                        ) {
                            Text("Execute", color = RiyaCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MemoryWorkspace(viewModel: RiyaViewModel, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = "APPROVED USER PREFERENCES & MEMORY",
            color = RiyaCyan,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Text(
            text = "Opt-in transparent preferences preserved across sessions",
            color = RiyaTextMuted,
            fontSize = 11.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(RiyaSurface)
                .border(1.dp, RiyaBorderSubtle, RoundedCornerShape(8.dp))
                .padding(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("• Preferred Communication: Hindi, English & Hinglish", color = RiyaTextPrimary, fontSize = 12.sp)
                Text("• Assistant Identity: RIYA (Personal AI Operating Assistant)", color = RiyaTextPrimary, fontSize = 12.sp)
                Text("• Primary Local Companion Ports: 8000 (Frontend) / 8001 (FastAPI Backend)", color = RiyaTextPrimary, fontSize = 12.sp)
                Text("• Autonomy Level: Ask before destructive actions (Default)", color = RiyaTextPrimary, fontSize = 12.sp)
                Text("• Data Storage: Local SQLite Room Database (riya.db)", color = RiyaNeonGreen, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun SettingsWorkspace(viewModel: RiyaViewModel, modifier: Modifier = Modifier) {
    var apiKeyInput by remember { mutableStateOf(viewModel.geminiApiKey.value) }
    var selectedLanguage by remember { mutableStateOf("Hinglish (Hindi + English)") }
    var companionMode by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "RIYA SYSTEM SETTINGS",
            color = RiyaCyan,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        // API Key Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(RiyaSurface)
                .border(1.dp, RiyaBorderSubtle, RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Google Gemini API Key", color = RiyaCyan, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text("Configured via AI Studio Secrets Panel or custom key below:", color = RiyaTextSecondary, fontSize = 10.5.sp)

                OutlinedTextField(
                    value = apiKeyInput,
                    onValueChange = { apiKeyInput = it },
                    placeholder = { Text("Enter Gemini API Key...", color = RiyaTextMuted) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = RiyaCyan,
                        unfocusedBorderColor = RiyaBorderSubtle,
                        focusedTextColor = RiyaTextPrimary,
                        unfocusedTextColor = RiyaTextPrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = { viewModel.setGeminiApiKey(apiKeyInput) },
                    colors = ButtonDefaults.buttonColors(containerColor = RiyaCyan)
                ) {
                    Text("Save & Apply Key", color = RiyaBgDark, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Companion Mode Toggle
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(RiyaSurface)
                .border(1.dp, RiyaBorderSubtle, RoundedCornerShape(8.dp))
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Girlfriend-style Companion Roleplay Mode", color = RiyaGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    Text("Professional mode is default. Optional warm, playful style.", color = RiyaTextMuted, fontSize = 10.5.sp)
                }
                Switch(
                    checked = companionMode,
                    onCheckedChange = { companionMode = it },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = RiyaGold,
                        checkedTrackColor = RiyaSurfaceElevated
                    )
                )
            }
        }
    }
}
