package com.example.gupshup.presentation.updatescreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gupshup.R
import com.example.gupshup.presentation.bottomnavigation.BottomNavigation


@Composable
@Preview(showSystemUi = true)
fun UpdateScreen() {

    val scrollState = rememberScrollState()

    val sampleStatus = listOf(
        StatusData(image = R.drawable.himanshu, name = "Himanshu Kumar", time = "10 min ago"),
        StatusData(image = R.drawable.himanshu, name = "Himanshu Kumar", time = "10 min ago"),
        StatusData(image = R.drawable.himanshu, name = "Himanshu Kumar", time = "10 min ago"),
        StatusData(image = R.drawable.himanshu, name = "Himanshu Kumar", time = "10 min ago")

    )

    val sampleChannel = listOf(

        Channel(
            image = R.drawable.whatsapp_icon,
            name = "Neet Roots",
            description = "Latest news in tech"
        ),
        Channel(
            image = R.drawable.whatsapp_icon,
            name = "Food Lover",
            description = "Latest news in tech"
        ),
        Channel(
            image = R.drawable.whatsapp_icon,
            name = "Image Recognition",
            description = "Latest news in tech"
        ),
        Channel(
            image = R.drawable.whatsapp_icon,
            name = "Change Background",
            description = "Latest news in tech"
        ),
        Channel(
            image = R.drawable.whatsapp_icon,
            name = "Rotation",
            description = "Latest news in tech"
        ),

        )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                containerColor = colorResource(id = R.color.Royal_Blue),
                modifier = Modifier.size(65.dp),
                contentColor = Color.White
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.camera222),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )

            }
        },
        bottomBar = {

            BottomNavigation()

        },
        topBar = {

            TopBar()

        }

    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .verticalScroll(scrollState)
        )
        {
            Text(
                text = "Status",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            MyStatus()

            sampleStatus.forEach { data ->

                StatusItem(statusData = data)

            }

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                color = Color.Gray

            )

            Text(
                text = "Channels",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {

                Text(text = "Stay updated on topics that matter to you. Find channels to follow below.")

                Spacer(modifier = Modifier.height(32.dp))
                Text(text = "Find channels to follow")
            }

            Spacer(modifier = Modifier.height(16.dp))

            sampleChannel.forEach {

                ChannelItemDesign(channel = it)

            }

        }

    }

}