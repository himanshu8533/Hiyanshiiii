package com.example.gupshup.presentation.callscreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gupshup.R
import com.example.gupshup.presentation.bottomnavigation.BottomNavigation
import com.example.gupshup.presentation.navigation.Routes
import com.example.gupshup.presentation.updatescreen.TopBar

@Composable
fun CallScreen(navHostController: NavHostController, outerPadding: PaddingValues = PaddingValues(0.dp)) {

    val sampleCall= listOf(
        Call(image = R.drawable.dipika_rohila, name = "Dipika Rohila", time = "Yesterday, 8:30 PM", isMissed = true),
        Call(image = R.drawable.himanshu, name = "Himanshu Kumar", time = "Today, 10:00 AM", isMissed = false),
        Call(image = R.drawable.prakhar, name = "Prakhar Goyal", time = "Nov, 8:32 AM", isMissed = true),
        Call(image = R.drawable.dipika_rohila, name = "Dipika Rohila", time = "Yesterday, 8:30 PM", isMissed = true),
        Call(image = R.drawable.himanshu, name = "Himanshu Kumar", time = "Today, 10:00 AM", isMissed = false),
        Call(image = R.drawable.prakhar, name = "Prakhar Goyal", time = "Nov, 8:32 AM", isMissed = true),
        Call(image = R.drawable.dipika_rohila, name = "Dipika Rohila", time = "Yesterday, 8:30 PM", isMissed = true),
        Call(image = R.drawable.himanshu, name = "Himanshu Kumar", time = "Today, 10:00 AM", isMissed = false),
        Call(image = R.drawable.prakhar, name = "Prakhar Goyal", time = "Nov, 8:32 AM", isMissed = true),
    )

    Scaffold(
        topBar = {
            TopBar(
                title = "Calls",
                onSettingsClick = { navHostController.navigate(Routes.SettingsScreen) },
                showCamera = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = colorResource(id = R.color.Royal_Blue),
                modifier = Modifier
                    .padding(bottom = outerPadding.calculateBottomPadding())
                    .size(65.dp),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.phone),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(bottom = outerPadding.calculateBottomPadding())
        ) {
            Spacer(modifier = Modifier.height(5.dp))

            FavouriteSection()

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.Royal_Blue)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Start a new call",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(6.dp))

            Text(
                "Recent Calls",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyColumn {
                items(sampleCall) { data ->
                    CallItemDesign(data)
                }
            }
        }
    }
}

