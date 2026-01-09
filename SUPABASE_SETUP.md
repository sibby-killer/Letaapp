# 🚀 Supabase Setup Guide - Leta App

## Why Supabase?
- ✅ **100% FREE** - No credit card needed
- ✅ **500MB database** - Perfect for campus app
- ✅ **50K monthly active users** - More than enough
- ✅ **Real-time subscriptions** - Live updates
- ✅ **Built-in authentication** - Email, social logins
- ✅ **PostgreSQL** - More powerful than Firestore

---

## Step 1: Create Supabase Project (2 Minutes)

### 1.1 Sign Up
1. Go to: https://supabase.com/
2. Click **"Start your project"**
3. Sign up with **GitHub** (recommended) or email

### 1.2 Create New Project
1. Click **"New Project"**
2. Fill in details:
   - **Name**: `Leta App`
   - **Database Password**: Create a strong password (save it!)
   - **Region**: Choose closest to Kenya (e.g., `Singapore (Southeast Asia)`)
3. Click **"Create new project"**
4. ⏳ Wait 2-3 minutes for project to be ready

---

## Step 2: Get Your Credentials (30 Seconds)

### 2.1 Copy Project URL and API Key
1. Once project is ready, go to **Settings** (gear icon, bottom left)
2. Click **"API"** in the sidebar
3. You'll see:
   - **Project URL**: `https://xxxxxxxxxxxxx.supabase.co`
   - **anon public key**: `eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...`

### 2.2 Update Your App Config
1. Open `app/src/main/res/raw/config.properties`
2. Replace these values:

```properties
# Supabase Configuration
supabase_url=https://xxxxxxxxxxxxx.supabase.co
supabase_anon_key=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

✅ **IMPORTANT**: Copy the ENTIRE anon key (it's very long!)

---

## Step 3: Create Users Table (1 Minute)

### 3.1 Go to SQL Editor
1. In Supabase Dashboard, click **"SQL Editor"** (left sidebar)
2. Click **"New query"**

### 3.2 Run This SQL
Copy and paste this entire SQL script:

```sql
-- Create users table
CREATE TABLE users (
  id UUID PRIMARY KEY REFERENCES auth.users(id) ON DELETE CASCADE,
  email TEXT NOT NULL,
  role TEXT NOT NULL CHECK (role IN ('student', 'vendor', 'rider')),
  name TEXT,
  phone TEXT,
  photo_url TEXT,
  rating FLOAT DEFAULT 0,
  total_orders INTEGER DEFAULT 0,
  created_at BIGINT NOT NULL,
  
  -- Rider specific fields
  rider_status TEXT CHECK (rider_status IN ('pending_approval', 'active', 'inactive')),
  total_earnings FLOAT DEFAULT 0,
  
  -- Vendor specific fields
  vendor_name TEXT,
  vendor_description TEXT,
  total_revenue FLOAT DEFAULT 0
);

-- Enable Row Level Security
ALTER TABLE users ENABLE ROW LEVEL SECURITY;

-- Policy: Users can read their own data
CREATE POLICY "Users can read own data"
  ON users
  FOR SELECT
  USING (auth.uid() = id);

-- Policy: Users can insert their own data
CREATE POLICY "Users can insert own data"
  ON users
  FOR INSERT
  WITH CHECK (auth.uid() = id);

-- Policy: Users can update their own data
CREATE POLICY "Users can update own data"
  ON users
  FOR UPDATE
  USING (auth.uid() = id);

-- Create orders table
CREATE TABLE orders (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  user_id UUID REFERENCES users(id) ON DELETE CASCADE,
  vendor_id UUID REFERENCES users(id) ON DELETE SET NULL,
  rider_id UUID REFERENCES users(id) ON DELETE SET NULL,
  status TEXT NOT NULL DEFAULT 'pending' CHECK (status IN ('pending', 'preparing', 'ready', 'picking_up', 'delivering', 'completed', 'cancelled')),
  subtotal FLOAT NOT NULL,
  delivery_fee FLOAT NOT NULL,
  discount FLOAT DEFAULT 0,
  total FLOAT NOT NULL,
  delivery_address TEXT,
  building_details TEXT,
  payment_method TEXT,
  payment_status TEXT,
  created_at BIGINT NOT NULL,
  updated_at BIGINT NOT NULL
);

-- Enable RLS for orders
ALTER TABLE orders ENABLE ROW LEVEL SECURITY;

-- Policy: Users can read their own orders
CREATE POLICY "Users can read own orders"
  ON orders
  FOR SELECT
  USING (
    auth.uid() = user_id OR 
    auth.uid() = vendor_id OR 
    auth.uid() = rider_id
  );

-- Policy: Users can create orders
CREATE POLICY "Users can create orders"
  ON orders
  FOR INSERT
  WITH CHECK (auth.uid() = user_id);

-- Policy: Users can update orders
CREATE POLICY "Users can update orders"
  ON orders
  FOR UPDATE
  USING (
    auth.uid() = user_id OR 
    auth.uid() = vendor_id OR 
    auth.uid() = rider_id
  );

