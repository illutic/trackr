package g.sig.feature_overview

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OverviewViewModel(app: Application) : AndroidViewModel(app) {
    private val _overviewStateFlow = MutableStateFlow(OverviewState.OverviewLoading)
    val overviewStateFlow: StateFlow<OverviewState> = _overviewStateFlow
}