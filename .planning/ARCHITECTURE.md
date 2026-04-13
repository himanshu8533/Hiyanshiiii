# Architecture - GupShup Android Application

## Architectural Pattern
**MVVM (Model-View-ViewModel)** with presentation-focused separation

```
┌─────────────────────────────────────────┐
│           Presentation Layer            │
│  (Composables, Screens, Navigation)     │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│            ViewModel Layer              │
│    (State Management, Firebase Calls)   │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│           Data/Model Layer              │
│   (Firebase, Data Classes, Models)      │
└─────────────────────────────────────────┘
```

## Application Bootstrap

### Entry Point
1. **Application Class:** `GupshupApplication`
   - Annotated with `@HiltAndroidApp`
   - Initializes Hilt dependency injection
   
2. **MainActivity:** `MainActivity.kt`
   - Annotated with `@AndroidEntryPoint`
   - Sets up Compose UI with `GupShupTheme`
   - Launches `WatsAppNavigationSystem()`

### Dependency Injection
- **Module:** `di/AppModule.kt`
- **Scope:** Singleton (application-wide)
- **Provides:**
  - `FirebaseAuth` instance
  - `FirebaseDatabase` instance

## Navigation Architecture

### Type-Safe Routes
- **Routes.kt:** Sealed class/interface defining all navigation destinations
- **Navigation Compose:** `NavHost` with typed composable destinations

### Navigation Graph
```
SplashScreen → WelcomeScreen → AuthScreen → HomeScreen
                                          → UpdateScreen
                                          → CommunitiesScreen
                                          → CallsScreen
                                          → Settings (various)
                                          → Profile screens
                                          → ReelScreen
```

## UI Layer (Presentation)

### Screen Organization
Each feature has its own package under `presentation/`:
- **Screens:** Composable functions named `{Feature}Screen`
- **Components:** Supporting UI components in same package
- **Navigation:** Screens receive `NavController` parameter

### State Management Pattern
```kotlin
// In ViewModel
private val _state = MutableStateFlow<State>(InitialState)
val state = _state.asStateFlow()

// In Composable
val state by viewModel.state.collectAsStateWithLifecycle()
```

### Theme System
- **GupShupTheme:** Custom theme wrapper
- **Color.kt:** Color palette definitions
- **Theme.kt:** Theme composition
- **Type.kt:** Typography definitions

## ViewModel Layer

### BaseViewModel
- **Location:** `presentation/viewmodels/BaseViewModel.kt`
- **Responsibilities:**
  - Firebase Realtime Database operations
  - Chat management (CRUD)
  - Message sending/receiving
  - User search by phone number
  - Chat list loading
  - Base64 image processing

### ViewModel Injection
- Using `@HiltViewModel` (implied)
- Injected via `hiltViewModel()` in composables

## Data Layer

### Models
- **Message.kt:** Chat message data class
- **PhoneAuthUser.kt:** User authentication model
- **ChatListModel.kt:** Chat list item model

### Firebase Structure
```
users/
  └── {userId}/
      └── chats/
          
chats/
  └── {phoneNumber}/
  
messages/
  └── {senderPhone}/
      └── {receiverPhone}/
          └── {messageId}
```

## Data Flow

### Sending a Message
```
User Input → ViewModel.sendMessage() 
           → Firebase.setValue() 
           → Dual-write (sender & receiver paths)
```

### Receiving Messages
```
Firebase.addChildEventListener() 
         → onChildAdded callback 
         → onNewMessage callback to UI
```

### Loading Chats
```
ViewModel.loadChatList() 
         → Firebase.addListenerForSingleValueEvent()
         → Fetch last message per chat
         → Update StateFlow
         → UI observes and renders
```

## Current Architecture Concerns

### Missing Layers
- **No Repository Pattern:** ViewModels directly call Firebase
- **No Use Case Layer:** Business logic mixed with data access
- **No Data Source Abstraction:** Single Firebase implementation

### Tight Coupling
- ViewModels depend on concrete Firebase instances
- No interfaces for data operations
- Direct `FirebaseAuth.getInstance()` calls in ViewModels

### State Management
- Mix of callbacks and StateFlow
- No unified state model for screens
- Error state not consistently tracked

## Recommended Architecture Evolution

### Short-Term
1. Add Repository layer between ViewModels and Firebase
2. Introduce interfaces for data operations
3. Standardize on Flow-based APIs

### Long-Term
1. Add Use Case/Interactor layer
2. Implement offline support with Room
3. Add comprehensive error handling
4. Unified state management with sealed interfaces
5. Add data source abstraction (local vs remote)

## Testing Strategy (Current)
- ⚠️ No unit tests implemented
- ⚠️ No instrumentation tests implemented
- ⚠️ Test files exist but contain only placeholders

## Security Considerations
- Phone numbers visible in database paths
- No data encryption mentioned
- Firebase security rules not in codebase
- Base64 image storage (inefficient, potential security issues)
