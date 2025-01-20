package dk.dtu.weatherapp.firebase

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import java.util.UUID

fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    return EncryptedSharedPreferences.create(
        "secure_app_userid",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
}

fun generateAndSaveUserId(context: Context) {
    val userId = UUID.randomUUID().toString()
    val sharedPreferences = getEncryptedSharedPreferences(context)

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

    return sharedPreferences.getString("user_id", null)!!
}