package com.mmust.leta.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Supabase Client - Handles all Supabase API calls
 */
public class SupabaseClient {
    
    private static SupabaseClient instance;
    private final String supabaseUrl;
    private final String supabaseKey;
    private final OkHttpClient client;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    
    private SupabaseClient(Context context) {
        ConfigManager config = ConfigManager.getInstance(context);
        this.supabaseUrl = config.getSupabaseUrl();
        this.supabaseKey = config.getSupabaseAnonKey();
        this.client = new OkHttpClient();
    }
    
    public static synchronized SupabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new SupabaseClient(context.getApplicationContext());
        }
        return instance;
    }
    
    // Sign Up
    public void signUp(String email, String password, SupabaseCallback callback) {
        try {
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", password);
            
            RequestBody body = RequestBody.create(json.toString(), JSON);
            Request request = new Request.Builder()
                    .url(supabaseUrl + "/auth/v1/signup")
                    .addHeader("apikey", supabaseKey)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
            
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError(e.getMessage());
                }
                
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseBody = response.body().string();
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonResponse = new JSONObject(responseBody);
                            String userId = jsonResponse.optString("id", "");
                            callback.onSuccess(userId, responseBody);
                        } catch (JSONException e) {
                            callback.onError("Invalid response format");
                        }
                    } else {
                        callback.onError(parseError(responseBody));
                    }
                }
            });
        } catch (JSONException e) {
            callback.onError("Failed to create request: " + e.getMessage());
        }
    }
    
    // Sign In
    public void signIn(String email, String password, SupabaseCallback callback) {
        try {
            JSONObject json = new JSONObject();
            json.put("email", email);
            json.put("password", password);
            
            RequestBody body = RequestBody.create(json.toString(), JSON);
            Request request = new Request.Builder()
                    .url(supabaseUrl + "/auth/v1/token?grant_type=password")
                    .addHeader("apikey", supabaseKey)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
            
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError(e.getMessage());
                }
                
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseBody = response.body().string();
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonResponse = new JSONObject(responseBody);
                            JSONObject user = jsonResponse.optJSONObject("user");
                            String userId = user != null ? user.optString("id", "") : "";
                            String accessToken = jsonResponse.optString("access_token", "");
                            
                            // Store token
                            callback.onSuccess(userId, accessToken);
                        } catch (JSONException e) {
                            callback.onError("Invalid response format");
                        }
                    } else {
                        callback.onError(parseError(responseBody));
                    }
                }
            });
        } catch (JSONException e) {
            callback.onError("Failed to create request: " + e.getMessage());
        }
    }
    
    // Get User Data
    public void getUserData(String userId, String accessToken, DataCallback callback) {
        Request request = new Request.Builder()
                .url(supabaseUrl + "/rest/v1/users?id=eq." + userId)
                .addHeader("apikey", supabaseKey)
                .addHeader("Authorization", "Bearer " + accessToken)
                .get()
                .build();
        
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onError(e.getMessage());
            }
            
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                    callback.onSuccess(responseBody);
                } else {
                    callback.onError(parseError(responseBody));
                }
            }
        });
    }
    
    // Create User Profile
    public void createUserProfile(String userId, String email, String role, String accessToken, DataCallback callback) {
        try {
            JSONObject json = new JSONObject();
            json.put("id", userId);
            json.put("email", email);
            json.put("role", role);
            json.put("created_at", System.currentTimeMillis());
            
            RequestBody body = RequestBody.create(json.toString(), JSON);
            Request request = new Request.Builder()
                    .url(supabaseUrl + "/rest/v1/users")
                    .addHeader("apikey", supabaseKey)
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Prefer", "return=representation")
                    .post(body)
                    .build();
            
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError(e.getMessage());
                }
                
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseBody = response.body().string();
                    if (response.isSuccessful()) {
                        callback.onSuccess(responseBody);
                    } else {
                        callback.onError(parseError(responseBody));
                    }
                }
            });
        } catch (JSONException e) {
            callback.onError("Failed to create request: " + e.getMessage());
        }
    }
    
    // Password Reset
    public void resetPassword(String email, SupabaseCallback callback) {
        try {
            JSONObject json = new JSONObject();
            json.put("email", email);
            
            RequestBody body = RequestBody.create(json.toString(), JSON);
            Request request = new Request.Builder()
                    .url(supabaseUrl + "/auth/v1/recover")
                    .addHeader("apikey", supabaseKey)
                    .addHeader("Content-Type", "application/json")
                    .post(body)
                    .build();
            
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    callback.onError(e.getMessage());
                }
                
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        callback.onSuccess("", "Password reset email sent");
                    } else {
                        callback.onError(parseError(response.body().string()));
                    }
                }
            });
        } catch (JSONException e) {
            callback.onError("Failed to create request: " + e.getMessage());
        }
    }
    
    private String parseError(String responseBody) {
        try {
            JSONObject error = new JSONObject(responseBody);
            return error.optString("error_description", 
                   error.optString("message", "An error occurred"));
        } catch (JSONException e) {
            return "An error occurred";
        }
    }
    
    public interface SupabaseCallback {
        void onSuccess(String userId, String data);
        void onError(String error);
    }
    
    public interface DataCallback {
        void onSuccess(String data);
        void onError(String error);
    }
}
