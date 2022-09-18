package g.sig.onboarding

import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import g.sig.core_navigation.Routes

fun NavGraphBuilder.onboardingGraph(
    deepLinks: List<NavDeepLink> = listOf(),
    onOnboardingComplete: () -> Unit
) {
    composable(Routes.Onboarding.destination, deepLinks = deepLinks) {
        OnboardingRoute(onOnboardingComplete)
    }
}