package g.sig.core_data.models.user

import android.icu.util.Currency
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserSettings(
    @PrimaryKey(autoGenerate = true) val userSettingsId: Int = 0,
    val userId: Int,
    val currency: Currency
)