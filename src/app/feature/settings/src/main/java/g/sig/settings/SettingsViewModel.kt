package g.sig.settings

import android.app.Application
import android.app.PendingIntent
import android.content.Intent
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import g.sig.core_data.shared_prefs.SharedKeys
import g.sig.core_data.shared_prefs.setPreferences
import g.sig.core_data.shared_prefs.sharedPrefs
import g.sig.core_navigation.DeepLinkUri
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(app: Application) : AndroidViewModel(app) {
    private val applicationContext: Application get() = getApplication()
    private val _settingsState = MutableStateFlow<SettingsState>(SettingsState.SettingsLoading)
    val settingsState: StateFlow<SettingsState> = _settingsState
    private val materialYou
        get() = applicationContext.sharedPrefs().getBoolean(SharedKeys.MaterialYou, false)

    init {
        viewModelScope.launch {
            delay(500)
            _settingsState.value = SettingsState.SettingsSuccess(materialYou)
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
}