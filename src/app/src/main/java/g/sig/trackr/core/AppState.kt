package g.sig.trackr.core

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import g.sig.core_data.shared_prefs.isMaterialYou
import g.sig.core_navigation.Navigable
import g.sig.core_navigation.TopLevelRoute
import g.sig.core_ui.utils.JankMetricDisposableEffect
import java.time.LocalDate

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()): AppState {
    NavigationTrackingSideEffect(navController = navController)
    return remember(navController) { AppState(navController) }
}

@Stable
class AppState(
    val navController: NavHostController
) {
    var currentMonth: LocalDate = LocalDate.now()
    @OptIn(ExperimentalMaterialApi::class)
    val addExpenseModalBottomSheetState = ModalBottomSheetState(ModalBottomSheetValue.Hidden)

    @OptIn(ExperimentalMaterialApi::class)
    val addCategoryModalBottomSheetState = ModalBottomSheetState(ModalBottomSheetValue.Hidden)
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

@Composable
private fun NavigationTrackingSideEffect(navController: NavHostController) {
    JankMetricDisposableEffect(navController) { metricsHolder ->
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            metricsHolder.state?.putState("Navigation", destination.route.toString())
        }

        navController.addOnDestinationChangedListener(listener)

        onDispose {
            navController.removeOnDestinationChangedListener(listener)
        }
    }
}