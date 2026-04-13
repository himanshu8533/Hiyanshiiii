# Stack - GupShup Android Application

## Languages & Versions
- **Kotlin:** 2.3.4
- **Java Compatibility:** 11
- **Gradle:** Kotlin DSL (build.gradle.kts)
- **Min SDK:** 24 (Android 7.0 Nougat)
- **Target SDK:** 36
- **Compile SDK:** 36

## Core Framework
- **Android Application:** Standard Android app with single module
- **Jetpack Compose:** Modern declarative UI toolkit
  - Material 3 design system
  - Material Icons (core + extended)
  - Compose UI Tooling (debug)
  - Compose UI Testing (Junit4)

## Architecture & DI
- **Dagger Hilt:** 2.59.2
  - `@HiltAndroidApp` for Application class
  - `@AndroidEntryPoint` for Activities
  - `@Module` and `@Provides` for dependency provision
  - Hilt Navigation Compose: 1.3.0
- **KSP (Kotlin Symbol Processing):** 2.3.4
  - Used for Hilt code generation

## Backend Services
- **Firebase Authentication:** 24.0.1
  - Phone number authentication
  - FirebaseAuth instance
- **Firebase Realtime Database:** 22.0.1 (database), 2.0.1 (firebase-database)
  - Real-time message sync
  - Chat list management
  - User data storage
- **Google Services Plugin:** For Firebase integration

## Navigation
- **Navigation Compose:** 2.9.7
  - Type-safe routes with sealed classes
  - Composable destinations
  - NavHost for navigation graph

## State Management
- **Kotlin Flow:** 
  - MutableStateFlow for reactive state
  - StateFlow exposure to UI layer
- **ViewModel:** AndroidX Lifecycle ViewModel
- **Lifecycle Runtime KTX:** Lifecycle-aware components

## Image Handling
- **Coil 3:** 3.3.0
  - Modern image loading library for Compose
  - Used for network image loading
- **Base64 Encoding:** Manual bitmap handling for profile images

## Serialization
- **kotlinx-serialization-json:** 1.10.0
  - JSON serialization for type-safe navigation routes
  - Plugin: `kotlin("plugin.serialization")` version 2.3.10

## Testing
- **JUnit:** Unit testing framework
- **AndroidX JUnit:** Android test runner
- **Espresso:** UI automation testing
- **Compose UI Test:** Compose-specific testing APIs

## Build & Configuration
- **Version Catalog:** Centralized dependency management via `libs.*`
- **ProGuard:** Code shrinking/obfuscation (configured but disabled in release)
- **Google Services JSON:** `google-services.json` for Firebase config

## Theme & Design
- **Material 3:** Latest Material Design guidelines
- **Edge-to-Edge:** `enableEdgeToEdge()` for immersive UI
- **Custom Theme:** `GupShupTheme` with Color, Theme, Type configuration
