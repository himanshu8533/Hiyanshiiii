package com.example.gupshup.presentation.updatescreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gupshup.R

@Composable
fun TopBar(
    title: String,
    titleColor: Color = Color.Black,
    onSettingsClick: () -> Unit,
    showCamera: Boolean = true,
    showSearch: Boolean = true,
    showMenu: Boolean = true,
    onCameraClick: () -> Unit = {},
    onSearchQueryChange: (String) -> Unit = {},
    menuOptions: @Composable (onDismiss: () -> Unit) -> Unit = { onDismiss ->
        DropdownMenuItem(
            text = { Text("Settings") },
            onClick = {
                onDismiss()
                onSettingsClick()
            }
        )
    }
) {
    var isSearching by remember { mutableStateOf(false) }
    var search by remember { mutableStateOf("") }
    var showMenuState by remember { mutableStateOf(false) }

    Surface(
        color = Color.White,
        modifier = Modifier.statusBarsPadding()
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isSearching) {
                    IconButton(onClick = {
                        isSearching = false
                        search = ""
                        onSearchQueryChange("")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.cross),
                            contentDescription = "Back",
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    TextField(
                        value = search,
                        onValueChange = {
                            search = it
                            onSearchQueryChange(it)
                        },
                        placeholder = { Text(text = "Search...", color = Color.Gray) },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                } else {
                    Text(
                        text = title,
                        fontSize = 24.sp,
                        color = titleColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    if (showCamera) {
                        IconButton(onClick = onCameraClick) {
                            Icon(
                                painter = painterResource(id = R.drawable.camera),
                                contentDescription = "Camera",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    if (showSearch) {
                        IconButton(onClick = { isSearching = true }) {
                            Icon(
                                painter = painterResource(id = R.drawable.search),
                                contentDescription = "Search",
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    if (showMenu) {
                        Box {
                            IconButton(onClick = { showMenuState = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.more),
                                    contentDescription = "More",
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            DropdownMenu(
                                expanded = showMenuState,
                                onDismissRequest = { showMenuState = false },
                                modifier = Modifier.background(Color.White)
                            ) {
                                menuOptions { showMenuState = false }
                            }
                        }
                    }
                }
            }
            HorizontalDivider(thickness = 0.5.dp, color = Color.LightGray)
        }
    }
}
