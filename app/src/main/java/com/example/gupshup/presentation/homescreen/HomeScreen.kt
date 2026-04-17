package com.example.gupshup.presentation.homescreen

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.gupshup.R
import com.example.gupshup.presentation.bottomnavigation.BottomNavigation
import com.example.gupshup.presentation.chat_box.ChatListBox
import com.example.gupshup.presentation.chat_box.ChatListModel
import com.example.gupshup.presentation.navigation.Routes
import com.example.gupshup.presentation.updatescreen.TopBar
import com.example.gupshup.presentation.viewmodels.BaseViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navHostController: NavHostController, homeBaseViewModel: BaseViewModel, outerPadding: PaddingValues = PaddingValues(0.dp)) {
    var showPopup by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                Toast.makeText(context, "Image selected from gallery!", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                Toast.makeText(context, "Photo captured!", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                cameraLauncher.launch(null)
            } else {
                Toast.makeText(context, "Camera Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    )

    val chatData by homeBaseViewModel.chatList.collectAsState()

    val userId = FirebaseAuth.getInstance().currentUser?.uid

    if (userId != null) {
        LaunchedEffect(Unit) {
            homeBaseViewModel.getChatForUser(userId) { _ -> }
        }
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "GupShup",
                titleColor = colorResource(id = R.color.Royal_Blue),
                onSettingsClick = { navHostController.navigate(Routes.SettingsScreen) },
                onCameraClick = {
                    galleryLauncher.launch("image/*")
                },
                menuOptions = { onDismiss ->
                    DropdownMenuItem(
                        text = { Text("New group") },
                        onClick = { onDismiss() })

                    DropdownMenuItem(
                        text = { Text("New community") },
                        onClick = { onDismiss() })

                    DropdownMenuItem(
                        text = { Text("Broadcast lists") },
                        onClick = { onDismiss() })

                    DropdownMenuItem(
                        text = { Text("Linked devices") },
                        onClick = { onDismiss() })

                    DropdownMenuItem(
                        text = { Text("Starred") },
                        onClick = { onDismiss() })

                    DropdownMenuItem(
                        text = { Text("Payments") },
                        onClick = { onDismiss() })

                    DropdownMenuItem(
                        text = { Text("Read all") },
                        onClick = { onDismiss() })

                    DropdownMenuItem(text = { Text("Settings") }, onClick = {
                        onDismiss()
                        navHostController.navigate(Routes.SettingsScreen)
                    })
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showPopup = true
                },
                containerColor = colorResource(id = R.color.Royal_Blue),
                contentColor = Color.White,
                modifier = Modifier
                    .padding(bottom = outerPadding.calculateBottomPadding())
                    .size(65.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_chat_icon),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = Color.White
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(bottom = outerPadding.calculateBottomPadding())
                .background(color = Color.White)
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            if (showPopup) {
                AddUserPopup(
                    onDismiss = {
                        showPopup = false
                    },
                    onUserAdd = { newUser ->
                        homeBaseViewModel.addChat(newUser)
                    },
                    baseViewModel = homeBaseViewModel
                )
            }
            val dummyGupShupChat = ChatListModel(
                name = "GupShup",
                phoneNumber = "Official",
                message = "Thank you so much! For the appreciation.",
                time = "10:05 AM"
            )

            LazyColumn {
                item {
                    ChatListBox(
                        chatListModel = dummyGupShupChat,
                        onClick = {
                            navHostController.navigate(
                                Routes.ChatScreen(
                                    phoneNumber = dummyGupShupChat.phoneNumber ?: "Official"
                                )
                            )
                        },
                        baseViewModel = homeBaseViewModel
                    )
                }
                items(chatData) { chat ->
                    ChatListBox(
                        chatListModel = chat,
                        onClick = {
                            navHostController.navigate(
                                Routes.ChatScreen(
                                    phoneNumber = chat.phoneNumber ?: "Ok"
                                )
                            )
                        },
                        baseViewModel = homeBaseViewModel
                    )
                }
            }
        }
    }
}


@Composable
fun AddUserPopup(
    onDismiss: () -> Unit,

    onUserAdd: (ChatListModel) -> Unit,

    baseViewModel: BaseViewModel
) {
    var phoneNumber by remember { mutableStateOf("") }

    var isSearching by remember { mutableStateOf(false) }

    var userFound by remember { mutableStateOf<ChatListModel?>(null) }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        TextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text(text = "Enter Phone Number") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            )
        )

        Row {
            Button(
                onClick = {
                    isSearching = true
                    baseViewModel.searchUserByPhoneNumber(phoneNumber) { user ->
                        isSearching = false

                        if (user != null) {
                            userFound = user
                        } else {
                            userFound = null
                        }
                    }
                }, modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    colorResource(R.color.Royal_Blue)
                )
            ) {
                Text("Search")
            }
            Spacer(modifier = Modifier.height(6.dp))

            Button(
                onClick = onDismiss,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    colorResource(R.color.Royal_Blue)
                )
            ) {
                Text("Cancel")
            }
        }

        if (isSearching) {

            Text(text = "Searching...", color = Color.Gray)
        }

        userFound?.let {
            Column {
                Text("User Found ${it.name}")

                Button(
                    onClick = {
                        onUserAdd(it)

                        onDismiss()
                    }, colors = ButtonDefaults.buttonColors(
                        colorResource(R.color.Royal_Blue)
                    )
                ) {

                    Text("Add to Chat")
                }
            }
        }
            ?: run {
                if (!isSearching) {
                    Text("No User Found with this phone number", color = Color.Gray)
                }
            }
    }
}

