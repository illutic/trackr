package g.sig.settings

sealed interface SettingsState {
    object SettingsLoading : SettingsState
    data class SettingsSuccess(val isMaterialYouEnabled: Boolean) : SettingsState
    data class SettingsError(val errorMessage: String) : SettingsState
}