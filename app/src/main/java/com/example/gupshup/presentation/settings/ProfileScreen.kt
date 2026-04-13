package com.example.gupshup.presentation.settings

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.gupshup.R
import com.example.gupshup.presentation.viewmodels.BaseViewModel
import com.example.gupshup.presentation.viewmodels.PhoneAuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController, viewModel: BaseViewModel = hiltViewModel(), authViewModel: PhoneAuthViewModel = hiltViewModel()) {
    val currentUser = FirebaseAuth.getInstance().currentUser
    val context = LocalContext.current
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }
    var bitmapImage by remember { mutableStateOf<Bitmap?>(null) }

    var name by remember { mutableStateOf("User Name") }
    var about by remember { mutableStateOf("Hey there! I am using GupShup.") }
    var profileImageBase64 by remember { mutableStateOf<String?>(null) }
    var showEditNameDialog by remember { mutableStateOf(false) }
    var showEditAboutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(currentUser?.uid) {
        currentUser?.uid?.let { uid ->
            FirebaseDatabase.getInstance().getReference("users").child(uid).get()
                .addOnSuccessListener { snapshot ->
                    name = snapshot.child("name").value as? String ?: "User Name"
                    about = snapshot.child("status").value as? String ?: "Hey there! I am using GupShup."
                    profileImageBase64 = snapshot.child("profileImage").value as? String
                    profileImageBase64?.let {
                        bitmapImage = viewModel.base64ToBitmap(it)
                    }
                }
        }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            profileImageUri = uri
            uri?.let {
                bitmapImage = if (Build.VERSION.SDK_INT < 28) {
                    @Suppress("DEPRECATION")
                    android.provider.MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    ImageDecoder.decodeBitmap(source)
                }
                // Save profile image immediately
                currentUser?.uid?.let { uid ->
                    authViewModel.saveUserProfile(uid, name, about, bitmapImage)
                }
            }
        }
    )

    if (showEditNameDialog) {
        var tempName by remember { mutableStateOf(name) }
        AlertDialog(
            onDismissRequest = { showEditNameDialog = false },
            title = { Text("Enter your name") },
            text = {
                TextField(value = tempName, onValueChange = { tempName = it })
            },
            confirmButton = {
                TextButton(onClick = {
                    name = tempName
                    currentUser?.uid?.let { uid ->
                        authViewModel.saveUserProfile(uid, name, about, bitmapImage)
                    }
                    showEditNameDialog = false
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { showEditNameDialog = false }) { Text("Cancel") }
            }
        )
    }

    if (showEditAboutDialog) {
        var tempAbout by remember { mutableStateOf(about) }
        AlertDialog(
            onDismissRequest = { showEditAboutDialog = false },
            title = { Text("About") },
            text = {
                TextField(value = tempAbout, onValueChange = { tempAbout = it })
            },
            confirmButton = {
                TextButton(onClick = {
                    about = tempAbout
                    currentUser?.uid?.let { uid ->
                        authViewModel.saveUserProfile(uid, name, about, bitmapImage)
                    }
                    showEditAboutDialog = false
                }) { Text("Save") }
            },
            dismissButton = {
                TextButton(onClick = { showEditAboutDialog = false }) { Text("Cancel") }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clickable { imagePickerLauncher.launch("image/*") }
            ) {
                if (bitmapImage != null) {
                    Image(
                        bitmap = bitmapImage!!.asImageBitmap(),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.user_placeholder),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF008069))
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                ) {
                    Icon(
                        Icons.Default.CameraAlt,
                        contentDescription = "Change Photo",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            ProfileInfoItem(
                icon = Icons.Default.Person,
                label = "Name",
                value = name,
                onEditClick = { showEditNameDialog = true }
            )

            HorizontalDivider(modifier = Modifier.padding(start = 56.dp, top = 8.dp, bottom = 8.dp))

            ProfileInfoItem(
                icon = Icons.Default.Info,
                label = "About",
                value = about,
                onEditClick = { showEditAboutDialog = true }
            )

            HorizontalDivider(modifier = Modifier.padding(start = 56.dp, top = 8.dp, bottom = 8.dp))

            ProfileInfoItem(
                icon = Icons.Default.Phone,
                label = "Phone",
                value = currentUser?.phoneNumber ?: "Not available",
                onEditClick = null
            )
        }
    }
}


@Composable
fun ProfileInfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    onEditClick: (() -> Unit)?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(32.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = label, fontSize = 14.sp, color = Color.Gray)
            Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
        if (onEditClick != null) {
            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF008069))
            }
        }
    }
}
