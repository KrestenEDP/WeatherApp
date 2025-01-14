package dk.dtu.weatherapp.Firebase

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.util.UUID

fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
    // Create or get the master key for encryption
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    // Create and return the EncryptedSharedPreferences instance
    return EncryptedSharedPreferences.create(
        "secure_app_userid",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,  // Key encryption scheme
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM  // Value encryption scheme
    )
}

fun generateAndSaveUserId(context: Context) {
    val userId = UUID.randomUUID().toString()  // Generating a unique UUID as the user ID
    val sharedPreferences = getEncryptedSharedPreferences(context)

    // Use the SharedPreferences editor to save the user ID securely
    with(sharedPreferences.edit()) {
        putString("user_id", userId)
        apply()
    }
}

fun getUserId(context: Context): String {
    val sharedPreferences = getEncryptedSharedPreferences(context)

    if (!sharedPreferences.contains("user_id")) {
        generateAndSaveUserId(context)
    }

    // Retrieve the user ID securely
    return sharedPreferences.getString("user_id", null)!!
}