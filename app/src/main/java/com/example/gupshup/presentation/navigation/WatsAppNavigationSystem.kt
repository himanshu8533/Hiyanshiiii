package com.example.gupshup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gupshup.presentation.callscreen.CallScreen
import com.example.gupshup.presentation.communitiesscreen.CommunitiesScreen
import com.example.gupshup.presentation.homescreen.HomeScreen
import com.example.gupshup.presentation.profile.userProfileSetScreen
import com.example.gupshup.presentation.splashscreen.SplashScreen
import com.example.gupshup.presentation.updatescreen.UpdateScreen
import com.example.gupshup.presentation.userregistrationscreen.AuthScreen
import com.example.gupshup.presentation.viewmodels.BaseViewModel
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
            val baseViewModel: BaseViewModel= hiltViewModel()
            HomeScreen(navController,baseViewModel)
        }

        composable<Routes.UpdateScreen>{
            UpdateScreen(navController)
        }

        composable<Routes.CommunitiesScreen> {
            CommunitiesScreen(navController)
        }
        composable<Routes.CallsScreen> {
            CallScreen(navController)
        }


        composable<Routes.UserProfileSetScreen> {
            // Now this only requires the navController
            userProfileSetScreen(navHostController = navController)
        }

        composable<Routes.SettingsScreen> {
            com.example.gupshup.presentation.settings.SettingsScreen(navController = navController)
        }

        composable<Routes.AccountSettingsScreen> {
            com.example.gupshup.presentation.settings.AccountSettingsScreen(navController = navController)
        }

        composable<Routes.PrivacySettingsScreen> {
            com.example.gupshup.presentation.settings.PrivacySettingsScreen(navController = navController)
        }

        composable<Routes.ProfileScreen> {
            com.example.gupshup.presentation.settings.ProfileScreen(navController = navController)
        }

        composable<Routes.ChangePhoneNumberScreen> {
            com.example.gupshup.presentation.settings.ChangePhoneNumberScreen(navController = navController)
        }

        composable<Routes.EmailAddressScreen> {
            com.example.gupshup.presentation.settings.EmailAddressScreen(navController = navController)
        }

        composable<Routes.ReelScreen> {
            com.example.gupshup.presentation.reelscreen.ReelScreen(navHostController = navController)
        }

        composable<Routes.ChatThemeScreen> {
            com.example.gupshup.presentation.settings.ChatThemeScreen(navController = navController)
        }


    }

}