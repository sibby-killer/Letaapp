# 🚀 Leta App - Deployment Checklist

## Pre-Deployment Configuration

### ✅ Code Migration Complete
- [x] All Stitch UI layouts converted to Android XML
- [x] 7 Java activities created with ViewBinding
- [x] UserRouter logic implemented
- [x] Firebase integration complete
- [x] OSMDroid maps configured
- [x] All permissions added to manifest
- [x] Bottom navigation menus created
- [x] Brand colors applied
- [x] Material3 Dark theme set

### 🔧 Required Configuration (BEFORE FIRST RUN)

#### 1. Firebase Setup
- [ ] Create Firebase project at https://console.firebase.google.com/
- [ ] Add Android app with package: `com.mmust.leta`
- [ ] Download **google-services.json** 
- [ ] Replace placeholder file at `app/google-services.json`
- [ ] Enable Email/Password authentication
- [ ] Enable Google Sign-In authentication
- [ ] Create Firestore database in test mode
- [ ] Copy Web client ID from Firebase Console

#### 2. Update Configuration Files
- [ ] Replace `YOUR_FIREBASE_WEB_CLIENT_ID_HERE` in `app/src/main/res/values/strings.xml`
- [ ] Verify `google-services.json` is in correct location
- [ ] Confirm package name matches in all files: `com.mmust.leta`

#### 3. Google Sign-In Configuration (Optional but Recommended)
```bash
# Get SHA-1 fingerprint from debug keystore
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```
- [ ] Copy SHA-1 fingerprint
- [ ] Add to Firebase Console → Project Settings → SHA certificate fingerprints
- [ ] For release builds, add release keystore SHA-1 as well

### 🏗️ Build & Test

#### Local Development Build
```bash
# Windows
.\gradlew.bat clean assembleDebug

# Linux/Mac
./gradlew clean assembleDebug
```

- [ ] Build completes without errors
- [ ] APK generated at: `app/build/outputs/apk/debug/app-debug.apk`

#### Install on Device
```bash
# Windows
.\gradlew.bat installDebug

# Linux/Mac
./gradlew installDebug
```

- [ ] App installs successfully
- [ ] App icon appears in launcher
- [ ] No crash on launch

### 🧪 Testing Checklist

#### Splash Screen
- [ ] Displays for ~3 seconds
- [ ] Progress bar animates
- [ ] "Checking authentication..." text visible
- [ ] Transitions to Auth screen (new user) or Dashboard (returning user)

#### Authentication Flow
- [ ] Email input accepts text
- [ ] Password input toggles visibility
- [ ] "Forgot Password?" link is clickable
- [ ] Login button validates empty fields
- [ ] Sign Up tab switches UI context
- [ ] Google Sign-In button launches picker
- [ ] Successful login routes to role selection (new) or dashboard (existing)
- [ ] Firebase errors display user-friendly messages

#### Role Selection
- [ ] Back button returns to auth
- [ ] Progress indicator shows step 1 of 4
- [ ] Student card is clickable
- [ ] Vendor card is clickable
- [ ] Radio buttons toggle correctly
- [ ] Continue button saves role to Firestore
- [ ] Navigation to appropriate dashboard works

#### Student Dashboard
- [ ] Map loads and displays
- [ ] Map is centered on MMUST (0.2827, 34.7519)
- [ ] Zoom controls work
- [ ] My Location FAB is visible
- [ ] Bottom navigation is visible
- [ ] Tapping My Location centers on user (if permission granted)
- [ ] Location permission prompt appears
- [ ] Search bar is visible (placeholder)

