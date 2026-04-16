package com.example.gupshup.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.gupshup.presentation.callscreen.CallScreen
import com.example.gupshup.presentation.chatscreen.ChatScreen
import com.example.gupshup.presentation.communitiesscreen.CommunitiesScreen
import com.example.gupshup.presentation.homescreen.HomeScreen
import com.example.gupshup.presentation.maincontainer.MainContainerScreen
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
            MainContainerScreen(navController)
        }

        composable<Routes.UpdateScreen>{
            MainContainerScreen(navController)
        }

        composable<Routes.CommunitiesScreen> {
            MainContainerScreen(navController)
        }
        composable<Routes.CallsScreen> {
            MainContainerScreen(navController)
        }
        
        composable<Routes.ReelScreen> {
            MainContainerScreen(navController)
        }
        
        composable<Routes.MainContainerScreen> {
            MainContainerScreen(navController)
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

        composable<Routes.ChatThemeScreen> {
            com.example.gupshup.presentation.settings.ChatThemeScreen(navController = navController)
        }

        composable<Routes.ChatScreen> { backStackEntry ->
            val route: Routes.ChatScreen = backStackEntry.toRoute()
            ChatScreen(navController = navController, phoneNumber = route.phoneNumber)
        }


    }

}