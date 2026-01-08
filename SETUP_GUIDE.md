# Leta App - Quick Setup Guide

## 🚀 Getting Started in 5 Minutes

### Step 1: Firebase Configuration (REQUIRED)

#### A. Create Firebase Project
1. Visit: https://console.firebase.google.com/
2. Click "Add project" → Name it "Leta App MMUST"
3. Disable Google Analytics (optional)
4. Click "Create project"

#### B. Add Android App
1. Click "Add app" → Select Android icon
2. **Package name**: `com.mmust.leta` (EXACT - don't change)
3. Download `google-services.json`
4. **IMPORTANT**: Replace the placeholder file at `app/google-services.json`

#### C. Enable Authentication
1. Go to: Build → Authentication → Get Started
2. Enable **Email/Password** sign-in method
3. Enable **Google** sign-in method
4. Copy the **Web client ID** (you'll need this)

#### D. Update Web Client ID
Open `app/src/main/res/values/strings.xml` and replace:
```xml
<string name="default_web_client_id">YOUR_FIREBASE_WEB_CLIENT_ID_HERE</string>
```

With your actual Web client ID from Firebase Console:
- Go to Project Settings → General
- Scroll to "Your apps" → Web API Key
- Copy the OAuth 2.0 client ID

#### E. Create Firestore Database
1. Go to: Build → Firestore Database → Create database
2. Choose "Start in **test mode**" (for development)
3. Select region: `us-central` or closest to Kenya
4. Click "Enable"

### Step 2: Build Configuration

The project is already configured with:
- ✅ Firebase dependencies
- ✅ OSMDroid for maps
- ✅ ViewBinding enabled
- ✅ All permissions set
- ✅ All activities registered

### Step 3: Test the Build

**Option A: Using Gradle Wrapper (Recommended)**
```powershell
# Windows
.\gradlew.bat assembleDebug

# Linux/Mac
./gradlew assembleDebug
```

**Option B: Using Android Studio**
1. Open Android Studio
2. File → Open → Select project root folder
3. Wait for Gradle sync
4. Click the green "Run" button

### Step 4: Run on Device

1. Enable USB Debugging on your Android device
2. Connect via USB
3. Run: `.\gradlew.bat installDebug` (Windows)
4. Or click "Run" in Android Studio

## 📱 Test User Flow

### Create Test Account
1. Launch app → Wait for splash screen
2. Click "Sign Up" tab
3. Enter email: `student@mmust.ac.ke`
4. Enter password: `Test123!`
5. Click "Sign Up"
6. Select "I am a Student"
7. Click "Continue"

You should see the Student Home screen with an OpenStreetMap centered on MMUST.

## 🔧 Troubleshooting

### Build Errors

**Problem**: `google-services.json` not found
- **Solution**: Replace the placeholder file with your actual Firebase config

**Problem**: `default_web_client_id` not found
- **Solution**: Update the string resource in `strings.xml`

**Problem**: OSMDroid not loading map tiles
- **Solution**: Ensure `INTERNET` permission is granted and device has network

**Problem**: Location not working
- **Solution**: Grant location permissions when app prompts

### Firebase Errors

**Problem**: "Auth operation not allowed"
- **Solution**: Enable Email/Password auth in Firebase Console

**Problem**: "Failed to get document: Permission denied"
- **Solution**: Set Firestore to test mode or update security rules

### Google Sign-In Not Working
- Ensure SHA-1 fingerprint is added to Firebase:
```bash
# Get debug keystore SHA-1
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```
- Add SHA-1 to Firebase Console → Project Settings → SHA certificate fingerprints

## 🧪 Testing Different Roles

### Student Role
1. Sign up with email ending in `@mmust.ac.ke`
2. Select "I am a Student"
3. Test Features:
   - Map view centered on MMUST
   - Bottom navigation (Home, Orders, Cart, Profile)
   - Search functionality (placeholder)

### Vendor Role
1. Sign up with different email
2. Select "I am a Vendor"
3. Test Features:
   - Dashboard with sales stats
   - Tabs: Active Orders, Menu, History
   - Order management interface

### Rider Role
1. Sign up with third email
2. Create user document manually in Firestore with `role: "rider"`
3. Test Features:
   - Map with earnings overlay
   - Online/Offline toggle
   - AI hotspot insights

## 📊 Firestore Structure

### Users Collection
```javascript
users/{userId}
  - role: "student" | "vendor" | "rider"
  - email: string
  - createdAt: timestamp
```

Create test users directly in Firestore Console:
1. Go to Firestore Database
2. Start collection: `users`
3. Document ID: [Firebase Auth UID]
4. Fields:
   - `role`: string → "student"
   - `email`: string → "test@mmust.ac.ke"
   - `createdAt`: number → 1704751200000

## 🎨 UI Customization

All colors are defined in `app/src/main/res/values/colors.xml`:
```xml
<color name="primary">#2BEE79</color>
<color name="background_dark">#102217</color>
```

Change these to customize the brand colors.

## 🗺️ Map Configuration

Default map center: MMUST Campus
- Latitude: 0.2827
- Longitude: 34.7519

Update in `StudentHomeActivity.java` and `RiderHomeActivity.java` if needed.

## 📝 Important Files to Configure

1. ✅ `app/google-services.json` - Firebase config
2. ✅ `app/src/main/res/values/strings.xml` - Web client ID
3. ⚠️ `app/build.gradle.kts` - Already configured
4. ⚠️ `AndroidManifest.xml` - Already configured

## 🚨 Known Issues

1. **Google Sign-In**: Requires SHA-1 certificate in Firebase
2. **Paystack**: Not yet integrated (coming soon)
3. **Real-time Updates**: Firestore listeners not implemented yet
4. **Image Assets**: Using placeholder icons (replace with actual assets)

## 🎯 Next Development Steps

1. Implement order placement flow
2. Add real-time order tracking
3. Integrate Paystack payment gateway
4. Build AI dispatch algorithm
5. Add push notifications
6. Create vendor menu management
7. Implement rider earnings tracker

## 💡 Pro Tips

- Use **Android Studio Emulator** with Google Play for testing Google Sign-In
- Set up **Firestore indexes** as queries get complex
- Enable **Firebase Analytics** to track user behavior
- Use **Firebase Crashlytics** for error reporting
- Test on real devices for location accuracy

## 📞 Support

If you encounter issues:
1. Check Android Studio Logcat for error messages
2. Verify Firebase configuration is correct
3. Ensure all permissions are granted
4. Test on physical device if emulator fails

---
**Ready to build? Run:** `.\gradlew.bat assembleDebug`
