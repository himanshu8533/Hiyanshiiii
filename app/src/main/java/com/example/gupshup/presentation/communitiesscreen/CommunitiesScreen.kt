package com.example.gupshup.presentation.communitiesscreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gupshup.R
import com.example.gupshup.presentation.bottomnavigation.BottomNavigation
import com.example.gupshup.presentation.navigation.Routes
import com.example.gupshup.presentation.updatescreen.TopBar

@Composable
fun CommunitiesScreen(navHostController: NavHostController, outerPadding: PaddingValues = PaddingValues(0.dp)) {

    val sampleCommunities = listOf(
        Communities(image = R.drawable.dipika_rohila, name = "Dipika Rohila", memberCount = "256 members"),
        Communities(image = R.drawable.himanshu, name = "Himanshu Kumar", memberCount = "200 members"),
        Communities(image = R.drawable.prakhar, name = "Prakhar Goyal", memberCount = "250 members"),
        Communities(image = R.drawable.dipika_rohila, name = "Dipika Rohila", memberCount = "257 members"),
        Communities(image = R.drawable.himanshu, name = "Himanshu Kumar", memberCount = "243 members"),
        Communities(image = R.drawable.prakhar, name = "Prakhar Goyal", memberCount = "272 members"),
        Communities(image = R.drawable.dipika_rohila, name = "Dipika Rohila", memberCount = "208 members"),
        Communities(image = R.drawable.himanshu, name = "Himanshu Kumar", memberCount = "250 members"),
        Communities(image = R.drawable.prakhar, name = "Prakhar Goyal", memberCount = "298 members")


    )


    Scaffold(
        topBar = {
            TopBar(
                title = "Communities",
                onSettingsClick = { navHostController.navigate(Routes.SettingsScreen) },
                showCamera = false
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier
            .padding(paddingValues)
            .padding(bottom = outerPadding.calculateBottomPadding())
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.Royal_Blue)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Start a new Community", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Your Communities",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            LazyColumn {
                items(sampleCommunities) {
                    CommunityItemDesign(communities = it)
                }
            }
        }
    }
}
