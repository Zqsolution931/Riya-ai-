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

data class PlatformCardInfo(
    val title: String,
    val subtitle: String,
    val status: String,
    val icon: ImageVector,
    val isConnected: Boolean,
    val portInfo: String? = null
)

@Composable
fun RiyaPlatformCards(
    modifier: Modifier = Modifier
) {
    val platforms = listOf(
        PlatformCardInfo("Android App", "Native App • Full Access", "Connected", Icons.Default.Android, true),
        PlatformCardInfo("WebView UI", "Browser Based UI", "Ready", Icons.Default.Language, true),
        PlatformCardInfo("Termux Backend", "Python • FastAPI", "Ports 8000 / 8001", Icons.Default.Terminal, true, "127.0.0.1:8001"),
        PlatformCardInfo("Windows Companion", "Desktop Application", "Paired (Standby)", Icons.Default.DesktopWindows, false),
        PlatformCardInfo("Web Dashboard", "Access from Anywhere", "Active", Icons.Default.Dashboard, true)
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        platforms.forEach { platform ->
            PlatformCardItem(
                info = platform,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun PlatformCardItem(
    info: PlatformCardInfo,
    modifier: Modifier = Modifier
) {
    val borderColor = if (info.isConnected) RiyaCyan.copy(alpha = 0.5f) else RiyaBorderSubtle
    val statusColor = if (info.isConnected) RiyaNeonGreen else RiyaAmber

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(RiyaSurfaceElevated)
            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
            .padding(vertical = 8.dp, horizontal = 6.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = info.icon,
                contentDescription = info.title,
                tint = if (info.isConnected) RiyaCyan else RiyaTextSecondary,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = info.title,
                color = RiyaTextPrimary,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = info.subtitle,
                color = RiyaTextMuted,
                fontSize = 9.sp,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "● ${info.status}",
                color = statusColor,
                fontSize = 9.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1
            )
        }
    }
}
