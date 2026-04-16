package com.example.gupshup.presentation.settings

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.gupshup.R
import com.example.gupshup.presentation.navigation.Routes
import com.example.gupshup.presentation.viewmodels.BaseViewModel
import com.example.gupshup.presentation.viewmodels.PhoneAuthViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController, viewModel: BaseViewModel = hiltViewModel()) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    var profileName by remember { mutableStateOf("User Name") }
    var profileStatus by remember { mutableStateOf("Hey there! I am using GupShup.") }
    var profileImageBase64 by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(currentUser?.uid) {
        currentUser?.uid?.let { uid ->
            FirebaseDatabase.getInstance().getReference("users").child(uid).get()
                .addOnSuccessListener { snapshot ->
                    profileName = snapshot.child("name").value as? String ?: "User Name"
                    profileStatus = snapshot.child("status").value as? String ?: "Hey there! I am using GupShup."
                    profileImageBase64 = snapshot.child("profileImage").value as? String
                }
        }
    }

    val context = LocalContext.current
    var showLanguageDialog by remember { mutableStateOf(false) }
    val languages = listOf("English", "Hindi", "Spanish", "French", "German")
    var selectedLanguage by remember { mutableStateOf("English") }

    if (showLanguageDialog) {
        AlertDialog(
            onDismissRequest = { showLanguageDialog = false },
            title = { Text("Select Language") },
            text = {
                Column {
                    languages.forEach { language ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    selectedLanguage = language
                                    showLanguageDialog = false
                                }
                                .padding(vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (language == selectedLanguage),
                                onClick = {
                                    selectedLanguage = language
                                    showLanguageDialog = false
                                }
                            )
                            Text(text = language, modifier = Modifier.padding(start = 8.dp))
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showLanguageDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Search Settings */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search Settings")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            item {
                ProfileSection(profileName, profileStatus, profileImageBase64, viewModel) {
                    navController.navigate(Routes.ProfileScreen)
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)
            }

            val settingsItems = listOf(
                SettingsItemData("Account", "Security notifications, change number", R.drawable.more, Routes.AccountSettingsScreen),
                SettingsItemData("Privacy", "Block contacts, disappearing messages", R.drawable.more, Routes.PrivacySettingsScreen),
                SettingsItemData("Avatar", "Create, edit, profile photo", R.drawable.more, null),
                SettingsItemData("Lists", "Manage people and groups", R.drawable.more, null),
                SettingsItemData("Chats", "Theme, wallpapers, chat history", R.drawable.more, Routes.ChatThemeScreen),
                SettingsItemData("Notifications", "Message, group & call tones", R.drawable.more, null),
                SettingsItemData("Storage and data", "Network usage, auto-download", R.drawable.more, null),
                SettingsItemData("App language", selectedLanguage, R.drawable.more, null),
                SettingsItemData("Help", "Help center, contact us, privacy policy", R.drawable.more, null),
                SettingsItemData("Invite a friend", "", R.drawable.more, null)
            )

            items(settingsItems) { item ->
                SettingsListItem(item) {
                    when (item.title) {
                        "Invite a friend" -> {
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, "Let's chat on GupShup! It's a fast, simple, and secure app we can use to message and call each other for free. Get it at https://example.com/download")
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        }
                        "App language" -> {
                            showLanguageDialog = true
                        }
                        else -> {
                            item.route?.let { navController.navigate(it) }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileSection(name: String, status: String, imageBase64: String?, viewModel: BaseViewModel, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val bitmap = imageBase64?.let { viewModel.base64ToBitmap(it) }
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.user_placeholder),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = status, fontSize = 14.sp, color = Color.Gray)
        }
        Icon(
            painter = painterResource(id = R.drawable.more), // Using 'more' as a placeholder for QR icon
            contentDescription = "QR Code",
            modifier = Modifier.size(24.dp),
            tint = Color(0xFF008069)
        )
    }
}


@Composable
fun SettingsListItem(item: SettingsItemData, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = item.icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(24.dp))
        Column {
            Text(text = item.title, fontSize = 16.sp)
            if (item.subtitle.isNotEmpty()) {
                Text(text = item.subtitle, fontSize = 13.sp, color = Color.Gray)
            }
        }
    }
}

data class SettingsItemData(
    val title: String,
    val subtitle: String,
    val icon: Int,
    val route: Routes?
)
