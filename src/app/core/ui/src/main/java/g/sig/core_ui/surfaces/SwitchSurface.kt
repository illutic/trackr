package g.sig.core_ui.surfaces

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun PrimarySwitchSurface(
    modifier: Modifier = Modifier,
    title: String,
    message: String? = null,
    selected: Boolean = false,
    onChanged: (Boolean) -> Unit
) {
    PrimarySurface(modifier = modifier) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            message?.let {
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = message,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        Switch(checked = selected, onCheckedChange = onChanged)
    }

}