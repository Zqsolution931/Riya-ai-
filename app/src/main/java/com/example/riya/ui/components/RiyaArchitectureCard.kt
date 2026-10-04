package com.example.riya.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun RiyaArchitectureCard(
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(RiyaSurface)
            .border(1.dp, RiyaBorderCyan, RoundedCornerShape(8.dp))
            .clickable { expanded = !expanded }
            .padding(10.dp)
    ) {
        Column {
            // Header bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountTree,
                        contentDescription = null,
                        tint = RiyaCyan,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "ARCHITECTURE & CORE ENGINE FLOW",
                        color = RiyaCyan,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (expanded) "Hide Details" else "Tap to Inspect Pipeline",
                        color = RiyaTextSecondary,
                        fontSize = 11.sp
                    )
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = RiyaTextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Main pipeline horizontal block
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                ArchitectureBlock(
                    title = "CLIENT LAYER",
                    items = listOf("Android Native", "WebView UI", "Windows App"),
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = RiyaCyan,
                    modifier = Modifier.size(14.dp)
                )

                ArchitectureBlock(
                    title = "API GATEWAY",
                    items = listOf("REST API", "WebSocket", "Auth Token"),
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = RiyaCyan,
                    modifier = Modifier.size(14.dp)
                )

                ArchitectureBlock(
                    title = "RIYA CORE",
                    items = listOf("AI/LLM Engine", "Voice & Vision", "Task Orchestrator"),
                    isHighlight = true,
                    modifier = Modifier.weight(1.2f)
                )

                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = RiyaCyan,
                    modifier = Modifier.size(14.dp)
                )

                ArchitectureBlock(
                    title = "DATABASE",
                    items = listOf("SQLite (Room)", "Conversations", "Tasks & Memory"),
                    modifier = Modifier.weight(1f)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .background(RiyaSurfaceElevated, RoundedCornerShape(6.dp))
                        .padding(10.dp)
                ) {
                    Text(
                        text = "● RIYA Core Engine Subsystems:",
                        color = RiyaGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "• AI & LLM Engine: Server-side Gemini 3.5 Flash & local contextual intelligence.\n" +
                               "• Voice Engine: Android TextToSpeech synthesis & SpeechRecognizer.\n" +
                               "• Orchestrator: Multi-Agent delegation to Research, Coding, and Android Native Agents.\n" +
                               "• Local Companion Bridge: Preconfigured transport adapter for 127.0.0.1:8000 and 127.0.0.1:8001.\n" +
                               "• SQLite Database: Room persistence on riya.db preserving offline user data.",
                        color = RiyaTextSecondary,
                        fontSize = 10.sp,
                        lineHeight = 15.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ArchitectureBlock(
    title: String,
    items: List<String>,
    isHighlight: Boolean = false,
    modifier: Modifier = Modifier
) {
    val borderColor = if (isHighlight) RiyaCyan else RiyaBorderSubtle
    val bgColor = if (isHighlight) RiyaSurfaceElevated else RiyaSurface

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(6.dp))
            .padding(6.dp)
    ) {
        Column {
            Text(
                text = title,
                color = if (isHighlight) RiyaCyan else RiyaGold,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(3.dp))
            items.forEach { item ->
                Text(
                    text = "• $item",
                    color = RiyaTextSecondary,
                    fontSize = 8.sp,
                    maxLines = 1
                )
            }
        }
    }
}
