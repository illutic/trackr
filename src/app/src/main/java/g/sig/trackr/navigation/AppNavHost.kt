package g.sig.trackr.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navDeepLink
import g.sig.core_navigation.DeepLinkUri
import g.sig.core_navigation.Routes
import g.sig.feature_overview.overviewGraph
import g.sig.settings.settingsGraph
import g.sig.transactions.transactionsGraph
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Routes.Overview.destination,
    addExpenseBottomSheetState: ModalBottomSheetState,
    initialMonth: LocalDate,
    onDateChanged: (LocalDate) -> Unit
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        overviewGraph(listOf(navDeepLink {
            uriPattern = "$DeepLinkUri/${Routes.Overview.deepLink}"
        }), addExpenseBottomSheetState, onDateChanged, initialMonth)
        transactionsGraph(listOf(navDeepLink {
            uriPattern = "$DeepLinkUri/${Routes.Transactions.deepLink}"
        }))
        settingsGraph(listOf(navDeepLink {
            uriPattern = "$DeepLinkUri/${Routes.Settings.deepLink}"
        }))
    }
}