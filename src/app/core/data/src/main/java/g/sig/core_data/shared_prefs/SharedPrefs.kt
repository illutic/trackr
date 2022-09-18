package g.sig.core_data.shared_prefs

import android.content.Context
import android.content.SharedPreferences
import android.icu.util.Currency
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import g.sig.core_data.Singletons
import java.util.*

private const val sharedPrefsFile: String = "sharedPrefs"

private val keyGenParameterSpec by lazy { MasterKeys.AES256_GCM_SPEC }
private val mainKeyAlias by lazy { MasterKeys.getOrCreate(keyGenParameterSpec) }

fun Context.createSharedPrefs(): SharedPreferences = EncryptedSharedPreferences.create(
    sharedPrefsFile,
    mainKeyAlias,
    applicationContext,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
)

fun Context.setPreferences(key: String, vararg values: Any?) {
    with(Singletons.getSharedPrefs(this).edit()) {
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

var Context.isMaterialYou
    get() = Singletons.getSharedPrefs(this).getBoolean(SharedKeys.MaterialYou, false)
    set(value) = setPreferences(SharedKeys.MaterialYou, value)

var Context.savedCurrencyCode : String?
    get() = Singletons.getSharedPrefs(this).getString(
        SharedKeys.Currency, Currency.getInstance(
            Locale.getDefault()
        ).currencyCode
    )
    set(value) = setPreferences(SharedKeys.Currency, value)
