package g.sig.trackr.core

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import g.sig.trackr.navigation.AppNavHost
import g.sig.trackr.navigation.TrackRBottomAppBar
import g.sig.trackr.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val appState = rememberAppState()

    Scaffold(
        bottomBar = {
            TrackRBottomAppBar(
                destinations = appState.topLevelDestinations,
                onNavigate = appState::navigate,
                currentDestination = appState.currentDestination
            )
        }
    ) { padding ->
        AppNavHost(modifier = Modifier.padding(padding), navController = appState.navController)
    }
}

@Composable
@Preview
fun AppPreview() {
    AppTheme {
        App()
    }
}