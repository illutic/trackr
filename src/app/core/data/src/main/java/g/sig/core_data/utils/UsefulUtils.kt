package g.sig.core_data.utils

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

val <T> MutableStateFlow<T>.stateFlow: StateFlow<T> get() = this