package com.example.riya.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.riya.data.model.SystemStatusState
import com.example.ui.theme.*

@Composable
fun RiyaBottomDock(
    systemStatus: SystemStatusState,
    isListening: Boolean,
    micLevel: Float,
    inputText: String,
    onInputTextChange: (String) -> Unit,
    onSend: () -> Unit,
    onToggleMic: () -> Unit,
    onCameraClick: () -> Unit,
    onTasksClick: () -> Unit,
    onAppsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onStopAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "mic_waves")
    val waveScale by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.15f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(RiyaCanvas)
            .border(1.dp, RiyaBorderCyan, RoundedCornerShape(0.dp))
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Main Control Bar: Status Chips + Center Mic + Input + Quick Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Subsystem Status Indicators
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                StatusBadge("Backend", "Online", RiyaNeonGreen)
                StatusBadge("Database", "Online", RiyaNeonGreen)
                StatusBadge("AI", "Connected", RiyaCyan)
                StatusBadge("Storage", "Normal", RiyaTextSecondary)
            }

            // Center Microphone with Audio Waves
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Wave bars on left
                AudioWaveGroup(isListening, micLevel)

                // Mic Circle Button
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.radialGradient(
                                listOf(
                                    if (isListening) RiyaCyan else RiyaSurfaceElevated,
                                    RiyaSurface
                                )
                            )
                        )
                        .border(
                            2.dp,
                            if (isListening) RiyaCyan else RiyaBorderCyan,
                            CircleShape
                        )
                        .clickable { onToggleMic() }
                        .testTag("riya_mic_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isListening) Icons.Default.GraphicEq else Icons.Default.Mic,
                        contentDescription = "Tap to talk",
                        tint = if (isListening) RiyaBgDark else RiyaCyan,
                        modifier = Modifier.size(24.dp)
                    )
                }

                // Wave bars on right
                AudioWaveGroup(isListening, micLevel)
            }

            // Input Field & Action Buttons
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Text input container
                Box(
                    modifier = Modifier
                        .widthIn(min = 180.dp, max = 280.dp)
                        .height(38.dp)
                        .clip(RoundedCornerShape(19.dp))
                        .background(RiyaSurfaceElevated)
                        .border(1.dp, RiyaBorderSubtle, RoundedCornerShape(19.dp))
                        .padding(horizontal = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        BasicTextField(
                            value = inputText,
                            onValueChange = onInputTextChange,
                            singleLine = true,
                            textStyle = TextStyle(
                                color = RiyaTextPrimary,
                                fontSize = 12.sp
                            ),
                            cursorBrush = SolidColor(RiyaCyan),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("riya_input_field"),
                            decorationBox = { innerTextField ->
                                if (inputText.isEmpty()) {
                                    Text(
                                        text = "Type a message...",
                                        color = RiyaTextMuted,
                                        fontSize = 12.sp
                                    )
                                }
                                innerTextField()
                            }
                        )

                        IconButton(
                            onClick = onSend,
                            modifier = Modifier
                                .size(28.dp)
                                .testTag("riya_send_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send",
                                tint = if (inputText.isNotBlank()) RiyaCyan else RiyaTextMuted,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }

                // Quick Dock buttons (Camera, Tasks, Apps, Settings)
                IconButton(onClick = onCameraClick, modifier = Modifier.size(34.dp)) {
                    Icon(imageVector = Icons.Default.CameraAlt, contentDescription = "Camera", tint = RiyaTextSecondary, modifier = Modifier.size(18.dp))
                }
                IconButton(onClick = onTasksClick, modifier = Modifier.size(34.dp)) {
                    Icon(imageVector = Icons.Default.Assignment, contentDescription = "Tasks", tint = RiyaCyan, modifier = Modifier.size(18.dp))
                }
                IconButton(onClick = onAppsClick, modifier = Modifier.size(34.dp)) {
                    Icon(imageVector = Icons.Default.Apps, contentDescription = "Apps", tint = RiyaTextSecondary, modifier = Modifier.size(18.dp))
                }
                IconButton(onClick = onStopAll, modifier = Modifier.size(34.dp)) {
                    Icon(imageVector = Icons.Default.Cancel, contentDescription = "Stop All", tint = RiyaNeonRed, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun StatusBadge(title: String, status: String, color: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(RiyaSurface)
            .border(0.8.dp, RiyaBorderSubtle, RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 3.dp)
    ) {
        Box(
            modifier = Modifier
                .size(5.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$title: ",
            color = RiyaTextMuted,
            fontSize = 9.sp
        )
        Text(
            text = status,
            color = color,
            fontSize = 9.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun AudioWaveGroup(isListening: Boolean, level: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        val heights = if (isListening) {
            listOf(10.dp, (16 + level * 20).dp, (24 + level * 30).dp, (14 + level * 15).dp)
        } else {
            listOf(4.dp, 8.dp, 12.dp, 6.dp)
        }

        heights.forEach { h ->
            Box(
                modifier = Modifier
                    .width(2.5.dp)
                    .height(h)
                    .clip(RoundedCornerShape(1.dp))
                    .background(if (isListening) RiyaCyan else RiyaCyanDim)
            )
        }
    }
}
