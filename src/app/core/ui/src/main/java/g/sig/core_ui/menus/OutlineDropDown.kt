package g.sig.core_ui.menus

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import g.sig.core_ui.AppIcons
import g.sig.core_ui.theme.shape16

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedCurrencyDropDown(
    modifier: Modifier = Modifier,
    label: String,
    items: List<String>,
    onItemSelected: (String) -> Unit,
    selectedItem: String
) {
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed: Boolean by interactionSource.collectIsPressedAsState()
    var showMenu by remember { mutableStateOf(isPressed) }
    if (isPressed) showMenu = true

    Box {
        OutlinedTextField(
            modifier = modifier
                .onGloballyPositioned {
                    textfieldSize = it.size.toSize()
                },
            readOnly = true,
            label = { Text(label, style = MaterialTheme.typography.labelLarge) },
            shape = shape16,
            value = selectedItem,
            interactionSource = interactionSource,
            trailingIcon = {
                Icon(
                    imageVector = AppIcons.chevron_down,
                    contentDescription = "drop down"
                )
            },
            onValueChange = {})
    }
    DropdownMenu(
        offset = DpOffset(16.dp, 16.dp),
        expanded = showMenu,
        onDismissRequest = { showMenu = false }) {
        items.forEach { currency ->
            DropdownMenuItem(text = {
                Text(
                    currency,
                    style = MaterialTheme.typography.labelMedium
                )
            },
                onClick = {
                    onItemSelected(currency)
                    showMenu = false
                })
        }
    }
}