package g.sig.core_ui.icons

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import g.sig.core_ui.AppIcons
import g.sig.core_ui.R

@Composable
@Preview
fun TrackRLogo(
    modifier: Modifier = Modifier,
    isMaterialYou: Boolean = false,
    showText: Boolean = false
) {
    if (!isMaterialYou) {
        Icon(
            modifier = modifier,
            painter = painterResource(id = AppIcons.logo),
            contentDescription = "logo",
            tint = Color.Unspecified
        )
    } else {
        Icon(
            modifier = modifier,
            painter = painterResource(id = AppIcons.logo_mono),
            contentDescription = "logo"
        )
    }
    if (showText) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold
        )
    }
}