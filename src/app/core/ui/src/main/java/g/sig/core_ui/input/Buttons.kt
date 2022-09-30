package g.sig.core_ui.input

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import g.sig.constants.AppButtonType

@Composable
@Preview
fun AppTextButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String = "",
    type: AppButtonType = AppButtonType.Primary
) {
    val buttonColors = when (type) {
        AppButtonType.Primary ->
            ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.primary,
                containerColor = Color.Transparent
            )
        AppButtonType.Secondary ->
            ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.secondary,
                containerColor = Color.Transparent
            )
        AppButtonType.Tertiary ->
            ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.tertiary,
                containerColor = Color.Transparent
            )
    }
    TextButton(modifier = modifier, onClick = onClick, colors = buttonColors) {
        Text(text, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
@Preview
fun AppTonalButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String = "",
    type: AppButtonType = AppButtonType.Primary,
    shape: Shape? = null
) {
    val buttonColors = when (type) {
        AppButtonType.Primary ->
            ButtonDefaults.filledTonalButtonColors(
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        AppButtonType.Secondary ->
            ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        AppButtonType.Tertiary ->
            ButtonDefaults.textButtonColors(
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            )
    }

    if (shape != null) {
        FilledTonalButton(
            modifier = modifier,
            onClick = onClick,
            colors = buttonColors,
            shape = shape
        ) {
            Text(text, style = MaterialTheme.typography.labelMedium)
        }
    } else {
        FilledTonalButton(modifier = modifier, onClick = onClick, colors = buttonColors) {
            Text(text, style = MaterialTheme.typography.labelMedium)
        }
    }
}