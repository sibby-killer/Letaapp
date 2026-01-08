# 🔐 Leta App - Configuration Guide

## NO HARDCODED API KEYS! ✅

All API keys and configuration are loaded from `app/src/main/res/raw/config.properties`

## Quick Setup

### 1. Open Configuration File
Navigate to: `app/src/main/res/raw/config.properties`

### 2. Add Your Paystack Keys

```properties
# Get these from: https://dashboard.paystack.com/#/settings/developers
paystack_public_key=pk_test_xxxxxxxxxxxxxxxxxxxxx
paystack_secret_key=sk_test_xxxxxxxxxxxxxxxxxxxxx
```

**Where to find Paystack keys:**
1. Go to https://dashboard.paystack.com/
2. Sign up or login
3. Navigate to Settings → API Keys & Webhooks
4. Copy your Public Key (starts with `pk_test_` or `pk_live_`)
5. Copy your Secret Key (starts with `sk_test_` or `sk_live_`)

### 3. Add Your Firebase API Key (Optional)

```properties
firebase_api_key=AIzaSyXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
```

**Where to find Firebase API key:**
1. Go to Firebase Console: https://console.firebase.google.com/
2. Select your project
3. Go to Project Settings → General
4. Scroll to "Web API Key"
5. Copy the key

### 4. Customize Settings (Optional)

```properties
# Change delivery fees
standard_delivery_fee=50
urgent_delivery_fee=100

# Change commission rates
rider_commission_percent=20
vendor_commission_percent=5

# Change referral discount
referral_discount_percent=10
```

## How to Use in Code

### Example: Get Paystack Key

```java
// In any Activity or Fragment
ConfigManager config = ConfigManager.getInstance(this);
String paystackPublicKey = config.getPaystackPublicKey();

// Initialize Paystack
PaystackSdk.setPublicKey(paystackPublicKey);
```

### Example: Get Delivery Fee

```java
ConfigManager config = ConfigManager.getInstance(this);
int standardFee = config.getStandardDeliveryFee();
int urgentFee = config.getUrgentDeliveryFee();
```

### Example: Get MMUST Coordinates

```java
ConfigManager config = ConfigManager.getInstance(this);
double lat = config.getMmustLatitude();
double lon = config.getMmustLongitude();
```

## Security Best Practices

### ✅ DO:
- Keep `config.properties` in `.gitignore` for production
- Use test keys during development
- Switch to live keys only for production builds
- Rotate keys regularly

### ❌ DON'T:
- Hardcode API keys in Java files
- Commit production keys to Git
- Share secret keys publicly
- Use live keys in debug builds

## Configuration File Structure

```
app/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/mmust/leta/
│       │       └── utils/
│       │           └── ConfigManager.java  ← Loads config
│       └── res/
│           └── raw/
│               └── config.properties       ← YOUR API KEYS HERE
```

## Testing Configuration

Add this to any Activity to test:

```java
ConfigManager config = ConfigManager.getInstance(this);

Log.d("Config Test", "Paystack Public Key: " + config.getPaystackPublicKey());
Log.d("Config Test", "Standard Delivery Fee: " + config.getStandardDeliveryFee());
Log.d("Config Test", "MMUST Lat: " + config.getMmustLatitude());
```

## Environment-Specific Configuration

For different environments (dev, staging, production), you can create:
- `config_dev.properties`
- `config_staging.properties`
- `config_production.properties`

Then use build variants to select the appropriate file.

## Support

If you have issues:
1. Check file path: `app/src/main/res/raw/config.properties`
2. Verify file format (no spaces around `=`)
3. Ensure keys are valid and active
4. Check Android Studio console for errors

---

**Remember: NEVER commit your actual API keys to version control!**
