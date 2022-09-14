package g.sig.core_data.shared_prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

private const val sharedPrefsFile: String = "sharedPrefs"

private val keyGenParameterSpec by lazy { MasterKeys.AES256_GCM_SPEC }
private val mainKeyAlias by lazy { MasterKeys.getOrCreate(keyGenParameterSpec) }

fun Context.sharedPrefs(): SharedPreferences = EncryptedSharedPreferences.create(
    sharedPrefsFile,
    mainKeyAlias,
    applicationContext,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
)

fun Context.setPreferences(key: String, vararg values: Any) {
    with(sharedPrefs().edit()) {
        values.forEach { value ->
            when (value) {
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is Long -> putLong(key, value)
                is Set<*> -> putStringSet(key, values.filterIsInstance<String>().toSet())
            }
        }
        apply()
    }
}
