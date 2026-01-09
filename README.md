# 🚀 Leta App - MMUST Delivery Super App

[![Android](https://img.shields.io/badge/Platform-Android-green.svg)](https://www.android.com/)
[![Java](https://img.shields.io/badge/Language-Java-orange.svg)](https://www.java.com/)
[![Supabase](https://img.shields.io/badge/Backend-Supabase-3ECF8E.svg)](https://supabase.com/)
[![OSM](https://img.shields.io/badge/Maps-OpenStreetMap-blue.svg)](https://www.openstreetmap.org/)
[![Free](https://img.shields.io/badge/Cost-100%25%20FREE-success.svg)](https://supabase.com/)

## 📖 Overview
Leta App is a hyper-local delivery super app designed specifically for the MMUST university campus. It connects students, vendors, and riders in a single platform with AI-powered dispatch and cost-efficient routing.

### The Problem
- 🗺️ Google Maps is too expensive and inaccurate for campus shortcuts
- 🍔 Students go hungry during short breaks
- 📦 Vendors lose sales due to lack of reach
- 🚴 Students with bikes need income opportunities

### The Solution
A **Single App architecture** with three dashboards:
- 👨‍🎓 **Student Dashboard**: Browse menus, order food, track deliveries
- 🏪 **Vendor Dashboard**: Manage orders, track sales, update menu
- 🚴 **Rider Dashboard**: Accept deliveries, track earnings, view hotspots

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

## ⚡ Quick Start (5 Minutes)

### Prerequisites
- Android Studio (latest version)
- Android device or emulator (Android 6.0+)
- Firebase account
- Paystack account (optional for payments)

### Step 1: Clone the Repository
```bash
git clone https://github.com/sibby-killer/Letaapp.git
cd Letaapp
```

### Step 2: Configure Supabase (100% FREE - No Card Needed!)

#### 2.1 Create Supabase Project
1. Go to [Supabase](https://supabase.com/)
2. Click **"Start your project"** → Sign up with GitHub or email
3. Click **"New Project"**
4. Fill in:
   - **Name**: `Leta App`
   - **Database Password**: Create strong password (save it!)
   - **Region**: Singapore or closest to Kenya
5. Click **"Create new project"**
6. ⏳ Wait 2-3 minutes for project setup

#### 2.2 Get Your Credentials
1. Go to **Settings** (gear icon) → **API**
2. Copy:
   - **Project URL**: `https://xxxxx.supabase.co`
   - **anon public key**: `eyJhbGci...` (very long key)

#### 2.3 Update App Configuration
Open `app/src/main/res/raw/config.properties` and paste your credentials:

```properties
# Supabase Configuration
supabase_url=https://xxxxx.supabase.co
supabase_anon_key=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### 2.4 Create Database Tables
1. In Supabase Dashboard, click **SQL Editor** → **New query**
2. Copy the entire SQL script from `SUPABASE_SETUP.md` (Step 3)
3. Paste and click **Run**
4. ✅ Creates `users`, `orders`, and `menu_items` tables with security policies

#### 2.5 Configure Authentication
1. Go to **Authentication** → **Settings**
2. Find **"Enable email confirmations"**
3. **Toggle OFF** (for faster testing - can enable later)
4. Click **Save**

✅ **That's it! No credit card needed!**

### Step 3: Configure API Keys (Optional for now)

Open `app/src/main/res/raw/config.properties` and add your keys:

```properties
# Paystack (Get from https://dashboard.paystack.com/)
paystack_public_key=pk_test_xxxxxxxxxxxxxxxxxxxxx
paystack_secret_key=sk_test_xxxxxxxxxxxxxxxxxxxxx

# Other settings (already configured)
mmust_latitude=0.2827
mmust_longitude=34.7519
standard_delivery_fee=50
urgent_delivery_fee=100
```

### Step 4: Build and Run

#### Option A: Android Studio
1. Open project in Android Studio
2. Let Gradle sync complete
3. Connect your device or start emulator
4. Click **Run** ▶️ button

#### Option B: Command Line
```bash
# Clean and build
.\gradlew.bat clean assembleDebug

# Install on connected device
.\gradlew.bat installDebug
```

### Step 5: Test the App

1. **Launch** the app → Wait for splash screen
2. Click **Sign Up** tab
3. Enter:
   - Email: `test@mmust.ac.ke`
   - Password: `test123456`
   - Confirm Password: `test123456`
4. Click **SIGN UP**
5. Select your role (Student/Vendor/Rider)
6. **Explore** the dashboard!

---

## 🎯 Features Implemented

### ✅ Complete UI (18 Screens)
- Splash screen with animation
- Login/Signup with Firebase
- Role selection (Student/Vendor/Rider)
- Student home with OSM map
- Shopping cart and checkout
- Order tracking
- Referral system
- Vendor dashboard and menu manager
- Rider dashboard with earnings
- AI chat interface

### ✅ Authentication System
- Email/Password signup with validation
- Password confirmation field
- Real-time input validation
- Eye icon for password visibility
- Forgot password functionality
- Session management with SharedPreferences
- User-friendly error messages
- **Powered by Supabase** - 100% FREE!

### ✅ Database & Backend
- **Supabase PostgreSQL** - More powerful than Firestore
- Row Level Security (RLS) policies
- Real-time subscriptions ready
- 50K monthly active users (free)
- 500MB database storage (free)
- No credit card required!

### ✅ Maps Integration
- OpenStreetMap (OSM) - no Google Maps fees!
- Centered on MMUST campus (0.2827, 34.7519)
- Real-time location tracking
- My Location button

### ✅ Security
- No hardcoded API keys
- Configuration file for sensitive data
- .gitignore protects secrets
- Row Level Security policies in Supabase
- JWT authentication
- ProGuard rules for release

### ✅ Code Quality
- 100% Java (no Kotlin)
- ViewBinding throughout
- Error handling system
- Input validation helpers
- Permission management
- Data models (User, Order, CartItem, MenuItem)

---

## 🏗️ Project Structure

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

## 🐛 Troubleshooting

### Build Errors
**Problem**: "Invalid API key" or authentication fails
- **Solution**: Check `config.properties` has correct `supabase_url` and `supabase_anon_key`
- Make sure you copied the ENTIRE anon key (it's very long!)

**Problem**: "Failed to create user profile"
- **Solution**: Run the SQL script from `SUPABASE_SETUP.md` to create tables
- Verify tables exist in Supabase Dashboard → Table Editor

**Problem**: Gradle sync fails
- **Solution**: Run `.\gradlew.bat --refresh-dependencies`
- Make sure internet connection is stable

### App Crashes
**Problem**: Map doesn't load
- **Solution**: Grant location permissions, check internet connection

**Problem**: Can't login after signup
- **Solution**: Check Firestore security rules are published correctly

---

## 📞 Support & Contributing

### Found a Bug?
Open an issue on GitHub with:
- Description of the bug
- Steps to reproduce
- Expected vs actual behavior
- Screenshots if applicable

### Want to Contribute?
1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📜 License
This project is proprietary - MMUST Leta App © 2024

---

## 🙏 Acknowledgments
- MMUST Campus Community
- Firebase for backend services
- OpenStreetMap for mapping
- Paystack for payment processing
- Material Design for UI components

---

**Built with ❤️ for MMUST Campus**

**Developer**: [@sibby-killer](https://github.com/sibby-killer)  
**Repository**: [Letaapp](https://github.com/sibby-killer/Letaapp)
