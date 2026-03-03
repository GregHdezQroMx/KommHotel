package com.kommhotel.app.features.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminDashboard() {
    Row(modifier = Modifier.fillMaxSize()) {
        // Sidebar
        AdminSidebar(modifier = Modifier.width(250.dp).fillMaxHeight())

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                .padding(24.dp)
        ) {
            Text(
                text = "Hotel Manager Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                StatCard("Occupancy", "78%", Icons.Default.Bed, Modifier.weight(1f))
                StatCard("Today's Bookings", "14", Icons.Default.Event, Modifier.weight(1f))
                StatCard("Pending Feedback", "5", Icons.Default.Feedback, Modifier.weight(1f), Color.Red)
                StatCard("Monthly Revenue", "$54,200", Icons.Default.MonetizationOn, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Recent Activity & Notifications
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                RecentFeedbackList(Modifier.weight(1.5f))
                QuickActions(Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun AdminSidebar(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("KommHotel Admin", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(32.dp))
            
            SidebarItem("Dashboard", Icons.Default.Dashboard, selected = true)
            SidebarItem("Rooms Inventory", Icons.Default.MeetingRoom)
            SidebarItem("Reservations", Icons.Default.CalendarMonth)
            SidebarItem("Guest Feedback", Icons.Default.Reviews)
            SidebarItem("Branding & UI", Icons.Default.Palette)
            
            Spacer(modifier = Modifier.weight(1f))
            
            SidebarItem("Settings", Icons.Default.Settings)
            SidebarItem("Logout", Icons.Default.Logout)
        }
    }
}

@Composable
fun SidebarItem(label: String, icon: ImageVector, selected: Boolean = false) {
    NavigationDrawerItem(
        label = { Text(label) },
        selected = selected,
        onClick = {},
        icon = { Icon(icon, contentDescription = null) },
        modifier = Modifier.padding(vertical = 4.dp),
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent
        )
    )
}

@Composable
fun StatCard(title: String, value: String, icon: ImageVector, modifier: Modifier = Modifier, color: Color = MaterialTheme.colorScheme.primary) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun RecentFeedbackList(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Recent Guest Experience", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            
            val dummyFeedback = listOf(
                "Great service at the lobby!",
                "Room 304 AC is a bit noisy.",
                "Loved the breakfast options."
            )
            
            dummyFeedback.forEach { feedback ->
                ListItem(
                    headlineContent = { Text(feedback) },
                    leadingContent = { Icon(Icons.Default.Person, contentDescription = null) },
                    overlineContent = { Text("Guest Feedback") }
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun QuickActions(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Quick Actions", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Update Room Rates")
            }
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedButton(onClick = {}, modifier = Modifier.fillMaxWidth()) {
                Text("Generate Monthly Report")
            }
        }
    }
}
