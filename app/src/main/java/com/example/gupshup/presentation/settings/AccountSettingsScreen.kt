package com.example.gupshup.presentation.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gupshup.R
import com.example.gupshup.presentation.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSettingsScreen(navController: NavHostController) {
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
                    item.route?.let { navController.navigate(it) }
                }
            }
        }
    }
}
