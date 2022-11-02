package g.sig.feature_overview

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import g.sig.core_navigation.Routes
import java.time.LocalDate

@OptIn(ExperimentalMaterialApi::class)
fun NavGraphBuilder.overviewGraph(
    deepLinks: List<NavDeepLink> = listOf(),
    addExpenseBottomSheetState: ModalBottomSheetState,
    onDateChanged: (LocalDate) -> Unit = {},
    initialMonth: LocalDate
) {
    composable(Routes.Overview.destination, deepLinks = deepLinks) {
        OverviewRoute(addExpenseBottomSheetState, onDateChanged, initialMonth)
    }
}