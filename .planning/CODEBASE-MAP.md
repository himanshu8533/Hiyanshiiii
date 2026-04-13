# Codebase Map - GupShup (WhatsApp-like Android App)

## Stack
- **Language:** Kotlin 2.3.4
- **UI Framework:** Jetpack Compose (Material 3)
- **Architecture:** MVVM with Clean Architecture patterns
- **Dependency Injection:** Dagger Hilt 2.59.2
- **Backend:** Firebase (Realtime Database, Authentication)
- **Navigation:** Jetpack Navigation Compose with type-safe routes
- **Image Loading:** Coil 3.3.0
- **Serialization:** kotlinx-serialization 1.10.0
- **Build Tool:** Gradle (Kotlin DSL)
- **Code Processing:** KSP (Kotlin Symbol Processing)
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 36
- **Java Compatibility:** Java 11

## Architecture
- **Pattern:** MVVM (Model-View-ViewModel) with presentation layer separation
- **Entry Point:** `MainActivity.kt` → `WatsAppNavigationSystem()`
- **Application Class:** `GupshupApplication` with `@HiltAndroidApp`
- **DI Module:** `di/AppModule.kt` (provides Firebase singleton instances)

### Module Structure
```
com.example.gupshup/
├── di/                          # Dependency Injection
│   └── AppModule.kt             # Firebase providers
├── models/                      # Data models
│   ├── Message.kt
│   └── PhoneAuthUser.kt
├── presentation/                # UI layer (MVVM)
│   ├── bottomnavigation/        # Bottom navigation component
│   ├── callscreen/              # Call screen features
│   ├── chat_box/                # Chat list components
│   ├── communitiesscreen/       # Communities feature
│   ├── homescreen/              # Main home screen
│   ├── navigation/              # Navigation system
│   ├── profile/                 # User profile setup
│   ├── reelscreen/              # Reels feature
│   ├── settings/                # Settings screens (multiple)
│   ├── splashscreen/            # App splash screen
│   ├── updatescreen/            # Updates/status screen
│   ├── userregistrationscreen/  # Auth/registration
│   ├── viewmodels/              # ViewModel classes
│   └── welcomescreen/           # Welcome/onboarding
├── ui/theme/                    # Theme configuration
│   ├── Color.kt
│   ├── Theme.kt
│   └── Type.kt
└── MainActivity.kt              # Main entry activity
```

## Conventions
- **Package Structure:** Feature-based organization under `presentation/`
- **Naming:** 
  - PascalCase for composables and screens
  - camelCase for variables and functions
  - Suffixes: `Screen` for composables, `ViewModel` for ViewModels
- **File Organization:** One class/component per file
- **Code Style:** Official Kotlin code style (per gradle.properties)
- **UI Components:** Jetpack Compose declarative UI
- **State Management:** StateFlow in ViewModels
- **Firebase Integration:** Direct Firebase SDK usage (no repository layer abstraction)
- **Imports:** Standard imports, fully qualified names for disambiguation

## Key Features Implemented
1. **Phone Authentication:** Firebase Auth with phone number
2. **Chat System:** Real-time messaging via Firebase Realtime Database
3. **Navigation:** Type-safe routes with sealed class
4. **Multiple Screens:** Home, Updates, Communities, Calls, Reels, Settings
5. **Profile Management:** User profile setup and editing
6. **Settings Suite:** Account, Privacy, Profile, Phone/Email changes
7. **Base64 Image Handling:** Profile image encoding/decoding

## Concerns
- ⚠️ **No Repository Layer:** ViewModels directly access Firebase (tight coupling)
- ⚠️ **Missing Error Handling:** Limited error handling in database operations
- ⚠️ **Base64 Images:** Storing images as Base64 strings (inefficient, consider Firebase Storage)
- ⚠️ **No Unit Tests:** Example tests exist but no actual test coverage
- ⚠️ **No Instrumentation Tests:** Only placeholder test in androidTest
- ⚠️ **Hardcoded Values:** Some magic numbers/strings in ViewModels
- ⚠️ **Security:** Phone numbers used as database keys (privacy concern)
- ⚠️ **Memory Management:** Bitmap decoding without size limits (potential OOM)
- ⚠️ **Minify Disabled:** Release builds have `isMinifyEnabled = false`
- ℹ️ **Consider:** Adding Room database for offline support
- ℹ️ **Consider:** Implementing use case layer for clean architecture
- ℹ️ **Consider:** Adding coroutines/Flow wrappers for Firebase operations
- ℹ️ **Consider:** ProGuard rules for production release

## Dependencies Status
### Production
- ✅ AndroidX Core, Lifecycle, Activity Compose
- ✅ Compose BOM (Bill of Materials)
- ✅ Material 3 + Material Icons
- ✅ Firebase Database & Auth
- ✅ Hilt DI
- ✅ Navigation Compose
- ✅ Coil 3 (image loading)
- ✅ kotlinx-serialization

### Testing
- ⚠️ JUnit (placeholder)
- ⚠️ AndroidX Test (placeholder)
- ⚠️ Espresso (placeholder)
- ⚠️ Compose UI Test (placeholder)

## Build Configuration
- **Version Catalog:** Using `libs.*` references
- **Compile SDK:** 36
- **KSP:** Enabled for Hilt code generation
- **Compose:** Enabled with official Compose plugin
