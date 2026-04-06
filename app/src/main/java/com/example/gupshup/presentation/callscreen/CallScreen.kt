package com.example.gupshup.presentation.callscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gupshup.R
import com.example.gupshup.presentation.bottomnavigation.BottomNavigation
import com.example.gupshup.presentation.navigation.Routes

@Composable
fun CallScreen(navHostController: NavHostController) {

    val sampleCall= listOf(
        Call(image = R.drawable.dipika_rohila, name = "Dipika Rohila", time = "Yesterday, 8:30 PM", isMissed = true),
        Call(image = R.drawable.himanshu, name = "Himanshu Kumar", time = "Today, 10:00 AM", isMissed = false),
        Call(image = R.drawable.prakhar, name = "Prakhar Goyal", time = "Nov, 8:32 AM", isMissed = true),
        Call(image = R.drawable.dipika_rohila, name = "Dipika Rohila", time = "Yesterday, 8:30 PM", isMissed = true),
        Call(image = R.drawable.himanshu, name = "Himanshu Kumar", time = "Today, 10:00 AM", isMissed = false),
        Call(image = R.drawable.prakhar, name = "Prakhar Goyal", time = "Nov, 8:32 AM", isMissed = true),
    )

    var isSearching by remember {
        mutableStateOf(false)
    }

    var search by remember {
        mutableStateOf("")
    }

    var showMenu by remember {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
        HorizontalDivider()

        Box(modifier = Modifier.fillMaxWidth()) {

            Column(modifier = Modifier.padding(top = 20.dp)) {

                Row {

                    if (isSearching) {

                        TextField(
                            value = search, onValueChange = {
                                search = it
                            }, placeholder = {
                                Text(text = "Search")
                            }, colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            ), modifier = Modifier.padding(start = 12.dp), singleLine = true
                        )
                    } else {

                        Text(
                            text = "Call",
                            fontSize = 28.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 12.dp, top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    if (isSearching) {

                        IconButton(onClick = {
                            isSearching = false

                            search = ""
                        }) {

                            Icon(
                                painter = painterResource(id = R.drawable.cross2),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    } else {

                        Spacer(modifier = Modifier.height(24.dp))
                        IconButton(onClick = { isSearching = true }) {

                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        IconButton(onClick = { showMenu = true }) {

                            Icon(
                                painter = painterResource(id = R.drawable.more),
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            DropdownMenu(
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false }) {
                                DropdownMenuItem(
                                    text = { Text(text = "Settings") },
                                    onClick = { showMenu = false })
                            }
                        }
                    }
                }

                HorizontalDivider()
            }
        }
    },
        bottomBar = {
            BottomNavigation(
                navHostController,
                selectedItem = 0,
                unSelectedItem = Color.Gray,
                onClick = { index ->

                    when (index) {
                        0 -> {
                            navHostController.navigate(Routes.HomeScreen)
                        }

                        1 -> {
                            navHostController.navigate(Routes.UpdateScreen)
                        }

                        2 -> {
                            navHostController.navigate(Routes.ReelScreen)
                        }

                        3 -> {
                            navHostController.navigate(Routes.CommunitiesScreen)
                        }

                        4 -> {
                            navHostController.navigate(Routes.CallsScreen)
                        }

                    }
                })
        },
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
        },)
    {
        Column(modifier = Modifier.padding(it)) {

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
                items(sampleCall){ data->
                    CallItemDesign(data)

                }
            }

        }
    }
}
