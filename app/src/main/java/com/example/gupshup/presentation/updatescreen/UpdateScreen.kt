package com.example.gupshup.presentation.updatescreen

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.example.gupshup.R
import com.example.gupshup.presentation.bottomnavigation.BottomNavigation
import com.example.gupshup.presentation.navigation.Routes
import com.example.gupshup.presentation.updatescreen.TopBar


@Composable
fun UpdateScreen(navHostController: NavHostController, outerPadding: PaddingValues = PaddingValues(0.dp)) {

    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var showImageSourceDialog by remember { mutableStateOf(false) }

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
        topBar = {
            TopBar(
                title = "Updates",
                onSettingsClick = { navHostController.navigate(Routes.SettingsScreen) },
                showCamera = true,
                onCameraClick = {
                    showImageSourceDialog = true
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    showImageSourceDialog = true
                },
                containerColor = colorResource(id = R.color.Royal_Blue),
                modifier = Modifier
                    .padding(bottom = outerPadding.calculateBottomPadding())
                    .size(65.dp),
                contentColor = Color.White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.camera222),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(bottom = outerPadding.calculateBottomPadding())
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
            ) {
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

                HorizontalDivider(color = Color.Gray)

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
}

