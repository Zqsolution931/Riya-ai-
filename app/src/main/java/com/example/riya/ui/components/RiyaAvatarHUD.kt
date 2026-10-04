package com.example.riya.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.riya.data.model.AvatarState
import com.example.riya.data.model.RiyaOutfit
import com.example.ui.theme.*

@Composable
fun RiyaAvatarHUD(
    avatarState: AvatarState,
    outfit: RiyaOutfit,
    greetingText: String,
    micLevel: Float = 0f,
    modifier: Modifier = Modifier
) {
    // Infinite rotation for mechanical HUD rings
    val infiniteTransition = rememberInfiniteTransition(label = "hud_rings")
    val angle1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring1"
    )
    val angle2 by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ring2"
    )
    val pulseGlow by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "glow"
    )

    val auraColor = Color(outfit.auraHex)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        // Left Speech greeting quote bubble (as in reference image)
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(end = 12.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(RiyaSurfaceElevated.copy(alpha = 0.9f), RiyaSurface.copy(alpha = 0.6f))
                    ),
                    shape = RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp, topEnd = 4.dp, bottomEnd = 16.dp)
                )
                .border(1.dp, RiyaCyan.copy(alpha = 0.35f), RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp, topEnd = 4.dp, bottomEnd = 16.dp))
                .padding(14.dp)
        ) {
            Column {
                Text(
                    text = greetingText,
                    color = RiyaTextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Aura: ${outfit.title} • ${outfit.subtitle}",
                    color = auraColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // Center Dominant Avatar with circular holographic HUD rings
        Box(
            modifier = Modifier
                .size(190.dp),
            contentAlignment = Alignment.Center
        ) {
            // Background glow canvas
            Canvas(modifier = Modifier.fillMaxSize()) {
                // Outer dashed cyan ring
                drawCircle(
                    color = auraColor.copy(alpha = 0.25f * pulseGlow),
                    radius = size.minDimension / 2 - 4.dp.toPx(),
                    style = Stroke(
                        width = 2.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(25f, 15f), angle1)
                    )
                )

                // Middle golden mechanical ring
                drawCircle(
                    color = RiyaGold.copy(alpha = 0.35f),
                    radius = size.minDimension / 2 - 14.dp.toPx(),
                    style = Stroke(
                        width = 1.5.dp.toPx(),
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(12f, 20f), angle2)
                    )
                )

                // Inner luminous cyan ring
                drawCircle(
                    color = auraColor.copy(alpha = 0.7f),
                    radius = size.minDimension / 2 - 24.dp.toPx(),
                    style = Stroke(width = 2.dp.toPx())
                )
            }

            // Avatar portrait image
            Box(
                modifier = Modifier
                    .size(136.dp)
                    .clip(CircleShape)
                    .border(2.5.dp, Brush.sweepGradient(listOf(auraColor, RiyaCyan, RiyaGold, auraColor)), CircleShape)
                    .background(RiyaSurfaceElevated)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.riya_avatar),
                    contentDescription = "RIYA Holographic AI Avatar",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // Live status pill at top right of avatar ring
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 10.dp, y = 14.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(RiyaSurfaceElevated, RiyaSurface)
                        ),
                        RoundedCornerShape(12.dp)
                    )
                    .border(1.dp, Color(avatarState.hexColor).copy(alpha = 0.8f), RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.GraphicEq,
                        contentDescription = null,
                        tint = Color(avatarState.hexColor),
                        modifier = Modifier.size(12.dp)
                    )
                    Text(
                        text = avatarState.label,
                        color = Color(avatarState.hexColor),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
