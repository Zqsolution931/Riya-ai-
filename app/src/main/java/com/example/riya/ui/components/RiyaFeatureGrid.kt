package com.example.riya.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun RiyaFeatureGrid(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Row 1: Key Features & AI Providers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FeatureSectionCard(
                title = "KEY FEATURES",
                icon = Icons.Default.Stars,
                titleColor = RiyaCyan,
                modifier = Modifier.weight(1f),
                items = listOf(
                    "Natural Language Understanding",
                    "Voice & Speech (Hindi + English + Hinglish)",
                    "App Launch & Web Control",
                    "WhatsApp & Calling Integration",
                    "Smart Task Management & SQLite",
                    "Multi-Agent Execution Pipeline",
                    "Offline Local Assistant Mode",
                    "Privacy & Permissions Sandbox"
                )
            )

            FeatureSectionCard(
                title = "AI & PROVIDERS",
                icon = Icons.Default.Bolt,
                titleColor = RiyaGold,
                modifier = Modifier.weight(1f),
                items = listOf(
                    "● Gemini 3.5 Flash (Google) - ACTIVE",
                    "○ OpenAI & Local Models - Ready",
                    "• Configurable System Prompt",
                    "• Temperature & Token Limits",
                    "• Voice synthesis: Feminine tone",
                    "• Secure Environment Credentials"
                )
            )
        }

        // Row 2: Tool System & Agent System
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FeatureSectionCard(
                title = "TOOL SYSTEM",
                icon = Icons.Default.Build,
                titleColor = RiyaNeonGreen,
                modifier = Modifier.weight(1f),
                items = listOf(
                    "• Open App (YouTube, WhatsApp, System)",
                    "• Web Search & Knowledge Gathering",
                    "• Create Note & Persistent Memory",
                    "• Task Reminders & Alarms",
                    "• Device Telemetry & Diagnostics",
                    "• File Operations & Sandbox"
                )
            )

            FeatureSectionCard(
                title = "MULTI-AGENT RUNTIME",
                icon = Icons.Default.Groups,
                titleColor = RiyaNeonPurple,
                modifier = Modifier.weight(1f),
                items = listOf(
                    "• Orchestrator (Decomposition & Plan)",
                    "• Research Agent (Web & Citation)",
                    "• Coding Agent (Synthesis & Refactoring)",
                    "• Android Agent (Device APIs & Intents)",
                    "• Windows Companion (Ports 8000/8001)",
                    "• Data Agent (SQLite riya.db)"
                )
            )
        }

        // Row 3: Endpoints & Tech Stack
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FeatureSectionCard(
                title = "RIYA ENDPOINTS",
                icon = Icons.Default.Api,
                titleColor = RiyaCyan,
                modifier = Modifier.weight(1f),
                items = listOf(
                    "GET  /api/status      (Health & Node)",
                    "GET  /api/config      (Non-secret Manifest)",
                    "POST /api/chat        (Assistant Prompt)",
                    "POST /api/task        (Task Creation)",
                    "GET  /api/tools       (Tool Registry)",
                    "WS   /ws              (Real-time Stream)"
                )
            )

            FeatureSectionCard(
                title = "TECH STACK",
                icon = Icons.Default.Code,
                titleColor = RiyaAmber,
                modifier = Modifier.weight(1f),
                items = listOf(
                    "• Kotlin & Jetpack Compose (Android)",
                    "• Python & FastAPI (Backend on 8001)",
                    "• SQLite / Room Database (riya.db)",
                    "• WebSockets for Live Stream",
                    "• Google Gemini 3.5 Flash Core",
                    "• Material Design 3 Cybernetic HUD"
                )
            )
        }
    }
}

@Composable
fun FeatureSectionCard(
    title: String,
    icon: ImageVector,
    titleColor: Color,
    items: List<String>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(RiyaSurface)
            .border(1.dp, titleColor.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = titleColor,
                    modifier = Modifier.size(15.dp)
                )
                Text(
                    text = title,
                    color = titleColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            items.forEach { item ->
                Text(
                    text = item,
                    color = RiyaTextSecondary,
                    fontSize = 9.5.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}