#### Vendor Dashboard
- [ ] Stats display (Today's Sales, Pending, Completed)
- [ ] Tab layout shows: Active Orders, Menu, History
- [ ] Tabs are clickable and switch views
- [ ] Notifications button is visible
- [ ] Bottom navigation works
- [ ] RecyclerView area is visible (empty for now)

#### Rider Dashboard
- [ ] Header shows rider profile
- [ ] Online/Offline toggle works
- [ ] Map displays correctly
- [ ] Earnings stats overlay visible
- [ ] "Waiting for orders..." panel visible
- [ ] AI insight card displays
- [ ] "View Hotspots Map" button clickable
- [ ] Bottom navigation works
- [ ] Location permission requested

### 🔒 Security Checklist

#### Firebase Security
- [ ] Firestore rules set to test mode for development
- [ ] Plan to update rules before production
- [ ] Authentication methods enabled only as needed
- [ ] API keys restricted in Firebase Console (production)

#### Android Permissions
- [ ] Location permission uses runtime request (API 23+)
- [ ] Internet permission declared
- [ ] Network state permission declared
- [ ] Storage permission maxSdkVersion set to 32

### 📱 Device Testing

#### Minimum Requirements
- [ ] Android 4.1+ (API 16) devices tested
- [ ] Works on emulator with Google Play Services
- [ ] Works on physical device

#### Screen Sizes
- [ ] Phone (normal) - 5-6 inches
- [ ] Phone (large) - 6+ inches
- [ ] Tablet (optional)

#### Android Versions to Test
- [ ] Android 4.1 (API 16) - minimum
- [ ] Android 10 (API 29) - common
- [ ] Android 13+ (API 33+) - latest

### 🐛 Known Issues to Verify

#### Expected Behaviors
- [ ] Google Sign-In requires SHA-1 certificate (will fail without it)
- [ ] Maps require internet connection
- [ ] Location features require permission grant
- [ ] Placeholder google-services.json will cause Firebase errors

#### Fixed Issues
- [x] PreferenceManager import corrected to androidx
- [x] compileSdk syntax error fixed
- [x] ViewBinding enabled
- [x] All activities registered in manifest

### 📊 Firestore Data Structure

#### Test User Setup
Create test user manually in Firestore Console:

**Collection**: `users`  
**Document ID**: [Firebase Auth UID]  
**Fields**:
```json
{
  "role": "student",
  "email": "test@mmust.ac.ke",
  "createdAt": 1704751200000
}
```

- [ ] Test user created in Firestore
- [ ] User can login and sees correct dashboard
- [ ] Role routing works correctly

### 🔄 Continuous Integration (Optional)

#### GitHub Actions / CI Setup
- [ ] Set up automated builds
- [ ] Configure Firebase Test Lab
- [ ] Add lint checks
- [ ] Add unit tests (future)

### 📝 Documentation

#### Completed Documentation
- [x] README.md - Project overview
- [x] SETUP_GUIDE.md - Step-by-step setup
- [x] PROJECT_SUMMARY.md - Technical summary
- [x] DEPLOYMENT_CHECKLIST.md - This file

#### Code Documentation
- [ ] JavaDoc comments on public methods (future)
- [ ] Inline comments for complex logic (future)
- [ ] API documentation (future)

### 🎯 Production Readiness (Future)

#### Before Production Launch
- [ ] Update Firestore security rules
- [ ] Restrict Firebase API keys
- [ ] Generate release signing key
- [ ] Enable ProGuard/R8 obfuscation
- [ ] Remove all debug logs
- [ ] Test on production Firebase project
- [ ] Set up Crashlytics
- [ ] Set up Analytics
- [ ] Configure Firebase Performance Monitoring
- [ ] Update google-services.json to production
- [ ] Test payment flow with real transactions
- [ ] Legal: Privacy policy, Terms of service
- [ ] App Store: Screenshots, descriptions, ratings

### ✅ Final Sign-Off

#### Development Complete
- [x] All VIBE framework requirements met
- [x] All 14 tasks completed
- [x] Code follows Java best practices
- [x] ViewBinding used throughout
- [x] No Kotlin code (as required)
- [x] No Google Maps (OSMDroid only)
- [x] Firebase integrated
- [x] Three role dashboards implemented

#### Ready for Configuration
- [ ] Firebase project created
- [ ] google-services.json replaced
- [ ] Web client ID updated
- [ ] First test build successful
- [ ] First test user created
- [ ] End-to-end flow tested

---

## 🚦 Status: READY FOR FIREBASE CONFIGURATION

**Next Action**: Follow SETUP_GUIDE.md to configure Firebase

**Estimated Setup Time**: 15-30 minutes

**Build Command**: `.\gradlew.bat assembleDebug`

---

**Migration Completed**: ✅ 100%  
**Build Status**: ⚠️ Pending Firebase configuration  
**Deployment Status**: 🟡 Ready for testing after Firebase setup

---

Last Updated: January 8, 2026  
Framework: VIBE (Visuals, Interface, Backend, Exclusions)  
Platform: Android Native (Java)  
Target Audience: MMUST Campus Community
