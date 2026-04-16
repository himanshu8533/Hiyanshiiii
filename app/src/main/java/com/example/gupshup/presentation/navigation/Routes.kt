package com.example.gupshup.presentation.navigation

import kotlinx.serialization.Serializable

sealed class
Routes  {

    @Serializable
    data object SplashScreen : Routes()

    @Serializable
    data object WelcomeScreen : Routes()

    @Serializable
    data object UserRegistrationScreen : Routes()

    @Serializable
    data object HomeScreen : Routes()

    @Serializable
    data object UpdateScreen : Routes()

    @Serializable
    data object CommunitiesScreen : Routes()

    @Serializable
    data object CallsScreen : Routes()

    @Serializable
    data object UserProfileSetScreen : Routes()
    @Serializable
    data object SettingsScreen : Routes()

    @Serializable
    data object AccountSettingsScreen : Routes()

    @Serializable
    data object PrivacySettingsScreen : Routes()

    @Serializable
    data object ProfileScreen : Routes()

    @Serializable
    data object ChangePhoneNumberScreen : Routes()

    @Serializable
    data object EmailAddressScreen : Routes()

    @Serializable
    data object ChatScreen : Routes(){
        const val route ="chat_screen/{phoneNumber}"
        fun createRoutes(phoneNumber: String) = "chat_screen/$phoneNumber"
    }

    @Serializable
    data object ReelScreen : Routes()

    @Serializable
    data object ChatThemeScreen : Routes()

    @Serializable
    data object MainContainerScreen : Routes()
}


