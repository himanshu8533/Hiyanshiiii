package com.example.gupshup.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gupshup.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacySettingsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        val privacyItems = listOf(
            SettingsItemData("Last seen and online", "Everyone", R.drawable.more, null),
            SettingsItemData("Profile photo", "Everyone", R.drawable.more, null),
            SettingsItemData("About", "Everyone", R.drawable.more, null),
            SettingsItemData("Status", "My contacts", R.drawable.more, null),
            SettingsItemData("Read receipts", "If turned off, you won't send or receive Read receipts. Read receipts are always sent for group chats.", R.drawable.more, null),
            SettingsItemData("Default message timer", "Off", R.drawable.more, null),
            SettingsItemData("Groups", "Everyone", R.drawable.more, null),
            SettingsItemData("Live location", "None", R.drawable.more, null),
            SettingsItemData("Blocked contacts", "0", R.drawable.more, null),
            SettingsItemData("App lock", "Disabled", R.drawable.more, null)
        )

        LazyColumn(modifier = Modifier.padding(padding)) {
            item {
                Text(
                    "Who can see my personal info",
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF008069),
                    fontSize = 14.sp
                )
            }
            items(privacyItems.take(4)) { item ->
                SettingsListItem(item) {}
            }
            item {
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            }
            item {
                var readReceipts by remember { mutableStateOf(true) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Read receipts", fontSize = 16.sp)
                        Text(
                            "If turned off, you won't send or receive Read receipts. Read receipts are always sent for group chats.",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }
                    Switch(checked = readReceipts, onCheckedChange = { readReceipts = it })
                }
            }
            item {
                Text(
                    "Disappearing messages",
                    modifier = Modifier.padding(16.dp),
                    color = Color(0xFF008069),
                    fontSize = 14.sp
                )
            }
            items(privacyItems.drop(5)) { item ->
                SettingsListItem(item) {}
            }
        }
    }
}
