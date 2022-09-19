package g.sig.trackr

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.metrics.performance.JankStats
import g.sig.core_ui.theme.AppTheme
import g.sig.trackr.core.App
import g.sig.trackr.core.rememberAppState

class MainActivity : ComponentActivity() {
    private val jankStats by lazy {
        JankStats.createAndTrack(window) {
            if (it.isJank) Log.v("TrackR jank", it.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        jankStats.isTrackingEnabled = true
    }

    override fun onPause() {
        super.onPause()
        jankStats.isTrackingEnabled = false
    }

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