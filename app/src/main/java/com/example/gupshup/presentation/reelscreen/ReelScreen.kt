package com.example.gupshup.presentation.reelscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gupshup.presentation.bottomnavigation.BottomNavigation
import com.example.gupshup.presentation.navigation.Routes

@Composable
fun ReelScreen(navHostController: NavHostController) {
    Scaffold(
        bottomBar = {
            BottomNavigation(
                navHostController,
                selectedItem = 2,
                unSelectedItem = Color.Gray,
                onClick = { index ->
                    when (index) {
                        0 -> navHostController.navigate(Routes.HomeScreen)
                        1 -> navHostController.navigate(Routes.UpdateScreen)
                        2 -> { /* Already on ReelScreen */ }
                        3 -> navHostController.navigate(Routes.CommunitiesScreen)
                        4 -> navHostController.navigate(Routes.CallsScreen)
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Coming soon...",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
        }
    }
}
