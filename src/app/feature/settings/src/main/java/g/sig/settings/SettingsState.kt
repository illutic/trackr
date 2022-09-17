package g.sig.settings

import android.icu.util.Currency
import g.sig.core_data.models.user.UserSettings

sealed interface SettingsState {
    object SettingsLoading : SettingsState
    data class SettingsSuccess(
        val isMaterialYouEnabled: Boolean,
        val currencies: List<Currency>,
        val userSettings: UserSettings
    ) : SettingsState

    data class SettingsError(val errorMessage: String) : SettingsState
}