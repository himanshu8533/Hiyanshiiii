package com.example.gupshup.presentation.splashscreen

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.gupshup.R
import com.example.gupshup.presentation.navigation.Routes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navHostController: NavHostController){
    val scale = remember { Animatable(0f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit){
        launch {
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 1000)
            )
        }

        delay(2500) // Slightly longer to see the animation

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

        Image(painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .size(350.dp)
                .align(Alignment.Center)
                .scale(scale.value)
                .alpha(alpha.value)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .alpha(alpha.value),
            horizontalAlignment = Alignment.CenterHorizontally
        ){

            Text(text = "From", fontSize = 16.sp, color = androidx.compose.ui.graphics.Color.Gray)
            Text(text = "D,H,P", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = androidx.compose.ui.graphics.Color(0xFF008069))
        }
    }

}
