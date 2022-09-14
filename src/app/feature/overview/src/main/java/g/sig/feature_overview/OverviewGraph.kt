package g.sig.feature_overview

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import g.sig.core_navigation.Routes

fun NavGraphBuilder.overviewGraph() {
    composable(Routes.Overview.destination) {
        OverviewRoute()
    }
}