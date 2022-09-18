package g.sig.trackr.core

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import g.sig.core_data.shared_prefs.isMaterialYou
import g.sig.core_navigation.Navigable
import g.sig.core_navigation.TopLevelRoute

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()): AppState =
    remember(navController) { AppState(navController) }

@Stable
class AppState(
    val navController: NavHostController
) {
    val useMaterialYou: Boolean
        @Composable get() = LocalContext.current.isMaterialYou
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    /**
     * Used in the bottom bar
     */
    val topLevelDestinations =
        listOf(TopLevelRoute.Overview(), TopLevelRoute.Transactions(), TopLevelRoute.Settings())

    fun navigate(route: Navigable) {
        navController.navigate(route.destination)
    }
}