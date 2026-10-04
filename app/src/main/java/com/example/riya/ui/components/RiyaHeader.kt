package com.example.riya.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.riya.data.model.AvatarState
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun RiyaHeader(
    avatarState: AvatarState,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentTime by remember { mutableStateOf("") }
    var currentDate by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val timeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        while (true) {
            val now = Date()
            currentTime = timeFormat.format(now)
            currentDate = dateFormat.format(now)
            delay(1000)
        }
    }

    // Glowing pulse for online indicator
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "alpha"
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(
                    colors = listOf(RiyaSurfaceElevated.copy(alpha = 0.95f), RiyaCanvas.copy(alpha = 0.95f))
                )
            )
            .border(1.dp, RiyaBorderCyan, RoundedCornerShape(0.dp))
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Logo & Title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Stylized Riya emblem
            Box(
                modifier = Modifier
                    .size(38.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            listOf(RiyaCyan.copy(alpha = 0.4f), RiyaSurface)
                        )
                    )
                    .border(1.5.dp, RiyaCyan, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = "RIYA Logo",
                    tint = RiyaCyan,
                    modifier = Modifier.size(22.dp)
                )
            }

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "RIYA",
                        color = RiyaCyan,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 2.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Your Personal AI Assistant",
                        color = RiyaTextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Smart • Helpful • Always With You",
                    color = RiyaTextMuted,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }

        // Status Chips & Time
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Online Chip
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(RiyaSurface, RoundedCornerShape(12.dp))
                    .border(1.dp, RiyaNeonGreen.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(RiyaNeonGreen.copy(alpha = alphaAnim))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Online",
                    color = RiyaNeonGreen,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // AI Model Chip
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(RiyaSurface, RoundedCornerShape(12.dp))
                    .border(1.dp, RiyaCyan.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Bolt,
                    contentDescription = null,
                    tint = RiyaCyan,
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "AI: Gemini",
                    color = RiyaCyan,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Mic Status Chip
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(RiyaSurface, RoundedCornerShape(12.dp))
                    .border(1.dp, Color(avatarState.hexColor).copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = null,
                    tint = Color(avatarState.hexColor),
                    modifier = Modifier.size(13.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Mic: ${avatarState.label}",
                    color = Color(avatarState.hexColor),
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            // Real Clock
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = currentTime,
                    color = RiyaTextPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = currentDate,
                    color = RiyaTextMuted,
                    fontSize = 10.sp
                )
            }

            // Settings quick button
            IconButton(
                onClick = onSettingsClick,
                modifier = Modifier
                    .size(36.dp)
                    .testTag("header_settings_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = RiyaTextSecondary
                )
            }
        }
    }
}
