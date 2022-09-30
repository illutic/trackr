package g.sig.feature_overview

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddExpenseModal(state: ModalBottomSheetState, content: @Composable () -> Unit) {
    ModalBottomSheetLayout(
        sheetState = state,
        sheetBackgroundColor = MaterialTheme.colorScheme.surfaceVariant,
        sheetContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        sheetContent = {
            Text(text = "HOLA!")
        },
        content = content
    )
}