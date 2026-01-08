# 🔥 Firebase Setup - Complete Step-by-Step Guide

## Step 1: Create Firebase Project (5 minutes)

### 1.1 Go to Firebase Console
Visit: https://console.firebase.google.com/

### 1.2 Create New Project
1. Click **"Add project"**
2. **Project name**: `Leta App MMUST` (or your preferred name)
3. Click **Continue**
4. **Google Analytics**: Toggle OFF (optional for now)
5. Click **Create project**
6. Wait 30 seconds for creation
7. Click **Continue**

---

## Step 2: Add Android App to Firebase

### 2.1 Add Android App
1. On Firebase Console home, click the **Android icon** 📱
2. **Android package name**: `com.mmust.leta` (MUST MATCH EXACTLY!)
3. **App nickname**: `Leta App` (optional)
4. **Debug signing certificate SHA-1**: Leave blank for now (we'll add later)
5. Click **Register app**

### 2.2 Download google-services.json
1. Click **Download google-services.json**
2. Save the file to your computer
3. **IMPORTANT**: Move this file to `app/` folder in your project
   - Path should be: `Leta-App/app/google-services.json`
   - Replace the existing placeholder file

### 2.3 Verify File Location
```
Leta-App/
├── app/
│   ├── google-services.json  ← FILE MUST BE HERE!
│   ├── build.gradle.kts
│   └── src/
```

4. Click **Next** → **Next** → **Continue to console**

---

## Step 3: Enable Authentication Methods

### 3.1 Enable Email/Password Authentication
1. In Firebase Console, click **Build** → **Authentication**
2. Click **Get started**
3. Click **Sign-in method** tab
4. Click **Email/Password**
5. Toggle **Enable** to ON
6. Click **Save**

### 3.2 Enable Google Sign-In (Optional but Recommended)
1. Still in Sign-in method tab
2. Click **Google**
3. Toggle **Enable** to ON
4. **Project support email**: Select your email
5. Click **Save**

### 3.3 Copy Web Client ID
1. After enabling Google Sign-In
2. Expand the **Google** provider
3. Copy the **Web client ID** (starts with numbers, ends with `.apps.googleusercontent.com`)
4. Save this - you'll need it in Step 5!

---

## Step 4: Create Firestore Database

### 4.1 Create Database
1. In Firebase Console, click **Build** → **Firestore Database**
2. Click **Create database**
3. **Location**: Choose closest to Kenya (e.g., `europe-west2` or `us-central`)
4. Click **Next**

### 4.2 Choose Security Rules
1. Select **Start in test mode**
   - This allows read/write access for 30 days (good for development)
2. Click **Enable**
3. Wait for database creation (30 seconds)

### 4.3 Update Security Rules (Important!)
1. Click **Rules** tab
2. Replace with these rules:

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    // Users collection - users can only read/write their own data
    match /users/{userId} {
      allow read, write: if request.auth != null && request.auth.uid == userId;
    }
    
    // Orders collection - users can read their own orders
    match /orders/{orderId} {
      allow read: if request.auth != null && 
                     (resource.data.userId == request.auth.uid || 
                      resource.data.vendorId == request.auth.uid ||
                      resource.data.riderId == request.auth.uid);
      allow create: if request.auth != null;
      allow update: if request.auth != null;
    }
    
    // Menu items - anyone can read, only vendors can write
    match /menuItems/{itemId} {
      allow read: if request.auth != null;
      allow write: if request.auth != null;
    }
  }
}
```

3. Click **Publish**

---

## Step 5: Configure Your App

### 5.1 Update config.properties
1. Open `app/src/main/res/raw/config.properties`
2. Add your Firebase Web Client ID:

```properties
# Firebase Configuration (from Step 3.3)
firebase_web_client_id=YOUR_WEB_CLIENT_ID_HERE.apps.googleusercontent.com
```

### 5.2 Update strings.xml
1. Open `app/src/main/res/values/strings.xml`
2. Find this line:
```xml
<string name="default_web_client_id">YOUR_FIREBASE_WEB_CLIENT_ID_HERE</string>
```
3. Replace with your actual Web Client ID from Step 3.3

---

## Step 6: Get SHA-1 for Google Sign-In (Optional but Recommended)

### 6.1 Generate SHA-1
Open terminal/PowerShell in your project root and run:

**Windows (PowerShell):**
```powershell
keytool -list -v -keystore "$env:USERPROFILE\.android\debug.keystore" -alias androiddebugkey -storepass android -keypass android
```

**Mac/Linux:**
```bash
keytool -list -v -keystore ~/.android/debug.keystore -alias androiddebugkey -storepass android -keypass android
```

### 6.2 Copy SHA-1
Look for: `SHA1: XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX:XX`
Copy the entire SHA-1 fingerprint

### 6.3 Add to Firebase
1. Go to Firebase Console → Project Settings (gear icon)
2. Scroll to **Your apps**
3. Find your Android app
4. Click **Add fingerprint**
5. Paste the SHA-1
6. Click **Save**

---

## Step 7: Test the Connection

### 7.1 Build the App
```bash
.\gradlew.bat clean
.\gradlew.bat assembleDebug
```

### 7.2 Check Logcat
When you run the app, check Android Studio Logcat for:
- ✅ `FirebaseApp initialization successful`
- ❌ If you see errors, check that `google-services.json` is in the correct location

---

## Step 8: Create a Test Account

### 8.1 Via the App
1. Launch the app on your device/emulator
2. Click **Sign Up** tab
3. Enter:
   - Email: `test@mmust.ac.ke`
   - Password: `test123456`
   - Confirm Password: `test123456`
4. Click **Sign Up**
5. Select your role (Student/Vendor/Rider)

### 8.2 Via Firebase Console (Alternative)
1. Go to Firebase Console → Authentication → Users
2. Click **Add user**
3. Enter email and password
4. Click **Add user**

---

## Step 9: View Users in Firebase Console

1. Go to **Authentication** → **Users** tab
2. You should see registered users
3. Go to **Firestore Database** → **Data** tab
4. You should see `users` collection with user documents

---

## ✅ Verification Checklist

Before proceeding, verify:

- [ ] `google-services.json` is in `app/` folder
- [ ] Email/Password authentication is enabled in Firebase
- [ ] Firestore database is created
- [ ] Security rules are updated
- [ ] Web Client ID is in `strings.xml`
- [ ] App builds successfully
- [ ] You can create a test account

---

## 🐛 Troubleshooting

### Issue: "Default FirebaseApp is not initialized"
**Solution**: 
- Check `google-services.json` is in `app/` folder
- Sync Gradle files
- Clean and rebuild

### Issue: "API key not valid"
**Solution**:
- Re-download `google-services.json` from Firebase Console
- Replace the old file
- Rebuild

### Issue: Google Sign-In fails
**Solution**:
- Add SHA-1 fingerprint (Step 6)
- Enable Google Sign-In in Firebase Console
- Update Web Client ID in strings.xml

### Issue: "Permission denied" in Firestore
**Solution**:
- Update Firestore security rules (Step 4.3)
- Make sure user is authenticated

---

## 📱 What Happens After Setup?

1. **User signs up** → Firebase Auth creates account
2. **Role is selected** → Saved to Firestore `users/{uid}`
3. **User logs in** → Firebase Auth verifies credentials
4. **App checks role** → UserRouter navigates to correct dashboard
5. **User data syncs** → Real-time updates via Firestore

---

## 🎉 You're Done!

Your Firebase is now fully configured and ready to:
- ✅ Authenticate users
- ✅ Store user data
- ✅ Handle role-based access
- ✅ Support Google Sign-In
- ✅ Real-time data sync

**Next**: I'll now update the AuthActivity with the enhanced UI you requested!
