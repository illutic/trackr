package g.sig.trackr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import g.sig.core_ui.theme.AppTheme
import g.sig.trackr.core.App
import g.sig.trackr.core.rememberAppState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()

        setContent {
            val appState = rememberAppState()
            AppTheme(dynamicColor = appState.useMaterialYou) {
                App(appState)
            }
        }
    }
}