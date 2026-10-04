package com.example.riya.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.riya.data.model.WorkspaceTab
import com.example.riya.ui.components.*
import com.example.riya.ui.viewmodel.RiyaViewModel
import com.example.ui.theme.RiyaCanvas

@Composable
fun RiyaMainScreen(
    viewModel: RiyaViewModel
) {
    val currentTab by viewModel.currentTab.collectAsState()
    val currentOutfit by viewModel.currentOutfit.collectAsState()
    val avatarState by viewModel.avatarState.collectAsState()
    val greetingMessage by viewModel.greetingMessage.collectAsState()
    val messages by viewModel.messages.collectAsState()
    val agents by viewModel.agents.collectAsState()
    val systemStatus by viewModel.systemStatus.collectAsState()
    val isListening by viewModel.isListening.collectAsState()
    val micLevel by viewModel.micLevel.collectAsState()
    val inputText by viewModel.inputText.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(RiyaCanvas),
        topBar = {
            RiyaHeader(
                avatarState = avatarState,
                onSettingsClick = { viewModel.setTab(WorkspaceTab.SETTINGS) }
            )
        },
        bottomBar = {
            RiyaBottomDock(
                systemStatus = systemStatus,
                isListening = isListening,
                micLevel = micLevel,
                inputText = inputText,
                onInputTextChange = { viewModel.setInputText(it) },
                onSend = { viewModel.sendMessage(inputText) },
                onToggleMic = { viewModel.toggleVoiceListening() },
                onCameraClick = { viewModel.sendMessage("Analyze visual camera scene with Vision Agent") },
                onTasksClick = { viewModel.setTab(WorkspaceTab.TASKS) },
                onAppsClick = { viewModel.setTab(WorkspaceTab.TOOLS) },
                onSettingsClick = { viewModel.setTab(WorkspaceTab.SETTINGS) },
                onStopAll = { viewModel.stopAll() }
            )
        }
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(RiyaCanvas)
        ) {
            val isWideScreen = maxWidth >= 840.dp
            val isMediumScreen = maxWidth >= 600.dp && maxWidth < 840.dp

            if (currentTab == WorkspaceTab.HOME) {
                if (isWideScreen) {
                    // Full 3-Column Command Center matching the reference screenshot!
                    Row(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Left Column: Navigation, Quick Access & Outfit Selector
                        RiyaSidebar(
                            currentTab = currentTab,
                            currentOutfit = currentOutfit,
                            onTabSelected = { viewModel.setTab(it) },
                            onOutfitSelected = { viewModel.setOutfit(it) },
                            onQuickAction = { viewModel.executeQuickTool(it) }
                        )

                        // Center Stage: Avatar HUD, Platforms, Architecture & Feature Grid
                        val centerScrollState = rememberScrollState()
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .verticalScroll(centerScrollState)
                                .padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            RiyaAvatarHUD(
                                avatarState = avatarState,
                                outfit = currentOutfit,
                                greetingText = greetingMessage,
                                micLevel = micLevel
                            )

                            RiyaPlatformCards()

                            RiyaArchitectureCard()

                            RiyaFeatureGrid()

                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        // Right Column: Live Messages & Agent Activity Tracker
                        RiyaLivePanel(
                            messages = messages,
                            agents = agents,
                            onViewAllMessages = { viewModel.setTab(WorkspaceTab.CHAT) },
                            onViewAllAgents = { viewModel.setTab(WorkspaceTab.AGENTS) }
                        )
                    }
                } else {
                    // Reflow for Compact & Medium Mobile Viewports
                    val mobileScrollState = rememberScrollState()
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(mobileScrollState)
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Horizontal Workspace Chip Bar
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            listOf(
                                WorkspaceTab.HOME,
                                WorkspaceTab.CHAT,
                                WorkspaceTab.TASKS,
                                WorkspaceTab.AGENTS,
                                WorkspaceTab.TOOLS,
                                WorkspaceTab.SETTINGS
                            ).forEach { tab ->
                                NavItem(
                                    tab = tab,
                                    icon = when (tab) {
                                        WorkspaceTab.HOME -> Icons.Default.Home
                                        WorkspaceTab.CHAT -> Icons.Default.ChatBubble
                                        WorkspaceTab.TASKS -> Icons.Default.CheckCircle
                                        WorkspaceTab.AGENTS -> Icons.Default.Groups
                                        WorkspaceTab.TOOLS -> Icons.Default.Build
                                        else -> Icons.Default.Settings
                                    },
                                    isSelected = currentTab == tab,
                                    onClick = { viewModel.setTab(tab) }
                                )
                            }
                        }

                        RiyaAvatarHUD(
                            avatarState = avatarState,
                            outfit = currentOutfit,
                            greetingText = greetingMessage,
                            micLevel = micLevel
                        )

                        RiyaPlatformCards()

                        RiyaArchitectureCard()

                        RiyaFeatureGrid()

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            } else {
                // Secondary Workspace View with Sidebar & Back Navigation
                Row(modifier = Modifier.fillMaxSize()) {
                    if (isWideScreen) {
                        RiyaSidebar(
                            currentTab = currentTab,
                            currentOutfit = currentOutfit,
                            onTabSelected = { viewModel.setTab(it) },
                            onOutfitSelected = { viewModel.setOutfit(it) },
                            onQuickAction = { viewModel.executeQuickTool(it) }
                        )
                    }

                    Box(modifier = Modifier.weight(1f)) {
                        when (currentTab) {
                            WorkspaceTab.CHAT -> ChatWorkspace(viewModel)
                            WorkspaceTab.TASKS -> TasksWorkspace(viewModel)
                            WorkspaceTab.AGENTS -> AgentsWorkspace(viewModel)
                            WorkspaceTab.TOOLS -> ToolsWorkspace(viewModel)
                            WorkspaceTab.MEMORY -> MemoryWorkspace(viewModel)
                            WorkspaceTab.SETTINGS -> SettingsWorkspace(viewModel)
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}
