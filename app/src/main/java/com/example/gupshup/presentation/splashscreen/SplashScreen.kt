package com.example.gupshup.presentation.splashscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gupshup.R
import com.example.gupshup.presentation.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navHostController: NavHostController){

    LaunchedEffect(Unit){

        delay(1500)

        val currentUser = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            navHostController.navigate(Routes.HomeScreen) {
                popUpTo<Routes.SplashScreen> {
                    inclusive = true
                }
            }
        } else {
            navHostController.navigate(Routes.WelcomeScreen) {
                popUpTo<Routes.SplashScreen> {
                    inclusive = true
                }
            }
        }
    }


    Box(modifier = Modifier.fillMaxSize()) {

        Image(painter = painterResource(id = R.drawable.logo), // Changed R.drawable.h to R.drawable.logo
            contentDescription = null,
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.Center)
        )

        Column(modifier = Modifier.align(Alignment.BottomCenter)){

            Text(text = "From", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Row{
                Text(text = "D,H,P", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
        }
    }

}