-- Create menu items table
CREATE TABLE menu_items (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  vendor_id UUID REFERENCES users(id) ON DELETE CASCADE,
  name TEXT NOT NULL,
  description TEXT,
  price FLOAT NOT NULL,
  category TEXT CHECK (category IN ('snacks', 'drinks', 'meals')),
  image_url TEXT,
  available BOOLEAN DEFAULT true,
  preparation_time INTEGER DEFAULT 15,
  created_at BIGINT NOT NULL
);

-- Enable RLS for menu_items
ALTER TABLE menu_items ENABLE ROW LEVEL SECURITY;

-- Policy: Anyone can read menu items
CREATE POLICY "Anyone can read menu items"
  ON menu_items
  FOR SELECT
  USING (true);

-- Policy: Vendors can manage their own items
CREATE POLICY "Vendors can manage own items"
  ON menu_items
  FOR ALL
  USING (auth.uid() = vendor_id);
```

3. Click **"Run"** (or press `Ctrl+Enter`)
4. ✅ You should see "Success. No rows returned"

---

## Step 4: Enable Email Authentication (30 Seconds)

### 4.1 Configure Auth Settings
1. Go to **Authentication** → **Providers** (left sidebar)
2. Find **Email** provider
3. Make sure it's **enabled** ✅ (it should be by default)
4. Scroll down to **Email Templates**
5. (Optional) Customize the signup confirmation email

### 4.2 Disable Email Confirmation (For Testing)
1. Go to **Authentication** → **Settings**
2. Find **"Enable email confirmations"**
3. **Toggle OFF** for development (you can enable later)
4. Click **Save**

✅ Now users can sign up without confirming email (faster testing!)

---

## Step 5: Test Your Setup (Build & Run)

### 5.1 Sync Gradle
```bash
# In Android Studio: File → Sync Project with Gradle Files
# Or run:
.\gradlew.bat --refresh-dependencies
```

### 5.2 Build the App
```bash
.\gradlew.bat clean assembleDebug
```

### 5.3 Test Authentication
1. Launch the app
2. Click **Sign Up** tab
3. Enter:
   - Email: `test@mmust.ac.ke`
   - Password: `test123456`
   - Confirm Password: `test123456`
4. Click **SIGN UP**
5. ✅ Should create account and go to role selection!

### 5.4 Verify in Supabase
1. Go to **Authentication** → **Users** in Supabase Dashboard
2. You should see your test user!
3. Go to **Table Editor** → **users** table
4. After selecting a role, you should see the user record!

---

## ✅ Setup Complete!

Your app now uses Supabase for:
- ✅ User authentication (email/password)
- ✅ User profiles with roles
- ✅ Orders management
- ✅ Menu items storage
- ✅ Real-time updates (coming soon)

---

## 🎯 What You Get FREE

| Feature | Limit |
|---------|-------|
| **Database** | 500 MB storage |
| **Auth Users** | 50,000 monthly active users |
| **API Requests** | Unlimited |
| **Bandwidth** | 2 GB/month |
| **File Storage** | 1 GB storage |
| **Realtime** | 200 concurrent connections |

**Perfect for a campus app!** 🎓

---

## 🔐 Security Features

### Built-in Security
- ✅ Row Level Security (RLS) enabled
- ✅ Users can only access their own data
- ✅ JWT authentication
- ✅ API keys are safe (anon key is public)

### What's Protected
- Users can't see other users' data
- Vendors can't edit other vendors' menus
- Orders are only visible to involved parties
- Database access is restricted by policies

---

## 🐛 Troubleshooting

### Error: "Invalid API key"
- Check `config.properties` has correct `supabase_url` and `supabase_anon_key`
- Make sure you copied the entire anon key (it's very long!)

### Error: "Failed to create user profile"
- Make sure you ran the SQL script in Step 3
- Check the `users` table exists in Supabase Dashboard

### Error: "Network request failed"
- Check internet connection
- Verify `supabase_url` is correct (starts with `https://`)

### Can't see user in database
- Go to Supabase Dashboard → **Authentication** → **Users**
- Users should appear here after signup
- User profile in `users` table appears after role selection

---

## 🚀 Next Steps

1. ✅ **Test signup/login** - Create multiple test accounts
2. ✅ **Test role selection** - Try student, vendor roles
3. ✅ **Verify data** - Check Supabase dashboard for user records
4. 🎯 **Push to GitHub** - Save your working code!
5. 🎯 **Share your PRD** - I'll implement the backend logic!

---

## 📚 Useful Links

- **Supabase Dashboard**: https://app.supabase.com/
- **Supabase Docs**: https://supabase.com/docs
- **Supabase Auth Docs**: https://supabase.com/docs/guides/auth
- **SQL Reference**: https://supabase.com/docs/guides/database/tables

---

**🎉 Your app is now powered by Supabase - 100% FREE!**
