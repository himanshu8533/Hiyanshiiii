package com.example.gupshup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gupshup.presentation.communitiesscreen.CommunitiesScreen
import com.example.gupshup.presentation.homescreen.HomeScreen
import com.example.gupshup.presentation.splashscreen.SplashScreen
import com.example.gupshup.presentation.updatescreen.UpdateScreen
import com.example.gupshup.presentation.userregistrationscreen.AuthScreen
import com.example.gupshup.presentation.welcomescreen.WelcomeScreen

@Composable
fun WatsAppNavigationSystem(){

    val navController = rememberNavController()

    NavHost(startDestination = Routes.SplashScreen, navController = navController){

        composable<Routes.SplashScreen>{
            SplashScreen(navController)
        }

        composable<Routes.WelcomeScreen>{
            WelcomeScreen(navController)
        }

        composable<Routes.UserRegistrationScreen>{
            AuthScreen(navController)
        }

        composable<Routes.HomeScreen>{
            HomeScreen()
        }

        composable<Routes.UpdateScreen>{
            UpdateScreen()
        }

        composable<Routes.CommunitiesScreen> {
            CommunitiesScreen()
        }


    }

}