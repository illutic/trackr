package g.sig.trackr.navigation

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

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Routes.Overview.destination
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = startDestination
    ) {
        overviewGraph(listOf(navDeepLink { uriPattern = "$DeepLinkUri/overview" }))
        transactionsGraph(listOf(navDeepLink { uriPattern = "$DeepLinkUri/transactions" }))
        settingsGraph(listOf(navDeepLink { uriPattern = "$DeepLinkUri/settings" }))
    }
}