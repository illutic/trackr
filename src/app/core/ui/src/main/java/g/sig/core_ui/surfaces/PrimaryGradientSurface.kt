package g.sig.core_ui.surfaces

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import g.sig.core_ui.icons.TrackRLogo
import g.sig.core_ui.theme.shape16

@Composable
@Preview
fun PrimaryGradientRow(modifier: Modifier = Modifier, content: @Composable () -> Unit = {}) {
    Row(
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.secondary
                    )
                ),
                shape = shape16
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
@Preview
fun GradientLogo(modifier: Modifier = Modifier, materialYou: Boolean = false) {
    Row(
        modifier = modifier
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.secondary
                    )
                ),
                shape = shape16
            ),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TrackRLogo(isMaterialYou = materialYou)
    }
}