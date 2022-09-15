package g.sig.feature_overview

import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import g.sig.core_navigation.Routes

fun NavGraphBuilder.overviewGraph(deepLinks: List<NavDeepLink> = listOf()) {
    composable(Routes.Overview.destination, deepLinks = deepLinks) {
        OverviewRoute()
    }
}