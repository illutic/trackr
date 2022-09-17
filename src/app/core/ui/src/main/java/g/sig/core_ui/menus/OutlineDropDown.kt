package g.sig.core_ui.menus

import android.icu.util.Currency
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize
import g.sig.core_ui.AppIcons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedCurrencyDropDown(
    modifier: Modifier = Modifier,
    label: String, items: List<Currency>, onItemSelected: (Currency) -> Unit
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
            value = label,
            readOnly = true,
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
        modifier = Modifier.width(with(LocalDensity.current) { textfieldSize.width.toDp() }),
        expanded = showMenu,
        onDismissRequest = { showMenu = false }) {
        items.forEach { currency ->
            DropdownMenuItem(text = {
                Text(
                    currency.currencyCode,
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