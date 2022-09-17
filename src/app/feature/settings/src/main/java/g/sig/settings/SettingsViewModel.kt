package g.sig.settings

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import android.icu.util.Currency
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import g.sig.core_data.Response
import g.sig.core_data.models.user.UserSettings
import g.sig.core_data.repositories.UserRepo
import g.sig.core_data.shared_prefs.SharedKeys
import g.sig.core_data.shared_prefs.setPreferences
import g.sig.core_data.shared_prefs.sharedPrefs
import g.sig.core_navigation.DeepLinkUri
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

class SettingsViewModel(app: Application) : AndroidViewModel(app) {
    private val applicationContext: Application get() = getApplication()
    private val userRepo: UserRepo = UserRepo(applicationContext)

    private val _settingsState = MutableStateFlow<SettingsState>(SettingsState.SettingsLoading)
    val settingsState: StateFlow<SettingsState> = _settingsState
    private val materialYou
        get() = applicationContext.sharedPrefs().getBoolean(SharedKeys.MaterialYou, false)
    private val userId
        get() = applicationContext.sharedPrefs().getInt(SharedKeys.UserId, SharedKeys.NoUserId)

    init {
        viewModelScope.launch {
            userRepo.getCurrencies()
                .combine(userRepo.getUserSettings(userId)) { currencyResponse: Response<List<Currency>>, userSettingsResponse: Response<UserSettings> ->
                    if (currencyResponse is Response.Success && userSettingsResponse is Response.Success) {
                        SettingsState.SettingsSuccess(
                            isMaterialYouEnabled = materialYou,
                            currencies = currencyResponse.data,
                            userSettings = userSettingsResponse.data
                        )
                    } else if (currencyResponse is Response.Error) {
                        SettingsState.SettingsError(currencyResponse.errorMessage ?: "")
                    } else if (userSettingsResponse is Response.Error) {
                        SettingsState.SettingsError(userSettingsResponse.errorMessage ?: "")
                    } else {
                        SettingsState.SettingsLoading
                    }
                }.collectLatest {
                    _settingsState.value = it
                }
        }
    }

    fun toggleMaterialYou(toggle: Boolean) {
        applicationContext.setPreferences(SharedKeys.MaterialYou, toggle)

        // Restart App and add a deep link to settings
        val packageManager = applicationContext.packageManager
        val intent = packageManager.getLaunchIntentForPackage(applicationContext.packageName)
        val componentName = intent?.component
        val restartIntent =
            Intent.makeRestartActivityTask(componentName).apply {
                data = "$DeepLinkUri/settings".toUri()
            }

        val deepLinkPendingIntent =
            TaskStackBuilder.create(applicationContext).run {
                addNextIntentWithParentStack(restartIntent)
                getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
            }

        deepLinkPendingIntent?.send()
    }

    fun setUserCurrency(currency: Currency) {

    }
}