package g.sig.settings

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.icu.util.Currency
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import g.sig.core_data.shared_prefs.isMaterialYou
import g.sig.core_data.shared_prefs.savedCurrencyCode
import g.sig.core_navigation.DeepLinkUri
import g.sig.core_navigation.Routes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class SettingsViewModel(app: Application) : AndroidViewModel(app) {
    private val applicationContext: Application get() = getApplication()
    private val _savedCurrency =
        MutableStateFlow(Currency.getInstance(applicationContext.savedCurrencyCode))
    val savedCurrency = _savedCurrency.asStateFlow()

    val materialYou get() = applicationContext.isMaterialYou

    fun toggleMaterialYou(toggle: Boolean) {
        applicationContext.isMaterialYou = toggle

        // Restart App and add a deep link to settings
        val packageManager = applicationContext.packageManager
        val intent = packageManager.getLaunchIntentForPackage(applicationContext.packageName)
        val componentName = intent?.component
        val restartIntent =
            Intent.makeRestartActivityTask(componentName).apply {
                data = "$DeepLinkUri/${Routes.Settings.deepLink}".toUri()
            }

        val deepLinkPendingIntent =
            TaskStackBuilder.create(applicationContext).run {
                addNextIntentWithParentStack(restartIntent)
                getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
            }

        deepLinkPendingIntent?.send()
    }

    fun getCurrencies() = Currency.getAvailableCurrencies().sortedBy { it.currencyCode }

    fun setCurrency(currency: Currency) {
        applicationContext.savedCurrencyCode = currency.currencyCode
        _savedCurrency.value = currency
    }
}