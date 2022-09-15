package g.sig.settings

import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import g.sig.core_navigation.Routes

fun NavGraphBuilder.settingsGraph(deepLinks: List<NavDeepLink> = listOf()) {
    composable(Routes.Settings.destination, deepLinks = deepLinks) {
        SettingsRoute()
    }
}