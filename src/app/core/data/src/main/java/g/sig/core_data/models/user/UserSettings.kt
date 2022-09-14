package g.sig.core_data.models.user

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserSettings(
    @PrimaryKey(autoGenerate = true) val userSettingsId: Int,
    val userId: Int,
    @Embedded val currency: Currency
)