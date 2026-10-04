package com.example.riya.ui.components

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
import com.example.riya.data.model.AgentItem
import com.example.riya.data.model.ChatMessage
import com.example.riya.data.model.SenderType
import com.example.ui.theme.*

@Composable
fun RiyaLivePanel(
    messages: List<ChatMessage>,
    agents: List<AgentItem>,
    onViewAllMessages: () -> Unit,
    onViewAllAgents: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .widthIn(min = 260.dp, max = 340.dp)
            .background(RiyaSurface)
            .border(1.dp, RiyaBorderCyan, RoundedCornerShape(0.dp))
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Section 1: Live Messages
        Box(
            modifier = Modifier
                .weight(1.2f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(RiyaSurfaceElevated)
                .border(1.dp, RiyaBorderSubtle, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChatBubbleOutline,
                            contentDescription = null,
                            tint = RiyaCyan,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "Live Messages",
                            color = RiyaTextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "View All",
                        color = RiyaCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onViewAllMessages() }
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(messages.takeLast(6).reversed()) { msg ->
                        LiveMessageItem(msg)
                    }
                }
            }
        }

        // Section 2: Agent Activity
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(RiyaSurfaceElevated)
                .border(1.dp, RiyaBorderSubtle, RoundedCornerShape(8.dp))
                .padding(8.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SupportAgent,
                            contentDescription = null,
                            tint = RiyaGold,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "Agent Activity",
                            color = RiyaTextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "View All",
                        color = RiyaGold,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.clickable { onViewAllAgents() }
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(agents.take(4)) { agent ->
                        AgentActivityItem(agent)
                    }
                }
            }
        }
    }
}

@Composable
fun LiveMessageItem(msg: ChatMessage) {
    val (senderName, tintColor, icon) = when (msg.sender) {
        SenderType.USER -> Triple("You", RiyaCyan, Icons.Default.Person)
        SenderType.RIYA -> Triple("RIYA", RiyaGold, Icons.Default.AutoAwesome)
        SenderType.SYSTEM -> Triple("System", RiyaNeonGreen, Icons.Default.CheckCircle)
        SenderType.AGENT -> Triple(msg.agentName ?: "Agent", RiyaNeonPurple, Icons.Default.SmartToy)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(RiyaSurface)
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(tintColor.copy(alpha = 0.2f))
                .border(1.dp, tintColor.copy(alpha = 0.6f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tintColor,
                modifier = Modifier.size(14.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = senderName,
                    color = tintColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = msg.timestamp,
                    color = RiyaTextMuted,
                    fontSize = 9.sp
                )
            }
            Text(
                text = msg.text,
                color = RiyaTextPrimary,
                fontSize = 10.sp,
                lineHeight = 14.sp
            )
        }
    }
}

@Composable
fun AgentActivityItem(agent: AgentItem) {
    val icon = when (agent.iconType) {
        "search" -> Icons.Default.Search
        "code" -> Icons.Default.Code
        "android" -> Icons.Default.Android
        "desktop" -> Icons.Default.DesktopWindows
        else -> Icons.Default.Hub
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(RiyaSurface)
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(RiyaCyan.copy(alpha = 0.15f))
                .border(1.dp, RiyaCyan.copy(alpha = 0.4f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = RiyaCyan,
                modifier = Modifier.size(15.dp)
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = agent.name,
                    color = RiyaTextPrimary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = agent.lastActive,
                    color = RiyaTextMuted,
                    fontSize = 9.sp
                )
            }
            Text(
                text = agent.currentActivity,
                color = RiyaTextSecondary,
                fontSize = 9.5.sp,
                maxLines = 1
            )
        }
    }
}
