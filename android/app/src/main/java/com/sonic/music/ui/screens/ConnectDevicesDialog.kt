package com.sonic.music.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.sonic.music.data.connect.ConnectDevice
import com.sonic.music.model.Track
import com.sonic.music.ui.theme.*

@Composable
fun ConnectDevicesDialog(
    deviceName: String,
    isConnected: Boolean,
    serverHost: String,
    devices: List<ConnectDevice>,
    isRefreshing: Boolean,
    currentTrack: Track?,
    currentPositionMs: Long,
    onRefresh: () -> Unit,
    onUpdateServerHost: (String) -> Unit,
    onTransferPlayback: (targetDeviceId: String) -> Unit,
    onRemotePlayPause: (targetDeviceId: String, isPlaying: Boolean) -> Unit,
    onRemoteNext: (targetDeviceId: String) -> Unit,
    onRemotePrev: (targetDeviceId: String) -> Unit,
    onDismiss: () -> Unit
) {
    var showHostEditor by remember { mutableStateOf(false) }
    var hostInputText by remember(serverHost) { mutableStateOf(serverHost) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.72f))
                .clickable(onClick = onDismiss),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.92f)
                    .fillMaxHeight(0.85f)
                    .clip(RoundedCornerShape(24.dp))
                    .border(1.dp, SonicBorderSubtle, RoundedCornerShape(24.dp))
                    .clickable(enabled = false) {}, // Prevent clicks inside dialog from dismissing
                colors = CardDefaults.cardColors(containerColor = SonicInk1)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    // Header (Title & Close Button)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Connect",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = SonicTextBase
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(onClick = onRefresh) {
                                Icon(
                                    imageVector = Icons.Default.Refresh,
                                    contentDescription = "Refresh Devices",
                                    tint = if (isRefreshing) SonicMagenta else SonicTextSubdued,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            IconButton(onClick = onDismiss) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Close",
                                    tint = SonicTextSubdued,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Current Device Status Card (Matches Spotify Connect UI)
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(SonicInk2)
                            .border(1.dp, if (isConnected) Color(0xFF1DB954).copy(alpha = 0.35f) else SonicBorderSubtle, RoundedCornerShape(16.dp))
                            .padding(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(46.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        Brush.linearGradient(
                                            colors = listOf(SonicCoral, SonicMagenta)
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Smartphone,
                                    contentDescription = "This Device",
                                    tint = Color.White,
                                    modifier = Modifier.size(26.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "Current Device",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SonicTextBase
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(8.dp)
                                            .clip(CircleShape)
                                            .background(if (isConnected) Color(0xFF1DB954) else Color.Gray)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = if (isConnected) "Active (SONiC Engine)" else "Connecting to local server...",
                                        fontSize = 12.sp,
                                        color = if (isConnected) Color(0xFF1DB954) else SonicTextSubdued,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Discovered Devices Section
                    if (devices.isNotEmpty()) {
                        Text(
                            text = "Select a device to control or transfer",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = SonicTextSubdued
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(devices, key = { it.id }) { dev ->
                                DeviceItemRow(
                                    device = dev,
                                    onTransfer = { onTransferPlayback(dev.id) },
                                    onPlayPause = { onRemotePlayPause(dev.id, dev.isPlaying) },
                                    onNext = { onRemoteNext(dev.id) },
                                    onPrev = { onRemotePrev(dev.id) }
                                )
                            }
                        }
                    } else {
                        // Empty State (Instructions & Wi-Fi Check)
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No other devices found",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = SonicTextBase
                            )

                            Spacer(modifier = Modifier.height(18.dp))

                            ConnectHelpItem(
                                icon = Icons.Default.Wifi,
                                title = "Check your Wi-Fi",
                                description = "Connect the devices you are using to the same Wi-Fi network."
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            ConnectHelpItem(
                                icon = Icons.Default.Computer,
                                title = "Play from another device",
                                description = "Open SONiC on your PC or browser at http://$serverHost:3300"
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            ConnectHelpItem(
                                icon = Icons.Default.Sync,
                                title = "Instant Sync",
                                description = "Playback transfers seamlessly between phone and laptop in 1 tap."
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Server Host Configuration Expandable
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SonicInk2)
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showHostEditor = !showHostEditor },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Server Endpoint",
                                    fontSize = 11.sp,
                                    color = SonicTextSubdued
                                )
                                Text(
                                    text = "http://$serverHost:3300",
                                    fontSize = 13.sp,
                                    color = SonicCyan,
                                    fontWeight = FontWeight.Medium
                                )
                            }

                            Text(
                                text = if (showHostEditor) "Close" else "Change",
                                fontSize = 12.sp,
                                color = SonicMagenta,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        AnimatedVisibility(visible = showHostEditor) {
                            Column(modifier = Modifier.padding(top = 10.dp)) {
                                OutlinedTextField(
                                    value = hostInputText,
                                    onValueChange = { hostInputText = it },
                                    label = { Text("Server IP / Host", fontSize = 12.sp) },
                                    singleLine = true,
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = SonicTextBase,
                                        unfocusedTextColor = SonicTextBase,
                                        focusedBorderColor = SonicMagenta,
                                        unfocusedBorderColor = SonicBorderSubtle
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(
                                    onClick = {
                                        onUpdateServerHost(hostInputText)
                                        showHostEditor = false
                                    },
                                    modifier = Modifier.align(Alignment.End),
                                    colors = ButtonDefaults.buttonColors(containerColor = SonicMagenta),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    Text("Save and Reconnect", fontSize = 12.sp, color = Color.White)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun DeviceItemRow(
    device: ConnectDevice,
    onTransfer: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrev: () -> Unit
) {
    val isDesktop = device.type == "desktop"
    val playingStatus = if (device.currentTrack != null) {
        if (device.isPlaying) "Playing: ${device.currentTrack.title}" else "Paused: ${device.currentTrack.title}"
    } else {
        "Online (Idle)"
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(SonicInk2)
            .border(1.dp, SonicBorderSubtle, RoundedCornerShape(14.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(SonicInk3),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isDesktop) Icons.Default.Computer else Icons.Default.Smartphone,
                        contentDescription = device.name,
                        tint = SonicCyan,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = device.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SonicTextBase
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = playingStatus,
                        fontSize = 12.sp,
                        color = if (device.isPlaying) Color(0xFF1DB954) else SonicTextSubdued,
                        maxLines = 1
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = onTransfer,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SonicMagenta
                    ),
                    shape = RoundedCornerShape(20.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Transfer",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // Remote Control Bar for this Device
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(SonicInk3)
                    .padding(horizontal = 8.dp, vertical = 2.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPrev) {
                    Icon(
                        imageVector = Icons.Default.SkipPrevious,
                        contentDescription = "Remote Previous",
                        tint = SonicTextBase,
                        modifier = Modifier.size(20.dp)
                    )
                }
                IconButton(onClick = onPlayPause) {
                    Icon(
                        imageVector = if (device.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = "Remote Play/Pause",
                        tint = SonicTextBase,
                        modifier = Modifier.size(22.dp)
                    )
                }
                IconButton(onClick = onNext) {
                    Icon(
                        imageVector = Icons.Default.SkipNext,
                        contentDescription = "Remote Next",
                        tint = SonicTextBase,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ConnectHelpItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = SonicTextSubdued,
            modifier = Modifier
                .size(22.dp)
                .padding(top = 2.dp)
        )
        Spacer(modifier = Modifier.width(14.dp))
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = SonicTextBase
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontSize = 12.sp,
                color = SonicTextSubdued,
                lineHeight = 16.sp
            )
        }
    }
}
