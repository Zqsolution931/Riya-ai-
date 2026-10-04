package com.example.riya.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.riya.data.model.RiyaOutfit
import com.example.riya.data.model.WorkspaceTab
import com.example.ui.theme.*

@Composable
fun RiyaSidebar(
    currentTab: WorkspaceTab,
    currentOutfit: RiyaOutfit,
    onTabSelected: (WorkspaceTab) -> Unit,
    onOutfitSelected: (RiyaOutfit) -> Unit,
    onQuickAction: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxHeight()
            .widthIn(min = 190.dp, max = 220.dp)
            .background(RiyaSurface)
            .border(1.dp, RiyaBorderCyan, RoundedCornerShape(0.dp))
            .verticalScroll(scrollState)
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Nav items
        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            NavItem(WorkspaceTab.HOME, Icons.Default.Home, currentTab == WorkspaceTab.HOME) { onTabSelected(WorkspaceTab.HOME) }
            NavItem(WorkspaceTab.CHAT, Icons.Default.ChatBubble, currentTab == WorkspaceTab.CHAT) { onTabSelected(WorkspaceTab.CHAT) }
            NavItem(WorkspaceTab.TASKS, Icons.Default.CheckCircle, currentTab == WorkspaceTab.TASKS) { onTabSelected(WorkspaceTab.TASKS) }
            NavItem(WorkspaceTab.AGENTS, Icons.Default.Groups, currentTab == WorkspaceTab.AGENTS) { onTabSelected(WorkspaceTab.AGENTS) }
            NavItem(WorkspaceTab.TOOLS, Icons.Default.Build, currentTab == WorkspaceTab.TOOLS) { onTabSelected(WorkspaceTab.TOOLS) }
            NavItem(WorkspaceTab.MEMORY, Icons.Default.Psychology, currentTab == WorkspaceTab.MEMORY) { onTabSelected(WorkspaceTab.MEMORY) }
            NavItem(WorkspaceTab.SETTINGS, Icons.Default.Settings, currentTab == WorkspaceTab.SETTINGS) { onTabSelected(WorkspaceTab.SETTINGS) }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Quick Access Section
        Text(
            text = "QUICK ACCESS",
            color = RiyaTextMuted,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
            QuickActionItem("Open YouTube", Icons.Default.PlayArrow) { onQuickAction("Open YouTube") }
            QuickActionItem("Open WhatsApp", Icons.Default.Send) { onQuickAction("Open WhatsApp") }
            QuickActionItem("Create Reminder", Icons.Default.Alarm) { onQuickAction("Create Reminder") }
            QuickActionItem("System Status", Icons.Default.Speed) { onQuickAction("System Status") }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // RIYA Outfit Selector
        Text(
            text = "RIYA OUTFIT",
            color = RiyaCyan,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        // 2x3 Grid for Outfits
        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            val outfits = RiyaOutfit.values()
            for (i in outfits.indices step 3) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (j in 0 until 3) {
                        if (i + j < outfits.size) {
                            val outfit = outfits[i + j]
                            OutfitMiniCard(
                                outfit = outfit,
                                isSelected = currentOutfit == outfit,
                                onSelect = { onOutfitSelected(outfit) },
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Footer Version badge
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(6.dp))
                .background(RiyaSurfaceElevated)
                .border(1.dp, RiyaBorderSubtle, RoundedCornerShape(6.dp))
                .padding(vertical = 6.dp, horizontal = 8.dp)
        ) {
            Column {
                Text(
                    text = "RIYA OutfitTHINK",
                    color = RiyaTextPrimary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "v1.0.0 (Native Core)",
                    color = RiyaCyan,
                    fontSize = 9.sp
                )
            }
        }
    }
}

@Composable
fun NavItem(
    tab: WorkspaceTab,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val bgColor = if (isSelected) RiyaSurfaceElevated else Color.Transparent
    val borderColor = if (isSelected) RiyaCyan else Color.Transparent
    val textColor = if (isSelected) RiyaCyan else RiyaTextPrimary

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(6.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(6.dp))
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .testTag("nav_item_${tab.name.lowercase()}"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = tab.title,
            tint = textColor,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = tab.title,
            color = textColor,
            fontSize = 11.5.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
        )
    }
}

@Composable
fun QuickActionItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .clickable { onClick() }
            .padding(horizontal = 6.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = RiyaTextSecondary,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = title,
            color = RiyaTextSecondary,
            fontSize = 10.5.sp
        )
    }
}

@Composable
fun OutfitMiniCard(
    outfit: RiyaOutfit,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    val auraColor = Color(outfit.auraHex)
    val borderColor = if (isSelected) auraColor else RiyaBorderSubtle

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (isSelected) RiyaSurfaceElevated else RiyaSurface)
            .border(1.dp, borderColor, RoundedCornerShape(6.dp))
            .clickable { onSelect() }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .border(1.5.dp, auraColor, CircleShape)
        ) {
            Image(
                painter = painterResource(id = R.drawable.riya_avatar),
                contentDescription = outfit.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = outfit.title,
            color = if (isSelected) auraColor else RiyaTextPrimary,
            fontSize = 8.5.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
    }
}
