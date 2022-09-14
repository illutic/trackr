package g.sig.trackr.navigation

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import g.sig.core_navigation.TopLevelRoute
import g.sig.core_ui.DrawableResIcon
import g.sig.core_ui.ImageVectorIcon
import g.sig.core_ui.theme.AppTheme
import g.sig.trackr.core.rememberAppState

@Composable
fun TrackRBottomAppBar(
    modifier: Modifier = Modifier,
    destinations: List<TopLevelRoute>,
    onNavigate: (TopLevelRoute) -> Unit,
    currentDestination: NavDestination?
) {
    Surface(color = MaterialTheme.colorScheme.surface) {
        NavigationBar(
            modifier = modifier
        ) {
            destinations.forEach { destination ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == destination.destination } == true
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onNavigate(destination) },
                    icon = {
                        val icon = if (isSelected) {
                            destination.selectedIcon
                        } else {
                            destination.unselectedIcon
                        }
                        when (icon) {
                            is DrawableResIcon ->
                                Icon(
                                    painter = painterResource(icon.id),
                                    contentDescription = stringResource(
                                        id = destination.titleRes
                                    )
                                )
                            is ImageVectorIcon ->
                                Icon(
                                    imageVector = icon.vector,
                                    contentDescription = stringResource(id = destination.titleRes)
                                )
                        }
                    },
                    alwaysShowLabel = false,
                    label = {
                        Text(
                            text = stringResource(id = destination.titleRes),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                )
            }
        }
    }
}

@Composable
@Preview
fun PreviewBottomAppBarLight() {
    val appState = rememberAppState()
    AppTheme {
        TrackRBottomAppBar(
            destinations = appState.topLevelDestinations,
            onNavigate = {},
            currentDestination = NavDestination(appState.topLevelDestinations.first().destination)
        )
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun PreviewBottomAppBarDark() {
    val appState = rememberAppState()
    AppTheme {
        TrackRBottomAppBar(
            destinations = appState.topLevelDestinations,
            onNavigate = {},
            currentDestination = NavDestination(appState.topLevelDestinations.first().destination)
        )
    }
}