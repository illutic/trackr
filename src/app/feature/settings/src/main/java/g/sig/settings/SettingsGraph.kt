package g.sig.settings

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import g.sig.core_navigation.Routes

fun NavGraphBuilder.settingsGraph() {
    composable(Routes.Settings.destination) {
        SettingsScreen()
    }
}