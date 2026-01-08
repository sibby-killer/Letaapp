# Leta App - MMUST Delivery Super App

## Overview
Leta App is a hyper-local delivery super app designed specifically for the MMUST university campus. It connects students, vendors, and riders in a single platform with AI-powered dispatch and cost-efficient routing.

## Features

### Three User Dashboards
- **Student Dashboard**: Browse menus, order food, track deliveries
- **Vendor Dashboard**: Manage orders, track sales, update menu
- **Rider Dashboard**: Accept deliveries, track earnings, view hotspots

### Key Technologies
- **Native Java** - Pure Android development
- **Firebase Authentication** - Email/Password and Google Sign-In
- **Cloud Firestore** - User roles and data management
- **OpenStreetMap (OSM)** - Cost-efficient mapping (no Google Maps fees)
- **Paystack** - Split payments for commission automation
- **ViewBinding** - Type-safe view access

## Project Structure

```
app/src/main/
├── java/com/mmust/leta/
│   ├── SplashActivity.java          # App entry point with auth check
│   ├── AuthActivity.java            # Login/Signup with Firebase
│   ├── SelectRoleActivity.java      # Role selection (Student/Vendor/Rider)
│   ├── UserRouter.java              # Role-based navigation logic
│   ├── StudentHomeActivity.java     # Student dashboard with OSM map
│   ├── VendorDashboardActivity.java # Vendor order management
│   ├── RiderHomeActivity.java       # Rider delivery interface
│   └── MainActivity.java            # Legacy (to be removed)
├── res/
│   ├── layout/
│   │   ├── activity_splash.xml
│   │   ├── activity_auth.xml
│   │   ├── activity_select_role.xml
│   │   ├── activity_student_home.xml
│   │   ├── activity_vendor_dashboard.xml
│   │   └── activity_rider_home.xml
│   ├── menu/
│   │   ├── bottom_nav_student.xml
│   │   ├── bottom_nav_vendor.xml
│   │   └── bottom_nav_rider.xml
│   └── values/
│       ├── colors.xml               # Brand colors (#2BEE79 primary)
│       ├── strings.xml
│       └── themes.xml
└── AndroidManifest.xml              # All activities and permissions
```

## Setup Instructions

### 1. Firebase Setup
1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Create a new project or select existing one
3. Add an Android app with package name: `com.mmust.leta`
4. Download `google-services.json` and replace the placeholder in `app/`
5. Enable Authentication (Email/Password and Google)
6. Create a Firestore database with collection: `users`

### 2. Update Firebase Configuration
Replace in `app/src/main/res/values/strings.xml`:
```xml
<string name="default_web_client_id">YOUR_ACTUAL_FIREBASE_WEB_CLIENT_ID</string>
```

Get this from Firebase Console → Project Settings → General → Web API Key

### 3. Firestore Security Rules
```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
  }
}
```

### 4. Build and Run
```bash
# Build the project
./gradlew build

# Install on device/emulator
./gradlew installDebug
```

## User Flow

### New User Journey
1. **Splash Screen** (3 seconds) → Checks authentication
2. **Auth Screen** → Sign up with email or Google
3. **Select Role** → Choose Student, Vendor, or Rider
4. **Dashboard** → Navigate to role-specific home screen

### Returning User
1. **Splash Screen** → Auto-detects role from Firestore
2. **Dashboard** → Direct navigation to appropriate screen

## App Architecture

### VIBE Framework Implementation
✅ **V - Visuals**: UI extracted from Stitch designs, converted to Android XML
✅ **I - Interface**: Splash → Auth → Role Selection → Role Dashboard flow
✅ **B - Backend**: Firebase Auth + Firestore + OSMDroid + ViewBinding
✅ **E - Exclusions**: No Kotlin, No Google Maps, Native Java only

### UserRouter Logic
The `UserRouter.java` class handles role-based navigation:
- Queries Firestore for user's role
- Routes to appropriate dashboard
- Handles new users without roles
- Implements safe error handling

## Dependencies

```gradle
// Maps
implementation 'org.osmdroid:osmdroid-android:6.1.16'

// Firebase
implementation platform('com.google.firebase:firebase-bom:33.7.0')
implementation 'com.google.firebase:firebase-auth'
implementation 'com.google.firebase:firebase-firestore'
implementation 'com.google.android.gms:play-services-auth:21.3.0'

// Payments
implementation 'co.paystack.android:paystack:3.1.3'
```

## MMUST Campus Coordinates
- **Latitude**: 0.2827
- **Longitude**: 34.7519
- **Default Zoom**: 16.0

All map activities center on these coordinates by default.

## Color Scheme
- **Primary**: `#2BEE79` (Leta Green)
- **Background Dark**: `#102217`
- **Surface Dark**: `#193324`
- **Text Secondary**: `#92C9A8`

## Permissions Required
- `INTERNET` - API calls and map tiles
- `ACCESS_FINE_LOCATION` - User location tracking
- `ACCESS_COARSE_LOCATION` - Approximate location
- `ACCESS_NETWORK_STATE` - Network connectivity checks

## Next Steps (TODO)

### Student Features
- [ ] Browse vendor menus
- [ ] Add items to cart
- [ ] Place orders with Paystack
- [ ] Track order status in real-time
- [ ] View order history

### Vendor Features
- [ ] Menu manager (add/edit/delete items)
- [ ] Accept/reject orders
- [ ] Order processing workflow
- [ ] Sales analytics dashboard
- [ ] Payout tracking

### Rider Features
- [ ] AI dispatch algorithm (Greedy/Nearest Neighbor)
- [ ] Accept delivery jobs
- [ ] Turn-by-turn navigation
- [ ] Earnings tracking
- [ ] Performance metrics

### General
- [ ] Real-time notifications
- [ ] In-app chat support
- [ ] Referral system
- [ ] Rating system
- [ ] Split payment integration

## Contributing
This is a campus-specific project. For feature requests or bugs, create an issue.

## License
Proprietary - MMUST Leta App

## Contact
For support: [Your Contact Info]

---
**Built with ❤️ for MMUST Campus**
