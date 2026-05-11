package com.example.gupshup.presentation.chatscreen

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.gupshup.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class DummyMessage(
    val text: String,
    val isSentByMe: Boolean,
    val time: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavHostController, phoneNumber: String) {
    var messageText by remember { mutableStateOf("") }
    val context = LocalContext.current
    var showImageSourceDialog by remember { mutableStateOf(false) }
    var showVideoCallDialog by remember { mutableStateOf(false) }
    var showAudioCallDialog by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val initialMessages = remember(phoneNumber) {
        when (phoneNumber) {
            "someone" -> listOf(
                DummyMessage("Hii", false, "10:00 AM"),

            )
            "9876543210" -> listOf(
                DummyMessage("Hey Alice!", true, "09:00 AM"),
                DummyMessage("Hi! Are we meeting today?", false, "09:15 AM"),
                DummyMessage("Yes, at 5 PM.", true, "09:20 AM"),
                DummyMessage("See you tomorrow!", false, "09:45 AM")
            )
            "Group" -> listOf(
                DummyMessage("Project update?", false, "Yesterday"),
                DummyMessage("Almost done with the UI.", true, "Yesterday"),
                DummyMessage("Great. Send the report.", false, "Yesterday"),
                DummyMessage("The report is ready.", true, "08:00 AM")
            )
            "1234567890" -> listOf(
                DummyMessage("Yo Bob!", true, "Monday"),
                DummyMessage("Hey man, what's up?", false, "Monday"),
                DummyMessage("Not much, just working.", true, "Monday"),
                DummyMessage("Call me when you're free.", false, "Monday")
            )
            "5551234567" -> listOf(
                DummyMessage("Hey Charlie, did you see the link?", true, "12:00 PM"),
                DummyMessage("Just checking it now.", false, "12:15 PM"),
                DummyMessage("Check out this link!", false, "12:30 PM")
            )
            else -> listOf(
                DummyMessage("Hello!", false, "Recently"),
                DummyMessage("This is a new chat.", true, "Just now")
            )
        }
    }

    val messages = remember(phoneNumber) {
        mutableStateListOf<DummyMessage>().apply {
            addAll(initialMessages)
        }
    }

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

    if (showVideoCallDialog) {
        AlertDialog(
            onDismissRequest = { showVideoCallDialog = false },
            title = { Text("Video Call") },
            text = { Text("Calling $phoneNumber...") },
            confirmButton = {
                TextButton(onClick = { showVideoCallDialog = false }) {
                    Text("End Call", color = Color.Red)
                }
            }
        )
    }

    if (showAudioCallDialog) {
        AlertDialog(
            onDismissRequest = { showAudioCallDialog = false },
            title = { Text("Audio Call") },
            text = { Text("Calling $phoneNumber...") },
            confirmButton = {
                TextButton(onClick = { showAudioCallDialog = false }) {
                    Text("End Call", color = Color.Red)
                }
            }
        )
    }

    if (showImageSourceDialog) {
        AlertDialog(
            onDismissRequest = { showImageSourceDialog = false },
            title = { Text("Select Image Source") },
            text = { Text("Choose between Camera and Gallery") },
            confirmButton = {
                TextButton(onClick = {
                    showImageSourceDialog = false
                    val permissionCheckResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(null)
                    } else {
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }) {
                    Text("Camera")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showImageSourceDialog = false
                    galleryLauncher.launch("image/*")
                }) {
                    Text("Gallery")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            val displayName = when(phoneNumber) {
                                "someone" -> "someone"
                                "9876543210" -> "Alice"
                                "Group" -> "Project Group"
                                "1234567890" -> "Bob"
                                "5551234567" -> "Charlie"
                                else -> phoneNumber
                            }
                            Text(
                                text = displayName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "online",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { showVideoCallDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Videocam,
                            contentDescription = "Video Call",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { showAudioCallDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Call,
                            contentDescription = "Audio Call",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.Royal_Blue)
                )
            )
        },
        bottomBar = {
            ChatInputBar(
                text = messageText,
                onValueChange = { messageText = it },
                onSend = {
                    if (messageText.isNotBlank()) {
                        val userMessage = messageText
                        val currentTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
                        messages.add(DummyMessage(userMessage, true, currentTime))
                        messageText = ""

                        if (phoneNumber == "Gupshup" || phoneNumber == "someone") {
                            scope.launch {
                                delay(1000)
                                val aiReply = when {
                                    userMessage.lowercase().contains("hii") || userMessage.lowercase().contains("hi") || userMessage.lowercase().contains("hello") -> "Hii! How are you?"
                                    userMessage.lowercase().contains("how are you") -> "I'm doing great! How can I help you today?"
                                    userMessage.lowercase().contains("help") -> "Sure! I can help you with app navigation, settings, or just have a chat. What do you need?"
                                    userMessage.lowercase().contains("feature") -> "GupShup has many features: real-time messaging, AI assistance, image sharing, and soon video/audio calls!"
                                    userMessage.lowercase().contains("profile") || userMessage.lowercase().contains("picture") -> "To change your profile picture, go to Settings and tap on your current profile image."
                                    userMessage.lowercase().contains("name") -> "I am 'someone', your AI assistant here to make your experience wonderful!"
                                    userMessage.lowercase().contains("thank") -> "You're very welcome! Is there anything else you'd like to know?"
                                    userMessage.lowercase().contains("bye") || userMessage.lowercase().contains("goodbye") -> "Goodbye! Have a great day ahead!"
                                    userMessage.lowercase().contains("joke") -> "Why don't scientists trust atoms? Because they make up everything!"
                                    else -> "That's interesting! Tell me more about that or ask me something else."
                                }
                                val replyTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
                                messages.add(DummyMessage(aiReply, false, replyTime))
                            }
                        }
                    }
                },
                onCameraClick = {
                    showImageSourceDialog = true
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFE5DDD5)) 
        ) {
            // Background Pattern (Simulated with color for now)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                reverseLayout = false
            ) {
                item { Spacer(modifier = Modifier.height(12.dp)) }
                items(messages) { message ->
                    ChatBubble(message)
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
            }
        }
    }
}

@Composable
fun ChatInputBar(
    text: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    onCameraClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .navigationBarsPadding()
            .imePadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            shadowElevation = 1.dp
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
            ) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Face, contentDescription = "Emoji", tint = Color.Gray)
                }
                TextField(
                    value = text,
                    onValueChange = onValueChange,
                    placeholder = { Text("Message", color = Color.Gray) },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    maxLines = 4
                )
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Add, contentDescription = "Attach", tint = Color.Gray)
                }
                IconButton(onClick = onCameraClick) {
                    Icon(Icons.Default.CameraAlt, contentDescription = "Camera", tint = Color.Gray)
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        FloatingActionButton(
            onClick = onSend,
            modifier = Modifier.size(48.dp),
            shape = CircleShape,
            containerColor = colorResource(id = R.color.Royal_Blue),
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(2.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.Send,
                contentDescription = "Send",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun ChatBubble(message: DummyMessage) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = if (message.isSentByMe) Alignment.End else Alignment.Start
    ) {
        Surface(
            color = if (message.isSentByMe) Color(0xFFDCF8C6) else Color.White,
            shape = RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp,
                bottomStart = if (message.isSentByMe) 8.dp else 0.dp,
                bottomEnd = if (message.isSentByMe) 0.dp else 8.dp
            ),
            shadowElevation = 1.dp
        ) {
            Column(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                Text(
                    text = message.text,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = message.time,
                    fontSize = 11.sp,
                    color = Color.Gray,
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}
