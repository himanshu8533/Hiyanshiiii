package com.example.gupshup.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.gupshup.R
import com.example.gupshup.presentation.navigation.Routes
import com.example.gupshup.presentation.viewmodels.PhoneAuthViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettingsScreen(navController: NavHostController, viewModel: PhoneAuthViewModel = hiltViewModel()) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Account") },
            text = { Text("Are you sure you want to delete your account? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteAccount(userId) { success ->
                            if (success) {
                                navController.navigate(Routes.WelcomeScreen) {
                                    popUpTo(0)
                                }
                            }
                        }
                        showDeleteDialog = false
                    }
                ) {
                    Text("Delete", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Account") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        val accountItems = listOf(
            SettingsItemData("Security notifications", "", R.drawable.more, null),
            SettingsItemData("Passkeys", "", R.drawable.more, null),
            SettingsItemData("Email address", "", R.drawable.more, Routes.EmailAddressScreen),
            SettingsItemData("Two-step verification", "", R.drawable.more, null),
            SettingsItemData("Change number", "", R.drawable.more, Routes.ChangePhoneNumberScreen),
            SettingsItemData("Request account info", "", R.drawable.more, null),
            SettingsItemData("Ad preferences for status & channels", "", R.drawable.more, null),
            SettingsItemData("Add account", "", R.drawable.more, null),
            SettingsItemData("Delete account", "", R.drawable.more, null)
        )

        LazyColumn(modifier = Modifier.padding(padding)) {
            items(accountItems) { item ->
                SettingsListItem(item) {
                    if (item.title == "Delete account") {
                        showDeleteDialog = true
                    } else {
                        item.route?.let { navController.navigate(it) }
                    }
                }
            }
        }
    }
}
